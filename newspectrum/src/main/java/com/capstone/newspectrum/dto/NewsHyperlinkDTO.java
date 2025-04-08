package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsHyperlink;

public class NewsHyperlinkDTO {
    private Long id;
    private NewsArticle news_article;
    private NewsArticle related_news_article;
    private String content;

    public NewsHyperlinkDTO(Long id,
                            NewsArticle news_article,
                            NewsArticle related_news_article,
                            String content) {
        this.id = id;
        this.news_article = news_article;
        this.related_news_article = related_news_article;
        this.content = content;
    }

    public NewsHyperlinkDTO(NewsHyperlink news_hyperlink) {
        this.id = news_hyperlink.getId();
        this.news_article = news_hyperlink.getNews_article();
        this.related_news_article = news_hyperlink.getRelated_news_article();
        this.content = news_hyperlink.getContent();
    }
}
