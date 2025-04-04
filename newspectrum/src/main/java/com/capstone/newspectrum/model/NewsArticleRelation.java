package com.capstone.newspectrum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "news_article_relation")
public class NewsArticleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle news_article;

    @ManyToOne
    @JoinColumn(name = "related_news_article_id")
    private NewsArticle related_news_article;

    @Column(name = "similarity")
    private float similarity;  // 유사도 점수 같은 부가 정보
}
