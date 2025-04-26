package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.enumeration.Media;
import com.capstone.newspectrum.service.MainPageSevice;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class NewsViewController {
    @Autowired
    NewsArticleService newsArticleService;

    @GetMapping("/view/{news_article_id}")
    public String viewNewsArticle(Model model,
                                  @PathVariable("news_article_id") Long news_article_id) {
        NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_article_id);
        List<FocusKeywordItemDTO> focus_keyword_items = newsArticleService.get_focus_keyword_items_by_id(news_article_id);
        List<NewsArticleDTO> related_news_articles = newsArticleService.get_related_news_articles_by_id(news_article_id);

        model.addAttribute("news_article", news_article);
        model.addAttribute("focus_keyword_items", focus_keyword_items);
        model.addAttribute("related_news_articles", related_news_articles);

        return "view";
    }


}
