package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class TestController {
    @Autowired
    private NewsArticleService newsArticleService;

//    @RequestMapping("/test_add")
//    public String test_add(Model model){
//        List<NewsArticleDTO> test_data = new ArrayList<>();
//        test_data.add(newsArticleService.saveNewsArticle(
//                new NewsArticleDTO("title1", "this is title1")
//        ));
//        test_data.add(newsArticleService.saveNewsArticle(
//                new NewsArticleDTO("title2", "this is title2")
//        ));
//        model.addAttribute("test_data_list", test_data);
//        return "test";
//    }

//    @RequestMapping("/test")
//    public String test(Model model){
//        List<NewsArticleDTO> test_data = newsArticleService.getNewsArticleListAll();
//        model.addAttribute("test_data_list", test_data);
//        return "test";
//    }
}
