package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.NewsArticle;
import jakarta.persistence.Column;

public class NewsArticleDTO {
    private Long id;
    private String title;
    private String content;

    public NewsArticleDTO() {
    }
    public NewsArticleDTO(String title, String content){
        this.title = title;
        this.content=content;
    }
    public NewsArticleDTO(NewsArticle news_article){
        this.id = news_article.getId();
        this.title = news_article.getTitle();
        this.content = news_article.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
