package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.service.NewsArticleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class NewsViewPageTest {
    @Autowired
    private NewsArticleService newsArticleService;

    @Test
    void test_get_news_article_by_id() {
        System.out.println("""
        ###########################################################################################
        get_news_article_by_id
        ###########################################################################################
        """);
        Long news_id = 28480L;
        NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_id);
        System.out.println("########### news_article ##############");
        System.out.println("Domain : "+ news_article.getDomain());
        System.out.println("title : "+ news_article.getTitle());
        System.out.println("createdDate "+ news_article.getCreatedDate());
    }

    @Test
    void test_get_keyword_items_by_id() {
        System.out.println("""
        ###########################################################################################
        get_focus_keyword_items_by_id
        ###########################################################################################
        """);
        Long news_id = 28480L;
        List<KeywordDTO> items = newsArticleService.get_keyword_items_by_id(news_id);
        for(KeywordDTO item : items){
            System.out.println("########### FocusKeywordItemDTO ##############");
            System.out.println("keyword : "+ item.getKeyword());
            System.out.println("score : "+ item.getScore());
        }
    }

    @Test
    void test_get_related_news_articles_by_id() {
        System.out.println("""
        ###########################################################################################
        get_related_news_articles_by_id
        ###########################################################################################
        """);
        Long news_id = 28480L;
        List<RelatedNewsArticleAndScoreDTO> items = newsArticleService.get_related_news_articles_by_id(news_id);
        for(RelatedNewsArticleAndScoreDTO item : items){
            System.out.println("########### news_article ##############");
            System.out.println("Domain : "+ item.getNews_article().getDomain());
            System.out.println("score : "+ item.getScore());
            System.out.println("title : "+ item.getNews_article().getTitle().replace("\n", ""));
            System.out.println("createdDate "+ item.getNews_article().getCreatedDate());
        }
    }
}
