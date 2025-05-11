package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.service.NewsArticleService;
import com.capstone.newspectrum.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class SearchController {
    @Autowired
    NewsArticleService newsArticleService;
    SearchService searchService;
    @GetMapping("/{search_article_title}")
    public String searchNewsArticleByTitle(Model model, @PathVariable("search_article_title") String search_article_title){
        List<NewsArticleDTO> newsArticleDTO = searchService.get_news_article_by_title(search_article_title);

        for(NewsArticleDTO news : newsArticleDTO){
            List<FocusKeywordItemDTO> focus_keyword_items = newsArticleService.get_focus_keyword_items_by_id(news.getId());
            List<RelatedNewsArticleAndScoreDTO> related_news_articles = newsArticleService.get_related_news_articles_by_id(news.getId());
        }


        model.addAttribute("news_article", newsArticleDTO);
        for(NewsArticleDTO news : newsArticleDTO){
            model.addAttribute("focus_keyword_items", newsArticleDTO);
            model.addAttribute("related_news_articles", newsArticleDTO);
        }
        return "view";
    }
    @GetMapping("/{search_article_content}")
    public String searchNewsArticleByContent (Model model, @PathVariable("search_article_content") String search_article_content){
        List<NewsArticleDTO> newsArticleDTO = searchService.get_news_article_by_content(search_article_content);

        for(NewsArticleDTO news : newsArticleDTO){
            List<FocusKeywordItemDTO> focus_keyword_items = newsArticleService.get_focus_keyword_items_by_id(news.getId());
            List<RelatedNewsArticleAndScoreDTO> related_news_articles = newsArticleService.get_related_news_articles_by_id(news.getId());
        }

        model.addAttribute("news_article", newsArticleDTO);
        for(NewsArticleDTO news : newsArticleDTO){
            model.addAttribute("focus_keyword_items", newsArticleDTO);
            model.addAttribute("related_news_articles", newsArticleDTO);
        }
        return "view";
    }
}
