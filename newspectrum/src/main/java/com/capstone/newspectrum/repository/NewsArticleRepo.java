package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long> {
    NewsArticle findById(long id);
    List<NewsArticle> findAllNewsArticleByTitle(String title);
    List<NewsArticle> findAllNewsArticleByContent();
    List<NewsArticle> findAllNewsArticleByKeyword();
    List<NewsArticle> findAllNewArticleByIssue(); //이슈로 검색하는 부분 생각해보기

}
