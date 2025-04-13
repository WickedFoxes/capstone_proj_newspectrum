package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long> {
    NewsArticle findById(long id);
    @Query("SELECT a.domain FROM NewsArticle a WHERE a.id = :articleId")
    Domain getDomain(NewsArticle article_id);
    @Query("SELECT na FROM NewsArticle na WHERE na.title LIKE '%:title%'")
    List<NewsArticle> findAllByTitle(String title);
    @Query("SELECT na FROM NewsArticle na WHERE na.content LIKE '%content%'")
    List<NewsArticle> findAllNewsArticleByContent(String content);
    @Query("SELECT na FROM NewsArticle na WHERE na.createdDate := start_date")
    List<NewsArticle> findAllByCreatedDate(@Param("startDate") LocalDateTime start_date);
    @Query("SELECT na FROM NewsArticle na WHERE na.createdDate :>= startDate AND createdDate :<= endDate")
    List<NewsArticle> findAllByPeriod(@Param("startDate")LocalDateTime start_date,
                                      @Param("endDate") LocalDateTime end_date);
    @Query("SELECT na FROM newsArticle WHERE ")
    List<NewsArticleDTO> findNewsArticleForNewsArticleDTO();
}
