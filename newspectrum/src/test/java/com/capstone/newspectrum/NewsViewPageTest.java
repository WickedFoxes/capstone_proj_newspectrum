package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.MainBlockDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.service.MainPageSevice;
import com.capstone.newspectrum.service.NewsArticleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
        Long news_id = 5162L;
        NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_id);
        System.out.println("########### news_article ##############");
        System.out.println("Domain : "+ news_article.getDomain());
        System.out.println("title : "+ news_article.getTitle());
        System.out.println("createdDate "+ news_article.getCreatedDate());
    }

    @Test
    void test_get_focus_keyword_items_by_id() {
        System.out.println("""
        ###########################################################################################
        get_focus_keyword_items_by_id
        ###########################################################################################
        """);
        Long news_id = 5162L;
        List<FocusKeywordItemDTO> items = newsArticleService.get_focus_keyword_items_by_id(news_id);
        for(FocusKeywordItemDTO item : items){
            System.out.println("########### FocusKeywordItemDTO ##############");
            System.out.println("keyword : "+ item.getKeyword());
            System.out.println("키워드 관련 뉴스들 : " + item.getKeyword_articles().size());
            for(NewsArticleDTO article : item.getKeyword_articles()){
                System.out.println(article.getCreatedDate()+" "+article.getTitle().replace("\n", ""));
            }

        }
    }

    @Test
    void test_get_related_news_articles_by_id() {
        System.out.println("""
        ###########################################################################################
        get_related_news_articles_by_id
        ###########################################################################################
        """);
        Long news_id = 5162L;
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
