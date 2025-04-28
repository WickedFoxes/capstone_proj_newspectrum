package com.capstone.newspectrum.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "keyword", indexes = {
        @Index(name = "idx_keyword_keyword", columnList = "keyword"),
        @Index(name = "idx_keyword_news_article_id", columnList = "news_article_id")
})
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle news_article;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<KeywordRelation> related_keywords;

    @DateTimeFormat
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "score")
    private float score;

    public Keyword() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public NewsArticle getNews_article() {
        return news_article;
    }

    public void setNews_article(NewsArticle news_article) {
        this.news_article = news_article;
    }

    public List<KeywordRelation> getRelated_keywords() {
        return related_keywords;
    }

    public void setRelated_keywords(List<KeywordRelation> related_keywords) {
        this.related_keywords = related_keywords;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
