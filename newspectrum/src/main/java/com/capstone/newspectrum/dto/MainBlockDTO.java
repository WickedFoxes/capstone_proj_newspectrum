package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MainBlockDTO {
    private List<NewsArticleDTO> news_articles;
    private int cluster_count;
    private List<String> keywords;
    private List<Integer> keywords_cnt;
    private List<MainBlockTopKeywordDTO> main_block_top_keywords;

    public MainBlockDTO(List<NewsArticleDTO> news_articles,
                        int cluster_count,
                        List<String> keywords,
                        List<Integer> keywords_cnt,
                        List<MainBlockTopKeywordDTO> main_block_top_keywords) {
        this.news_articles = news_articles;
        this.cluster_count = cluster_count;
        this.keywords = keywords;
        this.keywords_cnt = keywords_cnt;
        this.main_block_top_keywords = main_block_top_keywords;
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

    public List<Integer> getKeywords_cnt() {
        return keywords_cnt;
    }

    public void setKeywords_cnt(List<Integer> keywords_cnt) {
        this.keywords_cnt = keywords_cnt;
    }

    public List<MainBlockTopKeywordDTO> getMain_block_top_keywords() {
        return main_block_top_keywords;
    }

    public void setMain_block_top_keywords(List<MainBlockTopKeywordDTO> main_block_top_keywords) {
        this.main_block_top_keywords = main_block_top_keywords;
    }
}