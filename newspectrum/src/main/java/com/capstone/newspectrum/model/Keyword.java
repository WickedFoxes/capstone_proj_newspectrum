package com.capstone.newspectrum.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "keyword")
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
}
