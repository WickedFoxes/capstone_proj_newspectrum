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
    public NewsArticle(NewsArticleDTO newsArticleDTO){
        this.title = newsArticleDTO.getTitle();
        this.content = newsArticleDTO.getContent();
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