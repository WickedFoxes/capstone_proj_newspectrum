package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.MainPageSevice;
import com.capstone.newspectrum.service.SectionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private MainPageSevice mainPageService;
    @Autowired
    private SectionPageService sectionPageService;

    @GetMapping("/")
    public String main(Model model) {
        System.out.println("테스트 실행 Java 버전: " + System.getProperty("java.version"));

        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime start = today.minusDays(1);

        Map<String, MainBlockDTO> issuesByDomain = mainPageService.get_main_block_list(today);

//        System.out.println("""
//        ###########################################################################################
//        get_main_block_list
//        ###########################################################################################
//        """);
//        Map<String, MainBlockDTO> items = mainPageService.get_main_block_list(today);
//        System.out.println(items.size());
//        items.forEach((domain, item) -> {
//            System.out.println("############## " + item.getDomain() +" ##############");
//            System.out.println("keywords : "+ item.getKeywords());
//            System.out.println("keywords cnt : "+ item.getKeywords_cnt());
//            int index = 0;
//            System.out.println(
//                    "keyword " +index +"번째 [" + item.getMain_block_top_keywords().get(index).getKeyword() + "] 연관 뉴스 : "
//            );
//            for(NewsArticleDTO newsArticleDTO : item.getMain_block_top_keywords().get(index).getNews_articles()){
//                System.out.println(" - " + newsArticleDTO.getTitle().replace("\n", ""));
//            }
//        });

        model.addAttribute("issuesByDomainJson", issuesByDomain);

        // 각 도메인별로 하나씩 대표 이슈 추출
        List<IssueDTO> hotIssues = new ArrayList<>();
        for (Domain domain : Domain.values()) {
            List<IssueDTO> domainIssues = sectionPageService.get_issue_by_domain(start, today, domain);
            if (!domainIssues.isEmpty()) {
                hotIssues.add(domainIssues.get(0));  // 가장 큰 클러스터 하나만 사용
                System.out.println("🔥 Domain: " + domain.name());
            }
        }

        model.addAttribute("hotIssues", hotIssues);

        return "main";
    }

}

