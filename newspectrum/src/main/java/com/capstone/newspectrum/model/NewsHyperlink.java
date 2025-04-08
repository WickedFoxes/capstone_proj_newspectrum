package com.capstone.newspectrum.model;

import jakarta.persistence.*;

@Entity
public class NewsHyperlink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle news_article;

    @ManyToOne
    @JoinColumn(name = "related_news_article_id")
    private NewsArticle related_news_article;

    @Column(name = "content")
    private String content;

    public NewsHyperlink() {
    }
    public NewsHyperlink(Long id, NewsArticle news_article, NewsArticle related_news_article, String content) {
        this.id = id;
        this.news_article = news_article;
        this.related_news_article = related_news_article;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsArticle getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticle news_article) {
        this.news_article = news_article;
    }

    public NewsArticle getRelated_news_article() {
        return related_news_article;
    }

    public void setRelated_news_article(NewsArticle related_news_article) {
        this.related_news_article = related_news_article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
