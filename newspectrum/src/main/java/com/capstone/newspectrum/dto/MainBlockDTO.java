package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MainBlockDTO {
    private List<NewsArticleDTO> news_articles;
    private int cluster_count;
    private List<String> keywords;
    private List<Integer> keywords_cnt;
    private List<MainBlockTopKeywordDTO> related_keywords_timeline;

    public MainBlockDTO(List<NewsArticleDTO> news_articles,
                        LocalDateTime created_date,
                        List<String> keywords,
                        List<Integer> keywords_cnt) {
        this.news_articles = news_articles;
        this.keywords = keywords;
        this.keywords_cnt = keywords_cnt;
    }

    public List<NewsArticleDTO> getNews_articles() {
        return news_articles;
    }

    public void setNews_articles(List<NewsArticleDTO> news_articles) {
        this.news_articles = news_articles;
    }

    public int getCluster_count() {
        return cluster_count;
    }

    public void setCluster_count(int cluster_count) {
        this.cluster_count = cluster_count;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<MainBlockTopKeywordDTO> getRelated_keywords_timeline() {
        return related_keywords_timeline;
    }

    public void setRelated_keywords_timeline(List<MainBlockTopKeywordDTO> related_keywords_timeline) {
        this.related_keywords_timeline = related_keywords_timeline;
    }

    public List<Integer> getKeywords_cnt() {
        return keywords_cnt;
    }

    public void setKeywords_cnt(List<Integer> keywords_cnt) {
        this.keywords_cnt = keywords_cnt;
    }
}