package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.Keyword;

import java.time.LocalDateTime;
import java.util.List;

public class FocusKeywordItemDTO {
    private Long id;
    private String keyword;
    private List<NewsArticleDTO> keyword_articles;

    public FocusKeywordItemDTO(Long id, String keyword,
                               List<NewsArticleDTO> keyword_articles) {
        this.id = id;
        this.keyword = keyword;
        this.keyword_articles = keyword_articles;
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

    public List<NewsArticleDTO> getKeyword_articles() {
        return keyword_articles;
    }

    public void setKeyword_articles(List<NewsArticleDTO> keyword_articles) {
        this.keyword_articles = keyword_articles;
    }
}
