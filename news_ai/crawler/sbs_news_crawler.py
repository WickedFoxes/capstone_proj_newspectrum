import requests
import pymysql
import json
from datetime import datetime
from bs4 import BeautifulSoup

sbs_section_type = {
    "정치" : "01",
    "경제" : "02",
    "사회" : "03",
    "스포츠" : "09",
    "연예" : "14",
}

def upload_to_db(title:str,
                 content:str,
                 domain:str,
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
        sql = ("INSERT INTO news_article (title, content, domain, media, href, img_url, created_date) "
            "VALUES (%s, %s, %s, %s, %s, %s, %s)")
        values = (title, content, domain, media, href, img_url, create_date)

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

def upload_sbs_news(pageDate:str = "20250321", 
                 pageIdx:int = 1, 
                 domain:str = "정치"):
    board_request = requests.get("https://news.sbs.co.kr/news/newsSection.do?"+
                        f"pageIdx={pageIdx}&sectionType={sbs_section_type[domain]}&pageDate={pageDate}")
    bsoup = BeautifulSoup(board_request.content, 'html.parser')

    news_list = bsoup.select('div.w_news_list > ul > li > a')
    
    
    detail_hrefs = []
    if(len(news_list) == 0): return False
    
    for news in news_list:
        detail_hrefs.append(news.attrs['href'])
    
    for href in detail_hrefs:
        if(is_href_exist(href)): continue
        detail_request = requests.get(href)
        bsoup = BeautifulSoup(detail_request.content, 'html.parser')

        title = bsoup.select_one('#news-title').text  #대괄호는  h3#articleTitle 인 것중 첫번째 그룹만 가져오겠다.
        content = bsoup.select_one('div.main_text > div').text
        
        # 썸네일 찾기
        img_url = ""
        ld_json_tag = bsoup.find('script', type='application/ld+json')
        
        # 해당 태그 안의 JSON 문자열 파싱
        if ld_json_tag:
            try:
                data = json.loads(ld_json_tag.string)
                # thumbnailUrl 추출
                img_url = data["image"][0]
            except:
                print("image 파싱 오류")
        create_date = bsoup.select_one('div.date_area > span:nth-child(2)').text

        upload_to_db(title=title,
                     content=content,
                     domain=domain,
                     media="sbs",
                     href=href,
                     img_url=img_url,
                     create_date=create_date)
    return True


# SBS-2025.03
for date in range(20250301, 20250405):
    for page in range(1, 10):
        upload_sbs_news(pageDate=str(date),pageIdx=str(page),domain="정치")
        upload_sbs_news(pageDate=str(date),pageIdx=str(page),domain="경제")
        upload_sbs_news(pageDate=str(date),pageIdx=str(page),domain="사회")
        upload_sbs_news(pageDate=str(date),pageIdx=str(page),domain="스포츠")
        upload_sbs_news(pageDate=str(date),pageIdx=str(page),domain="연예")

# # SBS-2025.02
# for date in range(20250201, 20250228+1):
#     for page in range(1, 10):
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="정치")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="경제")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="사회")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="스포츠")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="연예")

# # SBS-2025.01
# for date in range(20250101, 20250131+1):
#     for page in range(1, 10):
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="정치")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="경제")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="사회")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="스포츠")
#         upload_sbs_news(pageDate=str(date),pageIdx=str(page),type="연예")