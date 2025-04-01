package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class NewsCluster {
    @Id
    @GeneratedValue
    private Long id;
    private Long clusterId;
    @OneToMany
    private List<NewsArticleDTO> news_article;
    @DateTimeFormat
    private LocalDateTime createdDate;
}
