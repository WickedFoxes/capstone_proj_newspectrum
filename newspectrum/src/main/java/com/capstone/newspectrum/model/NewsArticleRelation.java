package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class NewsArticleRelation {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private List<NewsArticleDTO> news_article;
    @OneToMany
    private List<NewsArticleDTO> related_news_article;
    private float simularity;

}
