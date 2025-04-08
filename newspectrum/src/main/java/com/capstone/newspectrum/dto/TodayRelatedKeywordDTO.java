package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TodayRelatedKeywordDTO {
    private LocalDateTime date;
    private List<String> keywords;
    private List<NewsArticleDTO> related_news;

    public TodayRelatedKeywordDTO(LocalDateTime date,
                                  List<String> keywords,
                                  List<NewsArticleDTO> related_news) {
        this.date = date;
        this.keywords = keywords;
        this.related_news = related_news;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<NewsArticleDTO> getRelated_news() {
        return related_news;
    }

    public void setRelated_news(List<NewsArticleDTO> related_news) {
        this.related_news = related_news;
    }
}
