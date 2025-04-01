package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class ContentCheck {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private NewsArticleDTO news_article;
    private String keyword;
    private CheckType content_check_type;

    enum CheckType {
        copyNews,
        poorTitle,
        poorContent,
        advertisement;
    }
}
