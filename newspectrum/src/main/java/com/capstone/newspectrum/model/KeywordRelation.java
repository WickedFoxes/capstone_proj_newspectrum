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

    @Column(name = "simularity")
    private float simularity;
}
