package com.capstone.newspectrum.dto;

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
    private NewsArticle news_article;
    private List<KeywordRelationDTO> related_keywords;
    private LocalDateTime createdDate;

    public KeywordDTO() {}
    public KeywordDTO(Long id,
                      String keyword,
                      NewsArticle news_article,
                      List<KeywordRelation> related_keywords,
                      LocalDateTime createdDate) {
        this.id = id;
        this.keyword = keyword;
        this.news_article = news_article;
        this.related_keywords = new ArrayList<>();
        this.createdDate = createdDate;

        for (KeywordRelation keyword_relation : related_keywords){
            this.related_keywords.add(new KeywordRelationDTO(keyword_relation));
        }
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

    public NewsArticle getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticle news_article) {
        this.news_article = news_article;
    }

    public List<KeywordRelationDTO> getRelated_keywords() {
        return related_keywords;
    }

    public void setRelated_keywords(List<KeywordRelationDTO> related_keywords) {
        this.related_keywords = related_keywords;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
