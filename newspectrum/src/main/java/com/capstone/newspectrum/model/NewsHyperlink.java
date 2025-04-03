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
}
