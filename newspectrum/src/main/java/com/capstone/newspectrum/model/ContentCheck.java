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
}
