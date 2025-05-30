package com.capstone.newspectrum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "keyword_relation")
public class KeywordRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name="related_keyword_id")
    private Keyword related_keyword;

    @Column(name = "similarity")
    private float similarity;

    public KeywordRelation() {
    }
    public KeywordRelation(Long id, Keyword keyword, Keyword related_keyword, float similarity) {
        this.id = id;
        this.keyword = keyword;
        this.related_keyword = related_keyword;
        this.similarity = similarity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public Keyword getRelated_keyword() {
        return related_keyword;
    }

    public void setRelated_keyword(Keyword related_keyword) {
        this.related_keyword = related_keyword;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}
