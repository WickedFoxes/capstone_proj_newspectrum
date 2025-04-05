package com.capstone.newspectrum.service;

import com.capstone.newspectrum.model.KeywordRelation;
import com.capstone.newspectrum.repository.KeywordRelationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordRelationService {
    @Autowired
    private KeywordRelationRepo keywordRelationRepo;
    public List<KeywordRelation> getKeywordRelation(){
        List<KeywordRelation> keywordRelation = keywordRelationRepo.getKeywordRelation(); //하위 키워드 가져오는 내용 채워 넣을 것.
    }
}
