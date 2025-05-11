package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.service.SearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class SearchServiceTest {
    @Autowired
    private SearchService searchService;
    @Test
    void test_searchNewsArticleByTitle(){
        System.out.println("""
        ###########################################################################################
        TEST_search_news_article_by_title
        ###########################################################################################
        """);

        String title = "이재명";
        List<NewsArticleDTO> newsArticleDTOList = searchService.get_news_article_by_title(title);

        Assertions.assertNotNull(newsArticleDTOList);
        Assertions.assertFalse(newsArticleDTOList.isEmpty(), "검색 결과 없음.");

        for(NewsArticleDTO news : newsArticleDTOList){
            System.out.println("########### news_article_DTO##############");
            System.out.println("Title : "+ news.getTitle().replace("\n", ""));
            System.out.println("Domain : "+ news.getDomain());
            System.out.println("createdDate "+ news.getCreatedDate());
        }

        System.out.println("################ 조회된 뉴스 기사 수 ################");
        System.out.println(newsArticleDTOList.size() + "  개");
    }

    @Test
    void test_searchNewsArticleByContent(){
        System.out.println("""
        ###########################################################################################
        TEST_search_news_article_by_content
        ###########################################################################################
        """);
        String content = "이재명";
        List<NewsArticleDTO> newsArticleDTOList = searchService.get_news_article_by_content(content);

        Assertions.assertNotNull(newsArticleDTOList);
        Assertions.assertFalse(newsArticleDTOList.isEmpty(), "검색 결과 없음.");

        for(NewsArticleDTO news : newsArticleDTOList){
            System.out.println("########### news_article_DTO##############");
            System.out.println("Title : "+ news.getTitle().replace("\n", ""));
            System.out.println("Domain : "+ news.getDomain());
            System.out.println("createdDate "+ news.getCreatedDate());
        }

        System.out.println("################ 조회된 뉴스 기사 수 ################");
        System.out.println(newsArticleDTOList.size() + "  개");
    }
}
