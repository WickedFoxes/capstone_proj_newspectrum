package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.enumeration.Media;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "news_article")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "media")
    @Enumerated(EnumType.STRING)
    private Media media;

    @Column(name = "domain")
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Column(name = "href")
    private String href;

    @Column(name = "img_url")
    private String img_url;

    @DateTimeFormat
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "news_article", cascade = CascadeType.ALL)
    private List<NewsArticleRelation> related_news_articles;

    @OneToMany(mappedBy = "news_article", cascade = CascadeType.ALL)
    private List<NewsHyperlink> news_hyperlinks;

    public NewsArticle(){}

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

    public List<NewsArticleRelation> getRelated_news_articles() {
        return related_news_articles;
    }

    public void setRelated_news_articles(List<NewsArticleRelation> related_news_articles) {
        this.related_news_articles = related_news_articles;
    }

    public List<NewsHyperlink> getNews_hyperlinks() {
        return news_hyperlinks;
    }

    public void setNews_hyperlinks(List<NewsHyperlink> news_hyperlinks) {
        this.news_hyperlinks = news_hyperlinks;
    }
}