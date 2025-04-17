package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long> {
    // id에 해당하는 NewsArticle
    NewsArticle findById(long id);

    // keyword를 포함하는 본문을 가지고 있는 List<NewsArticle>
    List<NewsArticle> findByContentContaining(String keyword);

    // keyword을 포함하는 제목을 가지고 있는 List<NewsArticle>
    List<NewsArticle> findByTitleContaining(String keyword);

    // created_date가 start_date와 end_date 사이에 있는 List<NewsArticle>
    List<NewsArticle> findByCreatedDateBetween(LocalDateTime startDate,
                                               LocalDateTime endDate);

    // created_date가 start_date와 end_date 사이에 있으며, Domain이 domain인 List<NewsArticle>
    List<NewsArticle> findByCreatedDateBetweenAndDomain(LocalDateTime startDate,
                                                        LocalDateTime endDate,
                                                        Domain domain);
}
