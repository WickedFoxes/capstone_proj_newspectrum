package com.capstone.newspectrum.dto;

import java.util.List;

public class TodayKeywordItemDTO {
    private String today_keyword;
    private int today_keyword_cnt;
    private List<TodayRelatedKeywordDTO> today_related_keywords;

    public TodayKeywordItemDTO(String today_keyword,
                               int today_keyword_cnt,
                               List<TodayRelatedKeywordDTO> today_related_keywords) {
        this.today_keyword = today_keyword;
        this.today_keyword_cnt = today_keyword_cnt;
        this.today_related_keywords = today_related_keywords;
    }

    public String getToday_keyword() {
        return today_keyword;
    }

    public void setToday_keyword(String today_keyword) {
        this.today_keyword = today_keyword;
    }

    public int getToday_keyword_cnt() {
        return today_keyword_cnt;
    }

    public void setToday_keyword_cnt(int today_keyword_cnt) {
        this.today_keyword_cnt = today_keyword_cnt;
    }

    public List<TodayRelatedKeywordDTO> getToday_related_keywords() {
        return today_related_keywords;
    }

    public void setToday_related_keywords(List<TodayRelatedKeywordDTO> today_related_keywords) {
        this.today_related_keywords = today_related_keywords;
    }
}
