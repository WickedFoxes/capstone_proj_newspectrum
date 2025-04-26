package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.KeywordRelation;
import com.capstone.newspectrum.model.NewsArticle;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KeywordDTO {
    private Long id;
    private String keyword;
    private Long news_article_id;  // ID만 보존
    private LocalDateTime createdDate;
    private float score;

    public KeywordDTO() {}

    public KeywordDTO(Long id,
                      String keyword,
                      Long news_article_id,
                      LocalDateTime createdDate,
                      float score) {
        this.id = id;
        this.keyword = keyword;
        this.news_article_id = news_article_id;
        this.createdDate = createdDate;
        this.score = score;
    }
    public KeywordDTO(Keyword keyword){
        this.id = keyword.getId();
        this.keyword = keyword.getKeyword();
        this.news_article_id = keyword.getNews_article().getId();
        this.createdDate = keyword.getCreatedDate();
        this.score = keyword.getScore();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getNews_article_id() {
        return news_article_id;
    }

    public void setNews_article_id(Long news_article_id) {
        this.news_article_id = news_article_id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
