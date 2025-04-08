# 데이터베이스 연결
from model import *

from datetime import datetime, timedelta
import math
from collections import defaultdict, Counter
from typing import List, Dict


def compute_ppmi_top10_relation(keywords: List[str], 
                               contents: List[str]) -> Dict[str, List[int]]:
    N = len(contents)
    keyword_set = set(keywords)

    word_doc_freq = defaultdict(int)
    co_occurrence = defaultdict(int)

    # 문서별 키워드 추출 및 카운트
    for text in contents:
        relevant_tokens = {kw for kw in keyword_set if kw in text}  # 중복 제거
        for word in relevant_tokens:
            word_doc_freq[word] += 1
        for w1 in relevant_tokens:
            for w2 in relevant_tokens:
                if w1 != w2:
                    pair = tuple(sorted([w1, w2]))
                    co_occurrence[pair] += 1

    ppmi_result = {}

    for kw in keyword_set:
        scores = []
        for other in keyword_set:
            if kw == other:
                continue
            pair = tuple(sorted([kw, other]))
            co_freq = co_occurrence.get(pair, 0)
            if co_freq == 0:
                continue

            p_kw = word_doc_freq[kw] / N
            p_other = word_doc_freq[other] / N
            p_pair = co_freq / N

            if p_kw == 0 or p_other == 0:
                continue

            try:
                pmi = math.log2(p_pair / (p_kw * p_other))
                ppmi = max(pmi, 0)
                scores.append((other, ppmi))
            except:
                continue

        top10 = sorted(scores, key=lambda x: x[1], reverse=True)[:10]
        ppmi_result[kw] = top10

    return ppmi_result

"""
사용자 설정
- 뉴스 가져올 날짜 작성
"""
################# 사용자 입력 필요 #################
end_date_str = "2025-03-02 18:00:00"
start_date_str = "2025-02-01 18:00:00"
domains = ['정치','경제','사회','스포츠','연예']
################# 입력 끝 ###########################

start_date = datetime.strptime(start_date_str, "%Y-%m-%d %H:%M:%S")
end_date = datetime.strptime(end_date_str, "%Y-%m-%d %H:%M:%S")
delta = timedelta(hours=48)

current_end = start_date
while current_end < end_date:
    current_start = current_end - delta
    print(f"\n📅 날짜 범위: {current_start} ~ {current_end}")

    for domain in domains:
        print("###", domain, "###")
        articles = read_news_articles_by_domain(domain=domain,
                                            date_str=current_start,
                                            date_end=current_end)
        contents = [article.content for article in articles]
        print(len(contents)) 

        """
        키워드 가져오기
        - created_date 기준으로 키워드 가져오기
        """
        id_list = [article.id for article in articles]
        keywords = read_keywords_with_id_list(id_list)
        keyword_dict = defaultdict(list)
        for k in keywords:
            keyword_dict[k.keyword].append(k)
        print(len(keywords), "->", len(keyword_dict.keys()))

        """
        연관 TOP10 키워드 저장
        - PPMI 기준으로 연관도가 높은 키워드 TOP 10 가져오기
        """
        keyword_top10_relation = compute_ppmi_top10_relation(keywords=list(keyword_dict.keys()), 
                                                             contents=contents)
        created_pairs = set()  # 중복 방지용 (k1_id, k2_id)

        for k1 in keywords:
            for related_kw, score in keyword_top10_relation.get(k1.keyword, []):
                for k2 in keyword_dict.get(related_kw, []):
                    if k1.id == k2.id:
                        continue
                    pair_one = (k1.id, k2.id)
                    pair_two = (k2.id, k1.id)
                    if pair_one in created_pairs or pair_two in created_pairs:
                        continue

                    keyword_relation = KeywordRelation(
                        id=None,
                        keyword_id=k1.id,
                        related_keyword_id=k2.id,
                        similarity=score
                    )
                    create_keyword_relation(keyword_relation)
                    keyword_relation = KeywordRelation(
                        id=None,
                        keyword_id=k2.id,
                        related_keyword_id=k1.id,
                        similarity=score
                    )
                    create_keyword_relation(keyword_relation)
                    
                    created_pairs.add(pair_one)
                    created_pairs.add(pair_one)
    current_end += delta