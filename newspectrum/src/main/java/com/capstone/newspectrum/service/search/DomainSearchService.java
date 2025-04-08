package com.capstone.newspectrum.service.search;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DomainSearchService implements SearchServiceInterface {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    public List<NewsArticleDTO> getAllNewsArticleListByDomain(Domain domain){
        List<NewsArticle> list = newsArticleRepo.findAll(); //domain으로 필터링 할 수 없나?
        List<NewsArticleDTO> dto_list = new ArrayList<>();
        for(NewsArticle data : list){
            dto_list.add(new NewsArticleDTO(data));
        }
        return dto_list;
    }
}
