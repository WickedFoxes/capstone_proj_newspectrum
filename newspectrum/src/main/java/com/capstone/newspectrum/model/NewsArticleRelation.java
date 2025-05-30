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

    public NewsArticleRelation() {}

    public NewsArticleRelation(Long id,
                               NewsArticle news_article,
                               NewsArticle related_news_article,
                               float similarity) {
        this.id = id;
        this.news_article = news_article;
        this.related_news_article = related_news_article;
        this.similarity = similarity;
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

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}
