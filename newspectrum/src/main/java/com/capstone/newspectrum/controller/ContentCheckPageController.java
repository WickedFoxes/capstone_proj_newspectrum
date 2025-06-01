package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.ContentCheckDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.ContentCheck;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentCheckPageController {
    @Autowired
    NewsArticleService newsArticleService;

    @GetMapping("/contentcheck/{news_article_id}")
    public String contentCheck(Model model,
                               @PathVariable("news_article_id") Long news_article_id) {
        NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_article_id);

        if (news_article == null) {
            System.out.println("❌ 뉴스 기사 없음. ID = " + news_article_id);
            return "error/404"; // 또는 템플릿 만들어도 OK
        }

//        List<ContentCheckDTO> news_article_content_check = news_article.getContentChecks();
        List<ContentCheckDTO> news_article_content_check = new ArrayList<>();

//        if (news_article_content_check == null)
//            news_article_content_check = new ArrayList<>();

        model.addAttribute("news_article", news_article);
        model.addAttribute("content_check", news_article_content_check);

        return "contentcheck";
    }
}
