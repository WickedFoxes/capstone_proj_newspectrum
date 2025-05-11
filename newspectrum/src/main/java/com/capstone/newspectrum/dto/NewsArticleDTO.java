package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.enumeration.Media;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsArticleRelation;
import com.capstone.newspectrum.model.NewsHyperlink;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Media media;
    private Domain domain;
    private String href;
    private String img_url;
    private LocalDateTime createdDate;
    private List<Long> related_news_articles;
    private List<String> keywords;

    public NewsArticleDTO() {
    }
    public NewsArticleDTO(NewsArticle news_article){
        this.id = news_article.getId();
        this.title = news_article.getTitle();
        this.content = news_article.getContent();
        this.media = news_article.getMedia();
        this.domain = news_article.getDomain();
        this.href = news_article.getHref();
        this.img_url = news_article.getImg_url();
        this.createdDate = news_article.getCreatedDate();
        this.related_news_articles = new ArrayList<>();
        this.keywords = new ArrayList<>();

        for (NewsArticleRelation news_article_relation : news_article.getRelated_news_articles()){
            related_news_articles.add(news_article_relation.getNews_article().getId());
        }
        for (Keyword keyword : news_article.getKeywords()){
            this.keywords.add(keyword.getKeyword());
        }
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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Long> getRelated_news_articles() {
        return related_news_articles;
    }

    public void setRelated_news_articles(List<Long> related_news_articles) {
        this.related_news_articles = related_news_articles;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
