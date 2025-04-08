package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
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

        for (int i = 1; i <= 5; i++) {
            String title = "이슈 제목 " + i;
            String media = "뉴스 언론사 " + i;
            String imgUrl = "https://via.placeholder.com/150?text=Image+" + i; // 임시 이미지 URL

            List<String> relatedArticles = new ArrayList<>();
            for (int j = 1; j <= 10; j++) {
                relatedArticles.add("https://example.com/article" + i + "-" + j);
            }

            issues.add(new IssueDTO(title, media, imgUrl, relatedArticles));
        }

        model.addAttribute("issues", issues);
        return "main";
    }

}
