import requests
import pymysql
import json
from datetime import datetime
from bs4 import BeautifulSoup

joongang_section_type = {
    "정치" : "politics",
    "경제" : "money",
    "사회" : "society",
    "스포츠" : "sports",
    "연예" : "culture",
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

def upload_joongang_news(type="정치", pageStr=1, pageEnd=148):
    for page in range(pageStr, pageEnd+1):
        board_request = requests.get(f"https://www.joongang.co.kr/{joongang_section_type[type]}/?page={page}")
        print(f"https://www.joongang.co.kr/{joongang_section_type[type]}/?page={page}")

        bsoup = BeautifulSoup(board_request.content, 'html.parser')
        news_list = bsoup.select('#story_list > li > div.card_body > h2.headline > a')
    
        detail_hrefs = []
        if(len(news_list) == 0): return False
    
        for news in news_list:
            url = news.attrs['href']
            detail_hrefs.append(url)
    
        for href in detail_hrefs:
            if(is_href_exist(href)): continue
            detail_request = requests.get(href)
            bsoup = BeautifulSoup(detail_request.content, 'html.parser')

            # datetime 태그 찾기
            datePublished_tag = bsoup.find('meta', {'property': 'published_date'})
            datePublished = datePublished_tag['content']
            # datetime 변형
            dt = datetime.fromisoformat(datePublished)
            naive_dt = dt.replace(tzinfo=None)
            create_date = naive_dt.strftime('%Y-%m-%d %H:%M:%S')

            # image 태그 찾기
            image_tag = bsoup.find('meta', {'property': 'og:image'})
            img_url = image_tag['content']
            
            # title
            header = bsoup.select_one('#container > section > article > header')
            if header is None:
                continue
            title = header.find('h1').text
            # content
            content = bsoup.select_one("#article_body").text

            upload_to_db(title=title,
                        content=content,
                        type=type,
                        media="중앙일보",
                        href=href,
                        img_url=img_url,
                        create_date=create_date)
    return True

# mbn 2025.03 ~ 2025.01
# upload_joongang_news(pageStr=1, pageEnd=98, type="정치")
upload_joongang_news(pageStr=1, pageEnd=10, type="경제")
upload_joongang_news(pageStr=1, pageEnd=10, type="사회")
upload_joongang_news(pageStr=1, pageEnd=10, type="스포츠")
upload_joongang_news(pageStr=1, pageEnd=10, type="연예")