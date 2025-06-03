import argparse
import requests
import pymysql
from datetime import datetime
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed
from model import *

section_type = {
    "politics" : "politics",
    "economy" : "money",
    "society" : "society",
    "sports" : "sports",
    "entertain" : "culture",
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

def fetch_and_upload_detail(href, domain):
    try:
        if is_href_exist(href):
            return

        detail_request = requests.get(href, timeout=10)
        bsoup = BeautifulSoup(detail_request.content, 'html.parser')

        datePublished_tag = bsoup.find('meta', {'property': 'published_date'})
        datePublished = datePublished_tag['content']
        dt = datetime.fromisoformat(datePublished).replace(tzinfo=None)
        create_date = dt.strftime('%Y-%m-%d %H:%M:%S')

        image_tag = bsoup.find('meta', {'property': 'og:image'})
        img_url = image_tag['content']

        header = bsoup.select_one('#container > section > article > header')
        if header is None:
            return
        title = header.find('h1').text.strip()

        content_tag = bsoup.select_one("#article_body")
        if content_tag is None:
            return
        content = content_tag.text.strip()

        news_article = NewsArticle(
            title=title,
            content=content,
            domain=section_korean[domain],
            media="중앙일보",
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


def upload_news(domain="정치", pageStr=1, pageEnd=148):
    for page in range(pageStr, pageEnd+1):
        board_request = requests.get(f"https://www.joongang.co.kr/{section_type[domain]}/?page={page}")
        print(f"https://www.joongang.co.kr/{section_type[domain]}/?page={page}")

        bsoup = BeautifulSoup(board_request.content, 'html.parser')
        news_list = bsoup.select('#story_list > li > div.card_body > h2.headline > a')
    
        detail_hrefs = []
        if(len(news_list) == 0): return False
    
        for news in news_list:
            url = news.attrs['href']
            detail_hrefs.append(url)
    
        with ThreadPoolExecutor(max_workers=4) as executor:  # 스레드 개수는 조절 가능
            futures = [executor.submit(fetch_and_upload_detail, href, domain) for href in detail_hrefs]
            for future in as_completed(futures):
                pass  # 결과가 없어도 예외 처리를 위해 순회 필요
    return True

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="중앙일보 뉴스 크롤러")
    parser.add_argument(
        "--domain", type=str, default="정치",
        choices=list(section_type.keys()),
        help="크롤링할 뉴스 도메인 (정치, 경제, 사회, 스포츠, 연예)"
    )
    parser.add_argument(
        "--start", type=int, default=1,
        help="시작 페이지 번호 (기본값: 1)"
    )
    parser.add_argument(
        "--end", type=int, default=98,
        help="끝 페이지 번호 (기본값: 98)"
    )

    args = parser.parse_args()

    print(f"📰 도메인: {args.domain} | 페이지: {args.start} ~ {args.end}")
    upload_news(domain=args.domain, pageStr=args.start, pageEnd=args.end)