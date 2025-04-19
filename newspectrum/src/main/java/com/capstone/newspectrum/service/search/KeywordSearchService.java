package com.capstone.newspectrum.service.search;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class KeywordSearchService implements SearchServiceInterface {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    public List<NewsArticleDTO> getAllNewsArticleByKeyword(){
        List<NewsArticle> list = newsArticleRepo.findAll(); //Keyword로 필터링
        List<NewsArticleDTO> dto_list = new ArrayList<>();
        for(NewsArticle data : list){
            dto_list.add(new NewsArticleDTO(data));
        }
        return dto_list;
    }
}
