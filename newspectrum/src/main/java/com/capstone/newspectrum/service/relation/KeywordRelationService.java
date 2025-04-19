package com.capstone.newspectrum.service.relation;

import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.KeywordRelation;
import com.capstone.newspectrum.repository.relation.KeywordRelationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KeywordRelationService {
    @Autowired
    private KeywordRelationRepo keywordRelationRepo;
    public List<KeywordRelation> getKeywordRelation(Keyword keyword, double threshold){
        List<KeywordRelation> keywordRelation = keywordRelationRepo.getKeywordRelationBySimilarity(keyword, threshold);//하위 키워드 가져오는 내용 채워 넣을 것.
        return keywordRelation;
    }
}
