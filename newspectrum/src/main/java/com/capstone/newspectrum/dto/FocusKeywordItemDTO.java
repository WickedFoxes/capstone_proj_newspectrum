package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.Keyword;

import java.time.LocalDateTime;
import java.util.List;

public class FocusKeywordItemDTO {
    private String keyword;
    private List<NewsArticleDTO> keyword_articles;
    private List<Float> scores;

    public FocusKeywordItemDTO(String keyword,
                               List<NewsArticleDTO> keyword_articles) {
        this.keyword = keyword;
        this.keyword_articles = keyword_articles;
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
