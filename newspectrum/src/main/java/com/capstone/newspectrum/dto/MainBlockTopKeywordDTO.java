package com.capstone.newspectrum.dto;

import java.util.List;

public class MainBlockTopKeywordDTO {
    private String keyword;
    private List<NewsArticleDTO> news_articles;

    public MainBlockTopKeywordDTO(String keyword, List<NewsArticleDTO> news_articles) {
        this.keyword = keyword;
        this.news_articles = news_articles;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<NewsArticleDTO> getNews_articles() {
        return news_articles;
    }

    public void setNews_articles(List<NewsArticleDTO> news_articles) {
        this.news_articles = news_articles;
    }
}
