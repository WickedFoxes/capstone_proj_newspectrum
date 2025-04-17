package com.capstone.newspectrum.service;

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

//    public List<NewsArticleDTO> getNewsArticleListAll(){
//        List<NewsArticle> list = newsArticleRepo.findAll();
//        List<NewsArticleDTO> dto_list = new ArrayList<NewsArticleDTO>();
//
//        for(NewsArticle data : list){
//            dto_list.add(new NewsArticleDTO(data));
//        }
//
//        return dto_list;
//    }
//
//    public List<NewsArticleDTO> getNewsArticleListByTitle(String title){
//        List<NewsArticle> list = newsArticleRepo.findAllByTitle(title);
//        List<NewsArticleDTO> dto_list = new ArrayList<NewsArticleDTO>();
//
//        for(NewsArticle data : list){
//            dto_list.add(new NewsArticleDTO(data));
//        }
//
//        return dto_list;
//    }

//    public NewsArticleDTO saveNewsArticle(NewsArticleDTO newsArticleDTO){
//        newsArticleRepo.save(new NewsArticle(newsArticleDTO));
//        return newsArticleDTO;
//    }
}
