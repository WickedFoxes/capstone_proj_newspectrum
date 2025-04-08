package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private NewsArticleService newsArticleService;

    @GetMapping("/")
    public String main(Model model) {
        List<IssueDTO> issues = new ArrayList<>();
        int total_news_cnt = 0; // service를 통해 가져와야 함
        int total_cluster_cnt = 0; // service를 통해 가져와야 함
        List<TodayKeywordItemDTO> today_keyword_items = null; // service를 통해 가져와야 함

        model.addAttribute("issues", issues);
        model.addAttribute("total_news_cnt", total_news_cnt);
        model.addAttribute("total_cluster_cnt", total_cluster_cnt);
        model.addAttribute("today_keyword_items", today_keyword_items);
        return "main";
    }

    @GetMapping("/test")
    public String main_test(Model model) {
        List<IssueDTO> issues = new ArrayList<>();
        int total_news_cnt=100; // service를 통해 가져와야 함
        int total_cluster_cnt=10; // service를 통해 가져와야 함
        List<TodayKeywordItemDTO> today_keyword_items = null; // service를 통해 가져와야 함

//        for (int i = 1; i <= 5; i++) {
//            String title = "이슈 제목 " + i;
//            String media = "뉴스 언론사 " + i;
//            String imgUrl = "https://via.placeholder.com/150?text=Image+" + i; // 임시 이미지 URL
//
//            List<String> relatedArticles = new ArrayList<>();
//            for (int j = 1; j <= 10; j++) {
//                relatedArticles.add("https://example.com/article" + i + "-" + j);
//            }
//
//            issues.add(new IssueDTO(title, media, imgUrl, relatedArticles));
//        }

        model.addAttribute("issues", issues);
        model.addAttribute("total_news_cnt", total_news_cnt);
        model.addAttribute("total_cluster_cnt", total_cluster_cnt);
        model.addAttribute("today_keyword_items", today_keyword_items);
        return "main";
    }

}
