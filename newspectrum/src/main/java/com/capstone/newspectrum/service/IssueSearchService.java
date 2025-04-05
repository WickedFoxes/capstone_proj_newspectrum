package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class IssueSearchService implements SearchServiceInterface{
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    public List<NewsArticleDTO> getAllNewsArticleByIssue(){
        List<NewsArticle> list = newsArticleRepo.findAll(); //Issue 별로 필터링
        List<NewsArticleDTO> dto_list = new ArrayList<>();
        for(NewsArticle data : list){
            dto_list.add(new NewsArticleDTO(data));
        }
        return dto_list;
    }
}
