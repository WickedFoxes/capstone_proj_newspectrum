package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.service.NewsArticleService;
import com.capstone.newspectrum.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;

    // localhost:8080/search?searchword=검색어
    @GetMapping("/search")
    public String searchNewsArticle (Model model,  @RequestParam("searchword") String searchword){
        List<NewsArticleDTO> newsArticleDTOList = searchService.get_news_article_by_content(searchword);
        //List<NewsArticleDTO> newsArticleDTO = searchService.get_news_article_by_title(searchword);

        model.addAttribute("news_articles", newsArticleDTOList);
        return "search";
    }
}
