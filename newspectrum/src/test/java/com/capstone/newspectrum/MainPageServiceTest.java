package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.MainBlockDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.MainPageService;
import com.capstone.newspectrum.service.NewsArticleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class MainPageServiceTest {
    @Autowired
    private MainPageService mainPageService;
    @Autowired
    private NewsArticleService newsArticleService;

    @Test
    void test_get_main_block_list() {
        System.out.println("""
        ###########################################################################################
        get_main_block_list
        ###########################################################################################
        """);
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 0, 0);
        Map<String, MainBlockDTO> items = mainPageService.get_main_block_list(today);
        System.out.println(items.size());
        items.forEach((domain, item) -> {
            System.out.println("############## " + item.getDomain() +" ##############");
            System.out.println("keywords : "+ item.getKeywords());
            System.out.println("keywords cnt : "+ item.getKeywords_cnt());
            int index = 0;
            System.out.println(
                    "keyword " +index +"번째 [" + item.getMain_block_top_keywords().get(index).getKeyword() + "] 연관 뉴스 : "
            );
            for(NewsArticleDTO newsArticleDTO : item.getMain_block_top_keywords().get(index).getNews_articles()){
                System.out.println(" - " + newsArticleDTO.getTitle().replace("\n", ""));
            }
        });
    }

    //도메인별 뉴스 만화 10개씩 가져오기
    @Test
    void testGetNewsArticleDTOForComics(){
        System.out.println("""
        ###########################################################################################
        TEST_search_news_article_by_domain_for_comics
        ###########################################################################################
        """);
        Domain domain = Domain.정치;
        List<NewsArticleDTO> news_Article_for_comics = newsArticleService.get_news_article_by_domain_for_comics(domain);

        for(NewsArticleDTO news : news_Article_for_comics){
            System.out.println("########### news_article_DTO_by_comics ##############");
            System.out.println("Title : "+ news.getTitle().replace("\n", ""));
            System.out.println("Domain : "+ news.getDomain());
            System.out.println("createdDate "+ news.getCreatedDate());
        }
        System.out.println("################ 총 뉴스 " + news_Article_for_comics.size() + "개 ###############");
    }
}
