# 데이터베이스 연결
from model import *

import uuid
from datetime import datetime, timedelta

# NLP 모델
from keybert import KeyBERT
from transformers import AutoModel, AutoTokenizer, AutoModelForTokenClassification, pipeline
from konlpy.tag import Okt, Komoran, Kkma
from gliner import GLiNER

# KoSimCSE-roberta 모델 불러오기
koSimCSE_model = AutoModel.from_pretrained("BM-K/KoSimCSE-roberta")
# KeyBERT에 KoBERT 모델 연결
kw_model = KeyBERT(model=koSimCSE_model)

# taeminlee/gliner_ko 모델 불러오기
ner_model = GLiNER.from_pretrained("taeminlee/gliner_ko")
tta_labels = ["PERSON"]
# tta_labels = ["ARTIFACTS", "ANIMAL", "CIVILIZATION", "DATE", "EVENT", "STUDY_FIELD", "LOCATION", "MATERIAL", "ORGANIZATION", "PERSON", "PLANT", "QUANTITY", "TIME", "TERM", "THEORY"]

# tokenizer
# tag = Okt()
tag = Komoran(userdic='user_dict.txt')
# tag = Kkma()

korean_stopwords = [
    '그리고', '그러나', '하지만', '그래서', '또한',
    '이', '그', '저', '것', '수', '등', '때문에', '및',
    '하는', '하여', '하게', '하고', '이다', '있는', '없는',
    '됐다', '되었다', '같은', '위한', '대한', '중인', '하면서',
    '보다', '가지', '등의', '이번', '관련', '통해', '경우',
    '에서', '으로', '까지', '부터', '에는', '에서의',
    '하게', '되며', '되어', '되기', '된다', '됐다',
    '라고', '으로서', '로서', '였다', '이었다', '토요와이드'
]

def extract_keywords(title, content, top_n=5):
    print(title.replace("\n", ""))
    
    ner_results = ner_model.predict_entities(content, tta_labels)
    people_keywords = [(entity['text'], entity['score']) 
                       for entity in ner_results 
                       if entity['label'] == 'PERSON' 
                       and len(entity['text']) > 1
                       and len(entity['text']) < 10
                       and entity['score'] > 0.9]

    # title_nouns = tag.nouns(title)
    title_nouns = [word for word, word_tag in tag.pos(title) if word_tag.startswith('NN')]
    edited_title = " ".join(title_nouns)

    title_keywords = kw_model.extract_keywords(
            edited_title,
            top_n=top_n, 
            stop_words=korean_stopwords,
            keyphrase_ngram_range=(1, 1),
            use_mmr=True,
            diversity=0.7
    )
    
    result = []
    people = []

    for item, _ in people_keywords:
        result.append(item)
        people.append(item)
    for item, _ in title_keywords:
        if any(item in person for person in people): continue
        result.append(item)
    return list(set(result))

def get_48_hours_before(load_date_str):
    # 문자열을 datetime 객체로 변환
    load_date = datetime.strptime(load_date_str, "%Y-%m-%d %H:%M:%S")
    
    # 48시간 이전 계산
    before_48_hours = load_date - timedelta(hours=48)
    
    # 다시 문자열로 변환해서 반환 (원하는 형식으로)
    return before_48_hours.strftime("%Y-%m-%d %H:%M:%S")


################# 사용자 입력 필요 #################
end_date_str = "2025-03-02 00:00:00"
start_date_str = "2025-02-01 00:00:00"
domains = ['정치','경제','사회','스포츠','연예']
################# 입력 끝 ###########################

start_date = datetime.strptime(start_date_str, "%Y-%m-%d %H:%M:%S")
end_date = datetime.strptime(end_date_str, "%Y-%m-%d %H:%M:%S")
delta = timedelta(hours=48)

current_end = start_date
while current_end <= end_date:
    current_start = current_end - delta
    print(f"\n📅 날짜 범위: {current_start} ~ {current_end}")

    for domain in domains:
        # 뉴스 가져오기
        articles = read_news_articles_by_domain(domain=domain,
                                                date_str=current_start.strftime("%Y-%m-%d %H:%M:%S"),
                                                date_end=current_end.strftime("%Y-%m-%d %H:%M:%S"))
        print(f"  📰 [{domain}] 문서 개수: {len(articles)}")
        
        # 뉴스 키워드 생성
        for article in articles:
            keywords = extract_keywords(article.title, article.content, top_n=5)
            for keyword in keywords:
                keyword_item = Keyword(id=None,
                                    keyword=keyword,
                                    created_date=current_end,
                                    news_article_id=article.id)
                create_news_keyword(keyword_item)    

    current_end += delta  # 48시간 전진
