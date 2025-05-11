package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long> {
    // id에 해당하는 NewsArticle
    NewsArticle findById(long id);

    // keyword를 포함하는 본문을 가지고 있는 List<NewsArticle>
    List<NewsArticle> findAllByContentContaining(String keyword);

    // keyword을 포함하는 제목을 가지고 있는 List<NewsArticle>
    List<NewsArticle> findAllByTitleContaining(String keyword);

    // created_date가 start_date와 end_date 사이에 있는 List<NewsArticle>
    List<NewsArticle> findByCreatedDateBetween(LocalDateTime startDate,
                                               LocalDateTime endDate);

    // created_date가 start_date와 end_date 사이에 있으며, Domain이 domain인 List<NewsArticle>
    List<NewsArticle> findByCreatedDateBetweenAndDomain(LocalDateTime startDate,
                                                        LocalDateTime endDate,
                                                        Domain domain);

    @Query("""
    SELECT k.news_article
    FROM Keyword k
    WHERE k.keyword IN (:keyword1, :keyword2)
      AND k.news_article.createdDate BETWEEN :startDate AND :endDate
      AND k.news_article.domain = :domain
    GROUP BY k.news_article
    HAVING COUNT(DISTINCT k.keyword) = 2
    """)
    List<NewsArticle> findNewsArticlesWithBothKeywords(@Param("keyword1") String keyword1,
                                                       @Param("keyword2") String keyword2,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate,
                                                       @Param("domain") Domain domain);

    @Query("""
    SELECT k.news_article
    FROM Keyword k
    WHERE k.keyword = :keyword
      AND k.news_article.createdDate BETWEEN :startDate AND :endDate
      AND k.news_article.domain = :domain
    GROUP BY k.news_article
    """)
    List<NewsArticle> findNewsArticlesByKeyword(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("domain") Domain domain
    );


}

