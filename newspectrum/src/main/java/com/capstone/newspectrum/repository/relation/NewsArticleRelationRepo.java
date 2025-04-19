package com.capstone.newspectrum.repository.relation;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsArticleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsArticleRelationRepo extends JpaRepository<NewsArticle, Long> {
    @Query("SELECT ra.NewsArticleRelation FROM NewsArticleRelation ra" +
    "WHERE ra.article.id = :articleID AND ra.similarity >= threshold")
    List<NewsArticleRelation> getArticleRelationBySimilarity(
            @Param("articleID") NewsArticle articleID,
            @Param("threshold") double threshold
    ); //유사도 threshold 이상인 것들 가져오기
    @Query("SELECT a2 FROM NewsArticleRelation ra" +
    "JOIN NewsArticle a1 ON ra.newsArticle.id = a1.id" +
    "JOIN NewsArticle a2 ON ra.NewsArticleRelationid = a2.id" +
    "WHERE a1.id =: newsArticleID")
    List<NewsArticleRelation> getArticleRelaiontByArticleId(Long articleID);
}
