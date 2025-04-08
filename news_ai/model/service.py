# mariadb 데이터 관련
import pymysql
from dataclasses import fields
from .NewsArticle import NewsArticle
from .NewsCluster import NewsCluster
from .Keyword import Keyword
from .KeywordRelation import KeywordRelation
from .NewsArticleRelation import NewsArticleRelation
# READ
def read_news_articles_by_domain(date_str = "2025-03-01 00:00:00", 
                               date_end = "2025-03-02 23:59:59", 
                               domain = "경제"):
    # DB 연결
    conn = pymysql.connect(host='localhost',
                        user='root',
                        password='root',
                        db='newspectrum',
                        charset='utf8mb4',
                        cursorclass=pymysql.cursors.DictCursor)
    articles = []
    try:
        with conn.cursor() as cursor:
            sql = """
            SELECT * 
            FROM news_article  
            WHERE created_date >= %s AND created_date <= %s AND domain = %s
            ORDER BY created_date DESC
            """
            cursor.execute(sql, (date_str, date_end, domain))
            rows = cursor.fetchall()            
            print("✅ 데이터 가져오기 성공")

            for row in rows:
                article = NewsArticle(**row)
                articles.append(article)
    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)
    except Exception as e:
        print("❌ 기타 예외 발생:", e)
    finally:
        conn.close()
    return articles

def read_news_articles(date_str = "2025-03-01 00:00:00", 
                       date_end = "2025-03-02 23:59:59") -> list[NewsArticle]:
    # DB 연결
    conn = pymysql.connect(host='localhost',
                        user='root',
                        password='root',
                        db='newspectrum',
                        charset='utf8mb4',
                        cursorclass=pymysql.cursors.DictCursor)
    articles = []
    try:
        with conn.cursor() as cursor:
            item_fields = ", ".join([f.name for f in fields(NewsArticle)])
            
            sql = f"""
            SELECT {item_fields}  
            FROM news_article 
            WHERE created_date >= %s AND created_date <= %s 
            ORDER BY created_date DESC
            """
            cursor.execute(sql, (date_str, date_end))
            rows = cursor.fetchall()            
            print("✅ 데이터 가져오기 성공")

            for row in rows:
                article = NewsArticle(**row)
                articles.append(article)
    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)
    except Exception as e:
        print("❌ 기타 예외 발생:", e)
    finally:
        conn.close()
    
    return articles

def read_keywords_with_id_list(id_list):
    if not id_list:
        return []  # 빈 리스트일 경우 바로 리턴
    
    # DB 연결
    conn = pymysql.connect(host='localhost',
                        user='root',
                        password='root',
                        db='newspectrum',
                        charset='utf8mb4',
                        cursorclass=pymysql.cursors.DictCursor)
    keywords = []
    # %s, %s, ... 형태의 placeholder 생성
    format_strings = ','.join(['%s'] * len(id_list))
    
    try:
        with conn.cursor() as cursor:
            sql = f"""
            SELECT 
                k.*
            FROM 
                keyword k
            WHERE 
                news_article_id in ({format_strings});
            """
            cursor.execute(sql, id_list)
            rows = cursor.fetchall()            
            print("✅ 데이터 가져오기 성공")

            for row in rows:
                keyword = Keyword(**row)
                keywords.append(keyword)
    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)
    except Exception as e:
        print("❌ 기타 예외 발생:", e)
    finally:
        conn.close()
    return keywords

def read_articles_with_id_in_cluster(news_article_id):
    # DB 연결
    conn = pymysql.connect(host='localhost',
                        user='root',
                        password='root',
                        db='newspectrum',
                        charset='utf8mb4',
                        cursorclass=pymysql.cursors.DictCursor)
    news_articles = []
    try:
        with conn.cursor() as cursor:
            sql = """
            SELECT DISTINCT na.*
            FROM news_article na
            LEFT JOIN news_cluster nc ON na.id = nc.news_article_id
            LEFT JOIN keyword k ON na.id = k.news_article_id
            WHERE (
                nc.cluster_id = (
                    SELECT cluster_id 
                    FROM news_cluster 
                    WHERE news_article_id = %s
                    LIMIT 1
                )
            )
            AND na.id != %s;
            """
            cursor.execute(sql, (news_article_id, news_article_id))
            rows = cursor.fetchall()            
            print("✅ 데이터 가져오기 성공")

            for row in rows:
                news_article = NewsArticle(**row)
                news_articles.append(news_article)
    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)
    except Exception as e:
        print("❌ 기타 예외 발생:", e)
    finally:
        conn.close()
    return news_articles

def read_articles_with_clsuter_and_keyword(news_article_id, end_date, start_date, domain = "경제"):
    # DB 연결
    conn = pymysql.connect(host='localhost',
                        user='root',
                        password='root',
                        db='newspectrum',
                        charset='utf8mb4',
                        cursorclass=pymysql.cursors.DictCursor)
    news_articles = []
    try:
        with conn.cursor() as cursor:
            sql = """
            SELECT DISTINCT na.*
            FROM news_article na
            LEFT JOIN news_cluster nc ON na.id = nc.news_article_id
            LEFT JOIN keyword k ON na.id = k.news_article_id
            WHERE (
                nc.cluster_id = (
                    SELECT cluster_id 
                    FROM news_cluster 
                    WHERE news_article_id = %s
                    LIMIT 1
                )
                OR (
                    k.keyword IN (
                        SELECT keyword 
                        FROM keyword 
                        WHERE news_article_id = %s
                    )
                AND na.created_date >= %s
                AND na.created_date <= %s
                AND na.domain = %s
                )
            )
            AND na.id != %s;
            """
            cursor.execute(sql, (news_article_id, news_article_id, start_date, end_date, domain, news_article_id))
            rows = cursor.fetchall()            
            print("✅ 데이터 가져오기 성공")

            for row in rows:
                news_article = NewsArticle(**row)
                news_articles.append(news_article)
    except pymysql.MySQLError as e:
        print("❌ 데이터베이스 오류 발생:", e)
    except Exception as e:
        print("❌ 기타 예외 발생:", e)
    finally:
        conn.close()
    return news_articles


