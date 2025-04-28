package com.capstone.newspectrum.dto;

public class RelatedNewsArticleAndScoreDTO {
    private NewsArticleDTO news_article;
    private float score;

    public RelatedNewsArticleAndScoreDTO(NewsArticleDTO news_article, float score) {
        this.news_article = news_article;
        this.score = score;
    }

    public NewsArticleDTO getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticleDTO news_article) {
        this.news_article = news_article;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
