package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface KeywordRepo extends JpaRepository<Keyword, Long> {
    @Query("""
    SELECT DISTINCT k2 
    FROM KeywordRelation kr
    JOIN kr.keyword k1
    JOIN kr.related_keyword k2
    JOIN k2.news_article na
    WHERE k1.keyword = :keyword
      AND na.createdDate BETWEEN :startDate AND :endDate
      AND k1.news_article.id = na.id
    ORDER BY kr.similarity DESC
    """)
    List<Keyword> findRelatedKeywordsByKeywordAndDate(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
    SELECT DISTINCT k2 
    FROM KeywordRelation kr
    JOIN kr.keyword k1
    JOIN kr.related_keyword k2
    JOIN k2.news_article na
    WHERE k1.keyword = :keyword
      AND na.createdDate BETWEEN :startDate AND :endDate
      AND na.domain = :domain
      AND k1.news_article.id = na.id
    ORDER BY kr.similarity DESC
    """)
    List<Keyword> findRelatedKeywordsByKeywordAndDateAndDomain(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("domain") Domain domain
    );
}
