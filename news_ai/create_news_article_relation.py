# 데이터베이스 연결
from model import *

import numpy as np
from datetime import datetime, timedelta
from collections import defaultdict, Counter
from typing import List, Dict

# NLP 모델
from konlpy.tag import Okt, Komoran, Kkma
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.cluster import AgglomerativeClustering

# tokenizer
tag = Okt()

def get_48_hours_before(load_date_str):
    # 문자열을 datetime 객체로 변환
    load_date = datetime.strptime(load_date_str, "%Y-%m-%d %H:%M:%S")
    
    # 48시간 이전 계산
    before_48_hours = load_date - timedelta(hours=48)
    
    # 다시 문자열로 변환해서 반환 (원하는 형식으로)
    return before_48_hours.strftime("%Y-%m-%d %H:%M:%S")

def compute_tfidf_embedding(contents: list[str]):
    vectorizer = TfidfVectorizer(
        tokenizer=tag.nouns,
        max_df=0.9,
        min_df=2,
    )
    tfidf_matrix = vectorizer.fit_transform(contents)
    feature_names = vectorizer.get_feature_names_out()

    # 코사인 유사도
    cosine_sim = cosine_similarity(tfidf_matrix)
    embeddings = 1 - cosine_sim

    return embeddings, tfidf_matrix, feature_names

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
                                            date_str=current_start.strftime("%Y-%m-%d %H:%M:%S"),
                                            date_end=current_end.strftime("%Y-%m-%d %H:%M:%S"))
        for article in articles:
            related_articles = read_articles_with_clsuter_and_keyword(
                news_article_id=article.id,
                end_date=current_end.strftime("%Y-%m-%d %H:%M:%S"),
                start_date=(current_end-timedelta(days=14)).strftime("%Y-%m-%d %H:%M:%S"),
                domain=domain
            )
            if len(related_articles) == 0:
                continue
            if len(related_articles) == 1:
                related_article = related_articles[0]
                news_article_relation = NewsArticleRelation(
                    id=None,
                    news_article_id=article.id,
                    related_news_article_id=related_article.id,
                    similarity=0.5
                )
                create_news_article_relation(news_article_relation)
                continue

            contents = [article.content]
            for related_article in related_articles:
                contents.append(related_article.content)

            embeddings, _, _ = compute_tfidf_embedding(contents)
            distance_vector = embeddings[0][1:]  # 기준 문서와 나머지 간의 거리
            
            # 유사도 상위 15개 인덱스 (가장 가까운 것)
            top_k = min(15, len(distance_vector))
            top_indices = np.argsort(distance_vector)[:top_k]
            top15_indices = np.argsort(distance_vector)[:15] 

            for i in top15_indices:
                news_article_relation = NewsArticleRelation(
                    id=None,
                    news_article_id=article.id,
                    related_news_article_id=related_articles[i].id,
                    similarity=distance_vector[i]
                )
                create_news_article_relation(news_article_relation)

    current_end += delta