package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long> {
    NewsArticle findById(long id);
    List<NewsArticle> findAllByTitle(String title);
}
