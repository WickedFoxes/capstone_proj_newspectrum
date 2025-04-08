import requests
import pymysql
import json
from datetime import datetime
from bs4 import BeautifulSoup

mbn_section_type = {
    "정치" : "politics",
    "경제" : "economy",
    "사회" : "society",
    "스포츠" : "sports",
    "연예" : "entertain",
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

def upload_mbn_news(type="정치", pageStr=1, pageEnd=148):
    for page in range(pageStr, pageEnd+1):
        board_request = requests.get(f"https://www.mbn.co.kr/news/{mbn_section_type[type]}/?page={page}")
        bsoup = BeautifulSoup(board_request.content, 'html.parser')
        news_list = bsoup.select('div.list_area > dl > dt > a')
    
        detail_hrefs = []
        if(len(news_list) == 0): return False
    
        for news in news_list:
            url = news.attrs['href']
            print(url)
            if url.startswith('//'):
                detail_hrefs.append("https:"+url)
            else:
                detail_hrefs.append(f"https://www.mbn.co.kr/news/{mbn_section_type[type]}/{url}")
    
        for href in detail_hrefs:
            if(is_href_exist(href)): continue
            detail_request = requests.get(href)
            bsoup = BeautifulSoup(detail_request.content, 'html.parser')

            ld_json_tag = bsoup.find('script', type='application/ld+json')
            img_url = ""
            create_date = ""

            if ld_json_tag:
                try:
                    data = json.loads(ld_json_tag.string)
                    # thumbnailUrl 추출
                    img_url = data["image"][0]
                    datePublished = data["datePublished"]

                    # datetime 변형
                    dt = datetime.fromisoformat(datePublished)
                    naive_dt = dt.replace(tzinfo=None)
                    create_date = naive_dt.strftime('%Y-%m-%d %H:%M:%S')
                    
                except:
                    print("image 파싱 오류")

            print(create_date)

            title = bsoup.select_one('div.title_box > div > h1').text
            content = bsoup.select_one('#newsViewArea').text

            upload_to_db(title=title,
                        content=content,
                        type=type,
                        media="mbn",
                        href=href,
                        img_url=img_url,
                        create_date=create_date)
    return True

# mbn 2025.03 ~ 2025.01
upload_mbn_news(pageStr=1, pageEnd=10, type="정치")
upload_mbn_news(pageStr=1, pageEnd=10, type="경제")
upload_mbn_news(pageStr=1, pageEnd=10, type="사회")
upload_mbn_news(pageStr=1, pageEnd=10, type="스포츠")
upload_mbn_news(pageStr=1, pageEnd=10, type="연예")