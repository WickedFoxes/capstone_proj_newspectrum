import argparse
import requests
import pymysql
from datetime import datetime
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed
from model import *

section_type = {
    "politics" : "10",
    "economy" : "20",
    "society" : "30",
    "sports" : "70",
    "entertain" : "60",
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

def fetch_and_upload_detail(news: dict, domain: str):
    try:
        articleIdx = news["articleIdx"]
        title = news["articleTitle"]
        content = news["articleInnerTextContent"]
        img_url = news["articleThumbnailImgUrl"]
        href = f"https://news.jtbc.co.kr/article/{articleIdx}"
        datePublished = news["publicationDate"]

        # datetime 변형
        dt = datetime.fromisoformat(datePublished)
        naive_dt = dt.replace(tzinfo=None)
        create_date = naive_dt.strftime('%Y-%m-%d %H:%M:%S')

        # 중복 확인
        if is_href_exist(href):
            return

        news_article = NewsArticle(
            title=title.replace('\n', ''),
            content=content,
            domain=section_korean[domain],
            media="jtbc",
            href=href,
            img_url=img_url,
            created_date=create_date,
            comics_url=None,
            summary=None
        )
        create_news_article(news_article)
        print(f"✅ 업로드 성공: {title[:20]}...")
    except Exception as e:
        print(f"❌ 오류 발생 ({news.get('articleTitle', '')[:20]}...): {e}")


def upload_news(domain="정치", pageStr=1, pageEnd=148):
    for page in range(pageStr, pageEnd+1):
        print(f"https://news-api.jtbc.co.kr/v1/get/contents/section/list/articles?"
              f"pageNo={page}&sectionIdx={section_type[domain]}&pageSize=20")
        
        request = requests.get(
            f"https://news-api.jtbc.co.kr/v1/get/contents/section/list/articles?"
            f"pageNo={page}&sectionIdx={section_type[domain]}&pageSize=20"
        )
        data = request.json()
        articles = data.get('data', {}).get('list', [])
        if len(articles) == 0:
            return False

        with ThreadPoolExecutor(max_workers=4) as executor:
            futures = [executor.submit(fetch_and_upload_detail, news, domain) for news in articles]
            for _ in as_completed(futures):
                pass  # 예외 처리용 순회

    return True

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="jtbc 뉴스 크롤러")
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