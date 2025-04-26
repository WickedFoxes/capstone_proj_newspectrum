package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TodayRelatedKeywordDTO {
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<String> keywords;
    private List<NewsArticleDTO> related_news;

    public TodayRelatedKeywordDTO(LocalDateTime start_date,
                                  LocalDateTime end_date,
                                  List<String> keywords,
                                  List<NewsArticleDTO> related_news) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.keywords = keywords;
        this.related_news = related_news;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
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
