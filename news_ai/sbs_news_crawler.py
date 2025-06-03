import argparse
import requests
import pymysql
import json
from datetime import datetime, timedelta
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed
from model import *

section_type = {
    "politics" : "01",
    "economy" : "02",
    "society" : "03",
    "sports" : "09",
    "entertain" : "14",
}
section_korean = {
    "politics" : "정치",
    "economy" : "경제",
    "society" : "사회",
    "sports" : "스포츠",
    "entertain" : "연예",
}


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

def fetch_and_upload_detail(href: str, domain: str):
    try:
        if is_href_exist(href):
            return

        detail_request = requests.get(href, timeout=10)
        bsoup = BeautifulSoup(detail_request.content, 'html.parser')

        # 제목과 본문
        title_tag = bsoup.select_one('#news-title')
        content_tag = bsoup.select_one('div.main_text > div')
        if not title_tag or not content_tag:
            print(f"⚠️ 파싱 실패: {href}")
            return

        title = title_tag.text.strip()
        content = content_tag.text.strip()

        # 이미지 URL 추출
        img_url = ""
        ld_json_tag = bsoup.find('script', type='application/ld+json')
        if ld_json_tag:
            try:
                data = json.loads(ld_json_tag.string)
                img_url = data["image"][0] if isinstance(data["image"], list) else data["image"]
            except Exception as e:
                print(f"⚠️ 이미지 파싱 오류: {e}")

        # 날짜 추출
        date_tag = bsoup.select_one('div.date_area > span:nth-child(2)')
        create_date = date_tag.text.strip() if date_tag else ""

        news_article = NewsArticle(
            title=title.replace('\n', ''),
            content=content,
            domain=section_korean[domain],
            media="sbs",
            href=href,
            img_url=img_url,
            created_date=create_date,
            comics_url=None,
            summary=None
        )
        create_news_article(news_article)
        print(f"✅ 업로드 성공: {title[:20]}...")

    except Exception as e:
        print(f"❌ 오류 발생 ({href}): {e}")


def upload_news(pageDate:str = "20250321", 
                 pageIdx:int = 1, 
                 domain:str = "정치"):
    board_request = requests.get("https://news.sbs.co.kr/news/newsSection.do?"+
                        f"pageIdx={pageIdx}&sectionType={section_type[domain]}&pageDate={pageDate}")
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

        news_article = NewsArticle(
            title=title.replace('\n', ''),
            content=content,
            domain=domain,
            media="sbs",
            href=href,
            img_url=img_url,
            created_date=create_date,
            comics_url=None,
            summary=None
        )
        create_news_article(news_article)
        print(f"✅ 업로드 성공: {title[:20]}...")
    return True

def upload_news(pageDate: str = "20250321", pageIdx: int = 1, domain: str = "정치"):
    board_request = requests.get(
        f"https://news.sbs.co.kr/news/newsSection.do?pageIdx={pageIdx}&sectionType={section_type[domain]}&pageDate={pageDate}"
    )
    bsoup = BeautifulSoup(board_request.content, 'html.parser')
    news_list = bsoup.select('div.w_news_list > ul > li > a')

    if len(news_list) == 0:
        print("❌ 뉴스 없음")
        return False

    detail_hrefs = [news.attrs['href'] for news in news_list]

    with ThreadPoolExecutor(max_workers=4) as executor:
        futures = [executor.submit(fetch_and_upload_detail, href, domain) for href in detail_hrefs]
        for _ in as_completed(futures):
            pass

    return True

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="sbs 뉴스 크롤러")
    parser.add_argument(
        "--domain", type=str, default="정치",
        choices=list(section_type.keys()),
        help="크롤링할 뉴스 도메인 (정치, 경제, 사회, 스포츠, 연예)"
    )
    parser.add_argument(
        "--duration", type=int, default=50, 
        help="오늘 기준 과거 며칠 전부터의 기간"
    )
    args = parser.parse_args()

    # 오늘 날짜 (예: 2025-05-26)
    end_date = datetime.today()
    # 시작 날짜 = 오늘 - duration 일
    start_date = end_date - timedelta(days=args.duration)
    # 날짜를 문자열로 포맷 (예: 20250405)
    end_date_str = end_date.strftime("%Y%m%d")
    start_date_str = start_date.strftime("%Y%m%d")

    # 확인용 출력
    print(f"도메인: {args.domain}")
    print(f"수집 기간: {start_date_str} ~ {end_date_str}")

    # 반복문 (start_date부터 end_date까지 하루씩 증가)
    current_date = start_date
    while current_date <= end_date:
        date_str = current_date.strftime("%Y%m%d")  # "20250405" 형식
        
        for page in range(1, 10):
            upload_news(pageDate=str(date_str),pageIdx=str(page), domain=args.domain)
        
        current_date += timedelta(days=1)



    