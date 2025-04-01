package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.NewsArticle;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class KeywordDTO {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn(name="newsArticle_id")
    private NewsArticle newsArticle_id;
    private String keyword;
    @DateTimeFormat
    private LocalDateTime createdDate;
    private List<NewsArticleDTO> news_article;
}
