package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.MainBlockDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.service.MainPageSevice;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class MainPageServiceTest {
    @Autowired
    private MainPageSevice mainPageSevice;

    @Test
    void test_get_main_block_list() {
        System.out.println("""
        ###########################################################################################
        get_main_block_list
        ###########################################################################################
        """);
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 0, 0);
        Map<String, MainBlockDTO> items = mainPageSevice.get_main_block_list(today);
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
}
