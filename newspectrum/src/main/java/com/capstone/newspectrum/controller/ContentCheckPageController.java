package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.CheckType;
import com.capstone.newspectrum.model.ContentCheck;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contentCheck")
public class ContentCheckPageController {
    @Autowired
    NewsArticleService newsArticleService;
    @GetMapping("/{news_Article_id}")
    public String contentCheck(Model model,
                               @PathVariable("news_article_id") Long news_article_id){
    NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_article_id);
    List<ContentCheck> news_article_content_check = news_article.getContentCheck();

    model.addAttribute("news_article", news_article);
    model.addAttribute("content_check", news_article_content_check);
    return "news_article_contentCheck";
    }
}
