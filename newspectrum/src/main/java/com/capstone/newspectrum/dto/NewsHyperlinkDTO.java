package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsHyperlink;

public class NewsHyperlinkDTO {
    private Long id;
    private NewsArticleDTO news_article;
    private NewsArticleDTO related_news_article;
    private String content;

    public NewsHyperlinkDTO(Long id,
                            NewsArticleDTO news_article,
                            NewsArticleDTO related_news_article,
                            String content) {
        this.id = id;
        this.news_article = news_article;
        this.related_news_article = related_news_article;
        this.content = content;
    }

    public NewsHyperlinkDTO(NewsHyperlink news_hyperlink) {
        this.id = news_hyperlink.getId();
        this.news_article = new NewsArticleDTO(news_hyperlink.getNews_article());
        this.related_news_article = new NewsArticleDTO(news_hyperlink.getRelated_news_article());
        this.content = news_hyperlink.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsArticleDTO getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticleDTO news_article) {
        this.news_article = news_article;
    }

    public NewsArticleDTO getRelated_news_article() {
        return related_news_article;
    }

    public void setRelated_news_article(NewsArticleDTO related_news_article) {
        this.related_news_article = related_news_article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
