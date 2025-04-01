package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "news_article")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private Media media;
    private Domain domain;

    private String href;
    private String img_url;
    @DateTimeFormat
    private Date createdDate;

    public NewsArticle(){}
    public NewsArticle(NewsArticleDTO newsArticleDTO){
        this.title = newsArticleDTO.getTitle();
        this.content = newsArticleDTO.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    enum Media{
        sbs, mbc, kbs, mbn, jtbc, 중앙일보, 조선일보;
    }
    enum Domain{
        정치, 경제, 사회, 과학, 스포츠, 연애;
    }
}