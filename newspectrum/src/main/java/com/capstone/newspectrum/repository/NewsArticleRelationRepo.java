package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.model.NewsArticle;

import java.util.List;

public interface NewsArticleRelationRepo {
    List<NewsArticle> findBySimilarity(); //유사도 일정 이상인 것들 가져오기
}
