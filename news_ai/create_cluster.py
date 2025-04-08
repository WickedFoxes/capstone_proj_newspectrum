# 데이터베이스 연결
from model import *

import uuid
import numpy as np
from datetime import datetime, timedelta
from collections import Counter

# NLP 모델
from konlpy.tag import Okt, Komoran, Kkma
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.cluster import AgglomerativeClustering

# tokenizer
# tag = Okt()
tag = Komoran(userdic='user_dict.txt')
# tag = Kkma()

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

def cluster_articles(embeddings, n_clusters: int = 9):
    # 계층적 클러스터링
    clustering = AgglomerativeClustering(
        n_clusters=n_clusters,
        metric='precomputed',
        linkage='average'
    )
    labels = clustering.fit_predict(embeddings)
    return labels


def get_48_hours_before(load_date_str):
    # 문자열을 datetime 객체로 변환
    load_date = datetime.strptime(load_date_str, "%Y-%m-%d %H:%M:%S")
    
    # 48시간 이전 계산
    before_48_hours = load_date - timedelta(hours=48)
    
    # 다시 문자열로 변환해서 반환 (원하는 형식으로)
    return before_48_hours.strftime("%Y-%m-%d %H:%M:%S")


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
        # 뉴스 가져오기
        articles = read_news_articles_by_domain(domain=domain,
                                                date_str=current_start.strftime("%Y-%m-%d %H:%M:%S"),
                                                date_end=current_end.strftime("%Y-%m-%d %H:%M:%S"))
        titles = [article.title for article in articles]
        contents = [article.content for article in articles]
        created_dates = [article.created_date for article in articles]
        print(f"  📰 [{domain}] 문서 개수: {len(contents)}")

        # 클러스터 생성
        cluster_n = len(contents)*2//5

        embeddings, tfidf_matrix, feature_names = compute_tfidf_embedding(contents)
        print(embeddings.shape, tfidf_matrix.shape, feature_names.shape)

        labels = cluster_articles(embeddings, n_clusters=cluster_n)
        label_counts = Counter(labels)

        for label in label_counts:
            if(label_counts[label] < 3): continue
            print(f"Cluster {label}: {label_counts[label]}개")
            
            indices = np.where(labels == label)[0]
            print(label, indices)

            cluster_id=uuid.uuid4()
            for idx in indices:
                article = articles[idx]
                news_cluster_item = NewsCluster(id=None,
                                                cluster_id=cluster_id,
                                                news_article_id=article.id,
                                                created_date=current_end)
                create_news_cluster(news_cluster_item)        

    current_end += delta  # 48시간 전진
