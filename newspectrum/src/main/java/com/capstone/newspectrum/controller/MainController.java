package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.MainPageService;
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
    private MainPageService mainPageService;
    @Autowired
    private SectionPageService sectionPageService;

    private LocalDateTime today = LocalDateTime.of(2025, 5, 27, 00, 00);

    @GetMapping("/")
    public String main(Model model) {
        LocalDateTime start = today.minusDays(2);

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

        // 1. 대표 이슈에서 대표 뉴스 1개씩 랜덤 추출(총합 5개의 뉴스)
        Random random = new Random();
        List<NewsArticleDTO> today_main_block_articles = new ArrayList<>();
        for(IssueDTO issue : hotIssues){
            List<NewsArticleDTO> articles = issue.getNews_articles();

            int randIndex = random.nextInt(articles.size());
            today_main_block_articles.add(articles.get(randIndex));
        }
        model.addAttribute("today_main_block_articles", today_main_block_articles);


        // 2. AI만화에 사용할 news_article 10개
        List<NewsArticleDTO> news_articles_for_comic = mainPageService.get_news_article_for_comics(today);
        model.addAttribute("news_articles_for_comic", news_articles_for_comic);

        // 3. 키워드 클러스터에 사용할 데이터
        Map<String, MainBlockDTO> issuesByDomain = mainPageService.get_main_block_list(today);
        model.addAttribute("issuesByDomainJson", issuesByDomain);

        return "main";
    }

}

