package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsViewController {
    @Autowired
    NewsArticleService newsArticleService;

    @GetMapping("/view/{news_article_id}")
    public String viewNewsArticle(Model model,
                                  @PathVariable("news_article_id") Long news_article_id) {
        NewsArticleDTO news_article = newsArticleService.get_news_article_by_id(news_article_id);
        List<KeywordDTO> keyword_items = newsArticleService.get_keyword_items_by_id(news_article_id);
        List<RelatedNewsArticleAndScoreDTO> related_news_articles = newsArticleService.get_related_news_articles_by_id(news_article_id);

        // AI 요약을 마침표로 분할
        List<String> summaryLines = new ArrayList<>();
        if (news_article.getSummary() != null) {
            String[] split = news_article.getSummary().split("\\.\\s+");
            for (String line : split) {
                if (!line.isBlank()) summaryLines.add(line.trim() + ".");
                if (summaryLines.size() == 3) break;
            }
        }


        model.addAttribute("news_article", news_article);
        model.addAttribute("summaryLines", summaryLines);
        model.addAttribute("keyword_items", keyword_items);
        model.addAttribute("related_news_articles", related_news_articles);

        return "view";
    }


}
