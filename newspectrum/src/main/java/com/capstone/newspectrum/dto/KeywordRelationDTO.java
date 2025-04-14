package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.KeywordRelation;

public class KeywordRelationDTO {
    private Long id;
    private String keyword;
    private String related_keyword;
    private float similarity;

    public KeywordRelationDTO() {}
    public KeywordRelationDTO(Long id,
                              Keyword keyword,
                              Keyword related_keyword,
                              float similarity) {
        this.id = id;
        this.keyword = keyword.getKeyword();
        this.related_keyword = related_keyword.getKeyword();
        this.similarity = similarity;
    }
    public KeywordRelationDTO(KeywordRelation keyword_relation){
        this.id = keyword_relation.getId();
        this.keyword = keyword_relation.getKeyword().getKeyword();
        this.related_keyword = keyword_relation.getRelated_keyword().getKeyword();
        this.similarity = keyword_relation.getSimilarity();
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

    public String getRelated_keyword() {
        return related_keyword;
    }

    public void setRelated_keyword(String related_keyword) {
        this.related_keyword = related_keyword;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}
