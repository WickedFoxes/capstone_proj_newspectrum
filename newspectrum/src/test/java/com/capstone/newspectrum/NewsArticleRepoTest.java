package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.ContentCheckDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.enumeration.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<NewsArticle> newsArticles = newsArticleRepo.findAllByTitleContainingOrderByCreatedDateDesc(keyword);

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

    @Test
    void testfindNewsArticlesHaveCluster(){
        System.out.println("""
        ###########################################################################################
        testfindNewsArticlesHaveCluster
        ###########################################################################################
        """);
        LocalDateTime start = LocalDateTime.of(2025, 5, 25, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 27, 23, 59);
        Domain domain = Domain.정치;

        List<NewsArticle> newsArticles = newsArticleRepo.findNewsArticlesHaveCluster(start, end, domain);

        for (NewsArticle article : newsArticles.subList(0, 5)){
            NewsArticleDTO dto = new NewsArticleDTO(article);
            if(!dto.getTitle_checks().isEmpty()){
                System.out.println("title : "+dto.getTitle());
                ContentCheckDTO contentCheckDTO1 = dto.getTitle_checks().get(0);
                ContentCheckDTO contentCheckDTO2 = dto.getTitle_checks().get(1);
                ContentCheckDTO contentCheckDTO3 = dto.getTitle_checks().get(2);
                System.out.println(contentCheckDTO1.getContent_check_type() + ":" + String.format("%.2f", contentCheckDTO1.getScore()));
                System.out.println(contentCheckDTO2.getContent_check_type() + ":" + String.format("%.2f", contentCheckDTO2.getScore()));
                System.out.println(contentCheckDTO3.getContent_check_type() + ":" + String.format("%.2f", contentCheckDTO3.getScore()));
            }
            Map<String, Map<String, List<ContentCheckDTO>>> content_checks = dto.getContent_checks();
            for (Map.Entry<String, Map<String, List<ContentCheckDTO>>> parent : content_checks.entrySet()) {
                String type = parent.getKey().replace("\n", " ");
                Map<String, List<ContentCheckDTO>> contents = parent.getValue();
                System.out.println("[type :"+type+"]" + contents.size());

                int cnt = 5;
                for(Map.Entry<String, List<ContentCheckDTO>> child : contents.entrySet()){
                    if(cnt <= 0) break;
                    cnt -= 1;
                    String keyword = child.getKey();
                    List<ContentCheckDTO> dtoList = child.getValue();

                    System.out.println("🔑 문장: " + keyword);
                    for (ContentCheckDTO dtoItem : dtoList) {
                        System.out.printf("  - %s: %.1f\n", dtoItem.getContent_check_type(), dtoItem.getScore());
                    }
                }
            }
        }
    }
}
