package com.capstone.newspectrum.service.relation;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsArticleRelation;
import com.capstone.newspectrum.repository.relation.NewsArticleRelationRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsArticleRelationService {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    @Autowired
    private NewsArticleRelationRepo newsArticleRelationRepo;
    @Autowired
    public NewsArticleRelationService(NewsArticleRepo newsArticleRepo){
        this.newsArticleRepo = newsArticleRepo;
    }
    @Autowired
    public NewsArticleRelationService(NewsArticleRelationRepo newsArticleRelationRepo){
        this.newsArticleRelationRepo = newsArticleRelationRepo;
    }
    public List<NewsArticleRelation> getNewsArticleRelation(NewsArticle articleID, double threshold){
        return newsArticleRelationRepo.getArticleRelationBySimilarity(articleID, threshold);
    }
}
