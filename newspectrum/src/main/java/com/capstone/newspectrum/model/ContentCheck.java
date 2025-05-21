package com.capstone.newspectrum.model;

import com.capstone.newspectrum.enumeration.CheckType;
import jakarta.persistence.*;

public class ContentCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle news_article;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "content_check_type")
    @Enumerated(EnumType.STRING)
    private CheckType content_check_type;
    @Column(name = "score")
    private Float score;

    public ContentCheck() {
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public CheckType getContent_check_type() {
        return content_check_type;
    }
    public void setContent_check_type(CheckType content_check_type) {
        this.content_check_type = content_check_type;
    }
    public Float getScore(){
        return score;
    }
    public void setScore(Float score){
        this.score = score;
    }
}
