package com.capstone.newspectrum;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.dto.TodayRelatedKeywordDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
import com.capstone.newspectrum.repository.KeywordRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.repository.NewsClusterRepo;
import com.capstone.newspectrum.service.MainPageSevice;
import com.capstone.newspectrum.service.SectionPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class SectionPageServiceTest {
    @Autowired
    private SectionPageService sectionPageService;

    @Test
    void getIssueByDomainTest(){
        System.out.println("""
                ######################################################################################
                getIssueTest
                ######################################################################################
                """);
        LocalDateTime start = LocalDateTime.of(2025, 2, 27, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);
        Domain domain = Domain.경제;


        List<IssueDTO> issue = sectionPageService.get_issue_by_domain(start, end, domain);
        //###########issue 출력해보기#################
        for(IssueDTO issueDTO : issue){
            System.out.println(issueDTO.getCluster_title()+" : "+issueDTO.getCluster_cnt());
        }
    }

    @Test
    public void getTodayKeywordItemByDomain() {
        System.out.println("""
                ###############################################################################
                getTodayKeywordItemByDomain_Test
                ###############################################################################
                """);

        LocalDateTime start = LocalDateTime.of(2025, 2, 27, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 23, 59);
        Domain domain = Domain.경제;
        List<TodayKeywordItemDTO> today_keyword_item_list = sectionPageService.get_today_keyword_items_by_domain(start, end, domain);

        //#################출력###########
        for (TodayKeywordItemDTO todayKeywordItemDTOS : today_keyword_item_list) {
            System.out.println(todayKeywordItemDTOS.getToday_keyword()+":"+todayKeywordItemDTOS.getToday_keyword_cnt());
            for(TodayRelatedKeywordDTO related_keyword : todayKeywordItemDTOS.getToday_related_keywords()){
                System.out.print(related_keyword.getStart_date());
                System.out.print(" 연관 키워드 : ");
                for(String keyword : related_keyword.getKeywords()){
                    System.out.print(keyword+",");
                }
                System.out.print("\n");
            }
        }
    }
}
