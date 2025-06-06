package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.KeywordRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    @Autowired
    private KeywordRepo keywordRepo;

    public List<NewsArticleDTO> get_news_article_by_title(String title){
        List<NewsArticle> newsArticle = newsArticleRepo.findAllByTitleContainingOrderByCreatedDateDesc(title);
        List<NewsArticleDTO> newsArticleDTOList = new ArrayList<>();
        for(NewsArticle news_article : newsArticle){
            newsArticleDTOList.add(new NewsArticleDTO(news_article));
        }
        return newsArticleDTOList;
    }
    public List<NewsArticleDTO> get_news_article_by_content(String content){
        List<NewsArticle> newsArticle = newsArticleRepo.findArticlesByClusterWithKeywordInContent(content);
        List<NewsArticleDTO> newsArticleDTOList = new ArrayList<>();
        for(NewsArticle news : newsArticle){
            newsArticleDTOList.add(new NewsArticleDTO(news));
        }
        return newsArticleDTOList;
    }
}
