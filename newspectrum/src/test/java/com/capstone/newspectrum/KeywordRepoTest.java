package com.capstone.newspectrum;

import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.repository.KeywordRepo;
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
public class KeywordRepoTest {
    @Autowired
    private KeywordRepo keywordRepo;
    @Test
    void testFindRelatedKeywordsByKeywordAndDate() {
        System.out.println("""
        ###########################################################################################
        findRelatedKeywordsByKeywordAndDate
        ###########################################################################################
        """);
        String key = "윤석열";
        LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 2, 7, 23, 59);
        Domain domain = Domain.정치;
        List<Keyword> keywords = keywordRepo.findRelatedKeywordsByKeywordAndDate(key, start, end);

        for (Keyword keyword : keywords) {
            System.out.println(keyword.getKeyword());
            assertTrue(
                    !keyword.getNews_article().getCreatedDate().isBefore(start)
                            && !keyword.getNews_article().getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + keyword.getNews_article().getCreatedDate()
            );
        }
    }


    @Test
    void testFindRelatedKeywordsByKeywordAndDateAndDomain() {
        System.out.println("""
        ###########################################################################################
        findRelatedKeywordsByKeywordAndDateAndDomain
        ###########################################################################################
        """);
        String key = "윤석열";
        LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 2, 7, 23, 59);
        Domain domain = Domain.정치;
        List<Keyword> keywords = keywordRepo.findRelatedKeywordsByKeywordAndDateAndDomain(key, start, end, domain);


        for (Keyword keyword : keywords) {
            System.out.println(keyword.getKeyword());
            assertTrue(
                    !keyword.getNews_article().getCreatedDate().isBefore(start)
                            && !keyword.getNews_article().getCreatedDate().isAfter(end),
                    "날짜 범위를 벗어남: " + keyword.getNews_article().getCreatedDate()
            );
        }
    }
}
