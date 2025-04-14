package com.capstone.newspectrum;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.repository.NewsClusterRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class NewsClusterRepoTest {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    @Autowired
    private NewsClusterRepo newsClusterRepo;

    @Test
    void testFindByArticleCreatedDateBetween() {
        System.out.println("""
        ###########################################################################################
        findByNewsArticle_CreatedDateBetween
        ###########################################################################################
        """);
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);
        List<NewsCluster> newsClusters = newsClusterRepo.findByNewsArticle_CreatedDateBetween(start, end);


        for (NewsCluster newsCluster : newsClusters.subList(0, Math.min(10, newsClusters.size()))) {
            NewsArticle article = newsCluster.getNews_article();
            System.out.println(newsCluster.getClusterId() + " | " + article.getCreatedDate() + " | " + article.getTitle().replace("\n", ""));
            assertTrue(
                    !article.getCreatedDate().isBefore(start)
                            && !article.getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + article.getCreatedDate()
            );
        }
    }

    @Test
    void testFindByArticleCreatedDateBetweenAndDomain() {
        System.out.println("""
        ###########################################################################################
        findByNewsArticle_CreatedDateBetween
        ###########################################################################################
        """);
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);
        Domain domain = Domain.정치;
        List<NewsCluster> newsClusters = newsClusterRepo.findByNewsArticle_CreatedDateBetweenAndDomain(start, end, domain);


        for (NewsCluster newsCluster : newsClusters.subList(0, Math.min(10, newsClusters.size()))) {
            NewsArticle article = newsCluster.getNews_article();
            System.out.println(newsCluster.getClusterId() + " | " + article.getCreatedDate() + " | " + article.getTitle().replace("\n", ""));
            assertTrue(
                    !article.getCreatedDate().isBefore(start)
                            && !article.getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + article.getCreatedDate()
            );
            assertEquals(domain, article.getDomain(), "도메인이 다름: " + article.getDomain());
        }
    }
}
