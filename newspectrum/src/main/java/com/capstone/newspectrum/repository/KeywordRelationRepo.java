package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.model.KeywordRelation;

import java.util.List;

public interface KeywordRelationRepo {
    List<KeywordRelation> getKeywordRelation(); //하위키워드들 가져오기
}
