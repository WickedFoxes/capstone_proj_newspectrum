package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsArticleRelation;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsArticleService {
    @Autowired
    private NewsArticleRepo newsArticleRepo;

    public NewsArticleDTO get_news_article_by_id(Long news_article_id){
        Optional<NewsArticle> news_article = newsArticleRepo.findById(news_article_id);
        return news_article.map(NewsArticleDTO::new).orElse(null);
    }

    public List<FocusKeywordItemDTO> get_focus_keyword_items_by_id(Long news_article_id){
        Optional<NewsArticle> news_article = newsArticleRepo.findById(news_article_id);
        List<Keyword> keywords = news_article.get().getKeywords();
        keywords.sort((k1, k2) -> Double.compare(k2.getScore(), k1.getScore()));

        List<FocusKeywordItemDTO> result = new ArrayList<>();

        int keywords_num = 2;
        for(int i=0; i<keywords_num; i++){
            String keyword = keywords.get(i).getKeyword();
            List<NewsArticle> news_articles = newsArticleRepo.findNewsArticlesByKeyword(
                    keyword,
                    news_article.get().getCreatedDate().minusDays(7),
                    news_article.get().getCreatedDate(),
                    news_article.get().getDomain()
            );
            List<NewsArticleDTO> news_article_dto_list = new ArrayList<>();
            for(NewsArticle news : news_articles){
                news_article_dto_list.add(new NewsArticleDTO(news));
            }

            FocusKeywordItemDTO focusKeywordItemDTO = new FocusKeywordItemDTO(keyword, news_article_dto_list);
            result.add(focusKeywordItemDTO);
        }
        return result;
    }

    public List<RelatedNewsArticleAndScoreDTO> get_related_news_articles_by_id(Long news_article_id){
        Optional<NewsArticle> news_article = newsArticleRepo.findById(news_article_id);
        List<NewsArticleRelation> relations = news_article.get().getRelated_news_articles();
        relations.sort((r1, r2) -> Double.compare(r2.getSimilarity(), r1.getSimilarity()));

        List<RelatedNewsArticleAndScoreDTO> result = new ArrayList<>();
        for(NewsArticleRelation relation : relations){
            result.add(
                    new RelatedNewsArticleAndScoreDTO(
                            new NewsArticleDTO(relation.getRelated_news_article()),
                            relation.getSimilarity()
                    )
            );
        }
        return result;
    }
}
