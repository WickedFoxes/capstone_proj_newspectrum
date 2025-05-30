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

    private LocalDateTime today = LocalDateTime.of(2025, 3, 1, 00, 00);

    @GetMapping("/")
    public String main(Model model) {
        LocalDateTime start = today.minusDays(2);

        Map<String, MainBlockDTO> issuesByDomain = mainPageService.get_main_block_list(today);

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

