package com.capstone.newspectrum.repository;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsClusterRepo extends JpaRepository<NewsCluster, Long> {
    // newsArticle의 created_date가 start_date와 end_date 사이에 있는 List<NewsCluster>
    @Query("""
    SELECT nc 
    FROM NewsCluster nc 
    WHERE nc.news_article.createdDate BETWEEN :startDate AND :endDate  
    ORDER BY nc.clusterId 
    """)
    List<NewsCluster> findByNewsArticle_CreatedDateBetween(@Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate);

    // newsArticle의 created_date가 start_date와 end_date 사이에 있으며, Domain이 domain인 List<NewsCluster>
    @Query("""
        SELECT nc 
        FROM NewsCluster nc 
        WHERE nc.news_article.createdDate BETWEEN :startDate AND :endDate 
        AND nc.news_article.domain = :domain 
        ORDER BY nc.clusterId 
    """)
    List<NewsCluster> findByNewsArticle_CreatedDateBetweenAndDomain(@Param("startDate") LocalDateTime startDate,
                                                                    @Param("endDate") LocalDateTime endDate,
                                                                    @Param("domain") Domain domain);
}
