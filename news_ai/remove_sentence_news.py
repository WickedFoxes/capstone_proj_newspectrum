import pymysql

# DB 연결
conn = pymysql.connect(host='localhost', user='root', password='root', db='newspectrum', charset='utf8mb4')
cur = conn.cursor()

# 삭제할 문장 리스트
remove_texts = [
    "VOD 시청 안내",
    "어도비 플래시 플레이어 서비스 종료에 따라현재 브라우저 버전에서는 서비스가 원할하지 않습니다.아래 버튼을 클릭하셔서 브라우저 업그레이드(설치) 해주시기 바랍니다.",
    "브라우저 업그레이드 및 설치"
]

# 해당 문장이 포함된 content 조회
cur.execute("""
    SELECT id, content 
    FROM news_article 
    WHERE content LIKE '%VOD 시청 안내%'
       OR content LIKE '%어도비 플래시 플레이어 서비스 종료에 따라%'
       OR content LIKE '%브라우저 업그레이드 및 설치%'
""")
rows = cur.fetchall()

# 하나씩 처리
for article_id, content in rows:
    for remove_text in remove_texts:
        content = content.replace(remove_text, '')  # 문장 삭제
    content = content.strip()  # 앞뒤 공백 정리
    
    # 업데이트
    update_sql = "UPDATE news_article SET content = %s WHERE id = %s"
    cur.execute(update_sql, (content, article_id))

# 커밋 및 종료
conn.commit()
cur.close()
conn.close()
