import argparse
import requests
import pymysql
import json
from datetime import datetime
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor, as_completed
from model import *

section_type = {
    "politics" : "politics",
    "economy" : "economy",
    "society" : "society",
    "sports" : "sports",
    "entertain" : "entertain",
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

        # ld+json에서 이미지와 날짜 추출
        ld_json_tag = bsoup.find('script', type='application/ld+json')
        img_url = ""
        create_date = ""

        if ld_json_tag:
            try:
                data = json.loads(ld_json_tag.string)
                img_url = data["image"][0] if isinstance(data["image"], list) else data["image"]
                datePublished = data["datePublished"]
                dt = datetime.fromisoformat(datePublished).replace(tzinfo=None)
                create_date = dt.strftime('%Y-%m-%d %H:%M:%S')
            except Exception as e:
                print(f"⚠️ JSON 파싱 오류: {e}")

        # 제목 및 본문 추출
        title_tag = bsoup.select_one('div.title_box > div > h1')
        content_tag = bsoup.select_one('#newsViewArea')
        if not title_tag or not content_tag:
            print(f"⚠️ 파싱 실패: {href}")
            return

        title = title_tag.text.strip()
        content = content_tag.text.strip()

        news_article = NewsArticle(
            title=title.replace('\n', ''),
            content=content,
            domain=section_korean[domain],
            media="mbn",
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
        print(f"[📄 페이지 {page}]")
        board_request = requests.get(f"https://www.mbn.co.kr/news/{section_type[domain]}/?page={page}")
        bsoup = BeautifulSoup(board_request.content, 'html.parser')
        news_list = bsoup.select('div.list_area > dl > dt > a')

        if len(news_list) == 0:
            print("❌ 뉴스 없음, 중단")
            return False

        detail_hrefs = []
        for news in news_list:
            url = news.attrs['href']
            if url.startswith('//'):
                detail_hrefs.append("https:" + url)
            else:
                detail_hrefs.append(f"https://www.mbn.co.kr/news/{section_type[domain]}/{url}")

        with ThreadPoolExecutor(max_workers=4) as executor:
            futures = [executor.submit(fetch_and_upload_detail, href, domain) for href in detail_hrefs]
            for _ in as_completed(futures):
                pass

    return True


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="mbn 뉴스 크롤러")
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