# CREATE
def create_news_article(news_article:NewsArticle):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # 중복 확인 쿼리
        check_sql = """
        SELECT 1 FROM news_article
        WHERE href = %s
        LIMIT 1
        """
        cur.execute(check_sql, (news_article.href))
        if cur.fetchone():
            print("⚠️ 이미 존재하는 (href) 입니다. 삽입 안 함.")
            return

        # id를 제외한 필드만 선택
        item_fields = ", ".join([f.name for f in fields(NewsArticle) if f.name != "id"])
        placeholders = ", ".join(["%s"] * len(item_fields.split(", ")))
        # SQL 구문
        sql = f"INSERT INTO news_article ({item_fields}) VALUES ({placeholders})"
        # 값 튜플 생성
        values = tuple(getattr(news_article, f.name) for f in fields(NewsArticle) if f.name != "id")

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

def create_news_cluster(news_cluster:NewsCluster):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # 중복 확인 쿼리
        check_sql = """
        SELECT 1 FROM news_cluster 
        WHERE news_article_id = %s AND cluster_id = %s
        LIMIT 1
        """
        cur.execute(check_sql, (news_cluster.news_article_id, news_cluster.cluster_id))
        if cur.fetchone():
            print("⚠️ 이미 존재하는 (news_article_id, cluster_id) 조합입니다. 삽입 안 함.")
            return

        # id 제외 필드명 추출
        item_fields = [f.name for f in fields(NewsCluster) if f.name != "id"]
        field_str = ", ".join(item_fields)
        placeholders = ", ".join(["%s"] * len(item_fields))

        # SQL 구문
        sql = f"INSERT INTO news_cluster ({field_str}) VALUES ({placeholders})"
        
        # 값 추출 (중요: news_cluster에서!)
        values = tuple(getattr(news_cluster, f) for f in item_fields)

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

def create_news_keyword(keyword:Keyword):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # 중복 확인 쿼리
        check_sql = """
        SELECT 1 FROM keyword 
        WHERE news_article_id = %s AND keyword = %s
        LIMIT 1
        """
        cur.execute(check_sql, (keyword.news_article_id, keyword.keyword))
        if cur.fetchone():
            print("⚠️ 이미 존재하는 (news_article_id, keyword) 조합입니다. 삽입 안 함.")
            return

        # id 제외 필드명 추출
        item_fields = [f.name for f in fields(Keyword) if f.name != "id"]
        field_str = ", ".join(item_fields)
        placeholders = ", ".join(["%s"] * len(item_fields))

        # SQL 구문
        sql = f"INSERT INTO keyword ({field_str}) VALUES ({placeholders})"
        
        # 값 추출 (중요: news_cluster에서!)
        values = tuple(getattr(keyword, f) for f in item_fields)

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

def create_keyword_relation(keyword_relation:KeywordRelation):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # 중복 확인 쿼리
        check_sql = """
        SELECT 1 FROM keyword_relation 
        WHERE keyword_id = %s AND related_keyword_id = %s
        LIMIT 1
        """
        cur.execute(check_sql, (keyword_relation.keyword_id, keyword_relation.related_keyword_id))
        if cur.fetchone():
            print("⚠️ 이미 존재하는 (keyword_id, related_keyword_id) 조합입니다. 삽입 안 함.")
            return

        # id 제외 필드명 추출
        item_fields = [f.name for f in fields(KeywordRelation) if f.name != "id"]
        field_str = ", ".join(item_fields)
        placeholders = ", ".join(["%s"] * len(item_fields))

        # SQL 구문
        sql = f"INSERT INTO keyword_relation ({field_str}) VALUES ({placeholders})"
        
        # 값 추출 (중요: news_cluster에서!)
        values = tuple(getattr(keyword_relation, f) for f in item_fields)

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

def create_news_article_relation(news_article_relation:NewsArticleRelation):
    try:
        # DB 연결
        conn = pymysql.connect(host='localhost',
                            user='root',
                            password='root',
                            db='newspectrum',
                            charset='utf8mb4')
        cur = conn.cursor()

        # 중복 확인 쿼리
        check_sql = """
        SELECT 1 FROM news_article_relation 
        WHERE news_article_id = %s AND related_news_article_id = %s
        LIMIT 1
        """
        cur.execute(check_sql, (news_article_relation.news_article_id, news_article_relation.related_news_article_id))
        if cur.fetchone():
            print("⚠️ 이미 존재하는 (news_article_id, related_news_article_id) 조합입니다. 삽입 안 함.")
            return

        # id 제외 필드명 추출
        item_fields = [f.name for f in fields(NewsArticleRelation) if f.name != "id"]
        field_str = ", ".join(item_fields)
        placeholders = ", ".join(["%s"] * len(item_fields))

        # SQL 구문
        sql = f"INSERT INTO news_article_relation ({field_str}) VALUES ({placeholders})"
        
        # 값 추출 (중요: news_cluster에서!)
        values = tuple(getattr(news_article_relation, f) for f in item_fields)

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