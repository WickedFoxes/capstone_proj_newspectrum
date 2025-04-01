package com.capstone.newspectrum.model;

import com.capstone.newspectrum.dto.KeywordDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class KeywordRelation {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="keyword")
    private Keyword keyword;
    @OneToMany
    private List<KeywordDTO> related_keywords;
    private float simularity;
}
