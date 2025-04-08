package com.capstone.newspectrum.repository.relation;

import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.KeywordRelation;
import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRelationRepo extends JpaRepository<NewsArticle, Long> {
    @Query("SELECT rk.KeywordRelation FROM KeywordRelation rk" +
            "WHERE rk.keyword.id = :keywordID AND rk.similarity >= threshold")
    List<KeywordRelation> getKeywordRelationBySimilarity(Keyword keywordID, double threshold); //하위 키워드 가져오기
}
