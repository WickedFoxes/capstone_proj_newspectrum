package com.capstone.newspectrum.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_cluster")
public class NewsCluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cluster_id")
    private String clusterId;

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle news_article;

    @DateTimeFormat
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public NewsCluster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public NewsArticle getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticle news_article) {
        this.news_article = news_article;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
