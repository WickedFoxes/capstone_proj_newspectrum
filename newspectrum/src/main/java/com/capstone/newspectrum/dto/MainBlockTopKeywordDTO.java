package com.capstone.newspectrum.dto;

import java.util.List;

public class MainBlockTopKeywordDTO {
    String keyword;
    List<MainBlockRelatedKeywordsDTO> related_keywords_timelines;

    public MainBlockTopKeywordDTO(String keyword,
                                  List<MainBlockRelatedKeywordsDTO> related_keywords_timelines) {
        this.keyword = keyword;
        this.related_keywords_timelines = related_keywords_timelines;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<MainBlockRelatedKeywordsDTO> getRelated_keywords_timelines() {
        return related_keywords_timelines;
    }

    public void setRelated_keywords_timelines(List<MainBlockRelatedKeywordsDTO> related_keywords_timelines) {
        this.related_keywords_timelines = related_keywords_timelines;
    }
}
