import requests
import pymysql
import json
from datetime import datetime
from bs4 import BeautifulSoup

jtbc_section_type = {
    "정치" : "10",
    "경제" : "20",
    "사회" : "30",
    "스포츠" : "70",
    "연예" : "60",
}

def upload_to_db(title:str,
                 content:str,
                 type:str,
                 media:str,
                 href:str,
                 img_url:str,
                 create_date:str):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # SQL 구문
        sql = ("INSERT INTO news_article (title, content, type, media, href, img_url, created_date) "
            "VALUES (%s, %s, %s, %s, %s, %s, %s)")
        values = (title, content, type, media, href, img_url, create_date)

        # SQL 실행
        cur.execute(sql, values)

        # 변경사항 커밋
        conn.commit()
        print("✅ 데이터 삽입 성공")

    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)

    except Exception as e:
        print("❌ 기타 예외 발생:", e)

    finally:
        # 커서 및 연결 종료
        try:
            if cur:
                cur.close()
            if conn:
                conn.close()
            print("🔒 연결 종료")
        except:
            pass

def is_href_exist(href):
    try:
        conn = pymysql.connect(host='localhost',
                               user='root',
                               password='root',
                               db='newspectrum',
                               charset='utf8mb4')
        cur = conn.cursor()

        sql = "SELECT EXISTS(SELECT 1 FROM news_article WHERE href = %s)"
        cur.execute(sql, (href,))
        result = cur.fetchone()[0]

        return bool(result)

    except pymysql.MySQLError as e:
        print("❌ DB 오류:", e)
        return False  # 오류 발생 시 False 리턴 (필요에 따라 변경 가능)

    finally:
        try:
            if cur:
                cur.close()
            if conn:
                conn.close()
        except:
            pass


def upload_jtbc_news(type="정치", 
                     pageStr=1, 
                     pageEnd=148):
    for page in range(pageStr, pageEnd+1):
        print(f"https://news-api.jtbc.co.kr/v1/get/contents/section/list/articles?"
                                     +f"pageNo={page}"
                                     +f"&sectionIdx={jtbc_section_type[type]}"
                                     +f"&pageSize=20")
        request = requests.get(f"https://news-api.jtbc.co.kr/v1/get/contents/section/list/articles?"
                                     +f"pageNo={page}"
                                     +f"&sectionIdx={jtbc_section_type[type]}"
                                     +f"&pageSize=20")
        data = request.json()
        articles = data.get('data', {}).get('list', [])
        if(len(articles) == 0): return False
    
        for news in articles:
            articleIdx = news["articleIdx"]
            title = news["articleTitle"]
            content = news["articleInnerTextContent"]
            img_url = news["articleThumbnailImgUrl"]
            url = f"https://news.jtbc.co.kr/article/{articleIdx}"
            datePublished = news["publicationDate"]

            # datetime 변형
            dt = datetime.fromisoformat(datePublished)
            naive_dt = dt.replace(tzinfo=None)
            create_date = naive_dt.strftime('%Y-%m-%d %H:%M:%S')

            upload_to_db(title=title,
                        content=content,
                        type=type,
                        media="jtbc",
                        href=url,
                        img_url=img_url,
                        create_date=create_date)
    return True

# jtbc 2025.03 ~ 2025.01
upload_jtbc_news(pageStr=1, pageEnd=10, type="정치")
upload_jtbc_news(pageStr=1, pageEnd=10, type="경제")
upload_jtbc_news(pageStr=1, pageEnd=10, type="사회")
upload_jtbc_news(pageStr=1, pageEnd=10, type="스포츠")
upload_jtbc_news(pageStr=1, pageEnd=10, type="연예")