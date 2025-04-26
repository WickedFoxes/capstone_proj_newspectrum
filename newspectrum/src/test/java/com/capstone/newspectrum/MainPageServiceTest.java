package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.MainBlockDTO;
import com.capstone.newspectrum.service.MainPageSevice;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

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
        LocalDateTime today = LocalDateTime.of(2025, 3, 1, 0, 0);
        List<MainBlockDTO> items = mainPageSevice.get_main_block_list(today);
        System.out.println(items.size());
        for(MainBlockDTO item : items){
            System.out.println("########### cluster ##############");
            System.out.println("domain : "+ item.getDomain());
            System.out.println("cluster cnt : "+ item.getCluster_count());
            System.out.println("aritcles cnt : "+ item.getNews_articles().size());
            System.out.println("keywords : "+ item.getKeywords().subList(0, 20));
            System.out.println("keywords cnt : "+ item.getKeywords_cnt().subList(0, 20));
            System.out.println("block_top_keywords cnt : "+ item.getMain_block_top_keywords().size());
            System.out.println("가장 빈도수가 높은 키워드 : "+ item.getMain_block_top_keywords().get(0)
                    .getKeyword());
            System.out.println("가장 빈도수가 높은 키워드의 첫 주의 EndTime : "+ item.getMain_block_top_keywords().get(0)
                    .getRelated_keywords_timelines().get(0)
                    .getEnd_time());
            System.out.println("가장 빈도수가 높은 키워드의 첫 주의 StartTime : "+ item.getMain_block_top_keywords().get(0)
                    .getRelated_keywords_timelines().get(0)
                    .getStart_time());
            System.out.println("가장 빈도수가 높은 키워드의 첫 주의 연관 키워드 : "+ item.getMain_block_top_keywords().get(0)
                    .getRelated_keywords_timelines().get(0)
                    .getRelated_keywords());
        }
    }
}
