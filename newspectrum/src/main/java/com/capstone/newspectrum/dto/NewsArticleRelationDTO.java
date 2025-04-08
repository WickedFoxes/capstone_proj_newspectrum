package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsArticleRelation;
import jakarta.persistence.*;

public class NewsArticleRelationDTO {
    private Long id;
    private NewsArticle news_article;
    private NewsArticle related_news_article;
    private float similarity;  // 유사도 점수 같은 부가 정보

    public NewsArticleRelationDTO() {}
    public NewsArticleRelationDTO(Long id,
                                  NewsArticle news_article,
                                  NewsArticle related_news_article,
                                  float similarity) {
        this.id = id;
        this.news_article = news_article;
        this.related_news_article = related_news_article;
        this.similarity = similarity;
    }
    public NewsArticleRelationDTO(NewsArticleRelation news_article_relation){
        this.id = news_article_relation.getId();
        this.news_article = news_article_relation.getNews_article();
        this.similarity = news_article_relation.getSimilarity();
        this.related_news_article = news_article_relation.getRelated_news_article();
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
