package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.enumeration.Domain;

import java.time.LocalDateTime;
import java.util.List;

public class MainBlockDTO {
    private Domain domain;
    private List<String> keywords;
    private List<Integer> keywords_cnt;
    private List<MainBlockTopKeywordDTO> main_block_top_keywords;

    public MainBlockDTO(Domain domain,
                        List<String> keywords,
                        List<Integer> keywords_cnt,
                        List<MainBlockTopKeywordDTO> main_block_top_keywords) {
        this.domain = domain;
        this.keywords = keywords;
        this.keywords_cnt = keywords_cnt;
        this.main_block_top_keywords = main_block_top_keywords;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<Integer> getKeywords_cnt() {
        return keywords_cnt;
    }

    public void setKeywords_cnt(List<Integer> keywords_cnt) {
        this.keywords_cnt = keywords_cnt;
    }

    public List<MainBlockTopKeywordDTO> getMain_block_top_keywords() {
        return main_block_top_keywords;
    }

    public void setMain_block_top_keywords(List<MainBlockTopKeywordDTO> main_block_top_keywords) {
        this.main_block_top_keywords = main_block_top_keywords;
    }
}