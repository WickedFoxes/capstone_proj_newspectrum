package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<NewsArticleDTO> news_article;
    private String keyword;
    @DateTimeFormat
    private LocalDateTime createdDate;
}
