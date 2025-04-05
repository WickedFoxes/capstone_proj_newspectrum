package com.capstone.newspectrum.service;

import com.capstone.newspectrum.model.NewsArticleRelation;
import com.capstone.newspectrum.repository.NewsArticleRelationRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatedNewsService {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    @Autowired
    private NewsArticleRelationRepo newsArticleRelationRepo;

    public List<NewsArticleRelation> getNewsArticleRelation(){
        newsArticleRelationRepo.findBySimilarity(); //Repo먼저 수정할 것.
    }
}
