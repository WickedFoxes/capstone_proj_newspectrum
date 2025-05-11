package com.capstone.newspectrum;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.enumeration.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class NewsArticleRepoTest {
    @Autowired
    private NewsArticleRepo newsArticleRepo;

    @Test
    void testFindAllByTitle() {
        System.out.println("""
        ###########################################################################################
        testFindByTitle
        ###########################################################################################
        """);
        String keyword = "이재명";

        // when
        List<NewsArticle> newsArticles = newsArticleRepo.findAllByTitleContaining(keyword);

        // then
        for (NewsArticle article : newsArticles.subList(0, 10)) {
            System.out.println(article.getTitle().replace("\n", ""));
            assertTrue(
                    article.getTitle().contains(keyword),
                    "제목에 '"+keyword+"' 포함되지 않음: " + article.getTitle()
            );
        }
    }

    @Test
    void testFindByCreatedDateBetween() {
        System.out.println("""
        ###########################################################################################
        testFindByCreatedDateBetween
        ###########################################################################################
        """);
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);

        List<NewsArticle> newsArticles = newsArticleRepo.findByCreatedDateBetween(start, end);

        for (NewsArticle article : newsArticles.subList(0, Math.min(10, newsArticles.size()))) {
            System.out.println(article.getCreatedDate() + " | " + article.getTitle().replace("\n", ""));
            assertTrue(
                    !article.getCreatedDate().isBefore(start) && !article.getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + article.getCreatedDate()
            );
        }
    }

    @Test
    void testFindByCreatedDateBetweenAndDomain() {
        System.out.println("""
        ###########################################################################################
        testFindByCreatedDateBetweenAndDomain
        ###########################################################################################
        """);
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);
        Domain domain = Domain.정치;

        List<NewsArticle> newsArticles = newsArticleRepo.findByCreatedDateBetweenAndDomain(start, end, domain);

        for (NewsArticle article : newsArticles.subList(0, Math.min(10, newsArticles.size()))) {
            System.out.println(article.getCreatedDate() + " | " + article.getDomain() + " | " + article.getTitle().replace("\n", ""));
            assertTrue(
                    !article.getCreatedDate().isBefore(start) && !article.getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + article.getCreatedDate()
            );
            assertEquals(domain, article.getDomain(), "도메인이 다름: " + article.getDomain());
        }
    }
}
