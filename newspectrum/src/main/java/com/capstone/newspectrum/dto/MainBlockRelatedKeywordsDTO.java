package com.capstone.newspectrum.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MainBlockRelatedKeywordsDTO {
    LocalDateTime start_time;
    LocalDateTime end_time;
    List<String> related_keywords;

    public MainBlockRelatedKeywordsDTO(LocalDateTime start_time,
                                       LocalDateTime end_time,
                                       List<String> related_keywords) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.related_keywords = related_keywords;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public List<String> getRelated_keywords() {
        return related_keywords;
    }

    public void setRelated_keywords(List<String> related_keywords) {
        this.related_keywords = related_keywords;
    }
}
