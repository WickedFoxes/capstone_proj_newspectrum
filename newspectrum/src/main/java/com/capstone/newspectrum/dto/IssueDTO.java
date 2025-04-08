package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IssueDTO {
    private int cluster_cnt;
    private String cluster_title;
    private List<NewsArticleDTO> news_articles;
    private LocalDateTime created_date;

    public IssueDTO(int cluster_cnt,
                    String cluster_title,
                    List<NewsArticleDTO> news_articles,
                    LocalDateTime created_date
    ){
        this.cluster_cnt = cluster_cnt;
        this.cluster_title = cluster_title;
        this.news_articles = news_articles;
        this.created_date = created_date;
    }

    public int getCluster_cnt() {
        return cluster_cnt;
    }

    public void setCluster_cnt(int cluster_cnt) {
        this.cluster_cnt = cluster_cnt;
    }

    public String getCluster_title() {
        return cluster_title;
    }

    public void setCluster_title(String cluster_title) {
        this.cluster_title = cluster_title;
    }

    public List<NewsArticleDTO> getNews_articles() {
        return news_articles;
    }

    public void setNews_articles(List<NewsArticleDTO> news_articles) {
        this.news_articles = news_articles;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }
}