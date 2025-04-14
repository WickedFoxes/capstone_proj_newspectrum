package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public interface NewsClusterRepo extends JpaRepository<NewsCluster, Long> {
    @Query("SELECT na FROM NewsCluster nc WHERE nc.createdDate BETWEEN :startDate AND :endDate")
    List<NewsCluster> findNewsClusterByCreatedDate(@Param("startDate") LocalDateTime start_time,
                                                   @Param("endDate")LocalDateTime end_time);
    @Query(value = "SELECT na.* FROM news_article na " +
            "LEFT JOIN news_cluster_mapping ncm ON na.id = ncm.news_article_id " +
            "WHERE ncm.news_cluster_id = :clusterId", nativeQuery = true)
    List<NewsArticle> getNewsArticle(@Param("clusterId") Long clusterId);
}
