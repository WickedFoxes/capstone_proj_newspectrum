package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsArticleService {
    @Autowired
    private NewsArticleRepo newsArticleRepo;

    public NewsArticleDTO get_news_article_by_id(Long news_article_id){
        NewsArticleDTO result = null;
        return result;
    }

    public List<FocusKeywordItemDTO> get_focus_keyword_items_by_id(Long news_article_id){
        List<FocusKeywordItemDTO> result = new ArrayList<>();
        return result;
    }

    public List<NewsArticleDTO> get_related_news_articles_by_id(Long news_article_id){
        List<NewsArticleDTO> result = new ArrayList<>();
        return result;
    }
}
