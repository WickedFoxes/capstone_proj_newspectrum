package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller("/comics")
public class ComicsController {
    @Autowired
    private NewsArticleService newsArticleService;

    @GetMapping("/{domain}")
    public String mainPage_comics(Model model, @PathVariable("domain") Domain domain){
        List<NewsArticleDTO> newsArticleDTOList = newsArticleService.get_news_article_by_domain_for_comics(domain);
        List<String> comics_url = new ArrayList<>();
        for(int i = 0; i < newsArticleDTOList.size(); i++){
            comics_url.add(newsArticleDTOList.get(i).getComics_url());
        }
        model.addAttribute("comics_url", comics_url);
        return "mainPage_comics";
    }

}
