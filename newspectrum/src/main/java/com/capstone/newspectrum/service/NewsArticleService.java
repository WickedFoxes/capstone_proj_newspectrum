package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.FocusKeywordItemDTO;
import com.capstone.newspectrum.dto.KeywordDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.RelatedNewsArticleAndScoreDTO;
import com.capstone.newspectrum.enumeration.Domain;
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
    public List<NewsArticleDTO> get_news_Article_by_domain(Domain domain){
        List<NewsArticle> newsArticleList = newsArticleRepo.findAllByDomain(domain);
        List<NewsArticleDTO> newsArticleDTOList = new ArrayList<>();
        for(NewsArticle newsArticle : newsArticleList){
            newsArticleDTOList.add(new NewsArticleDTO(newsArticle));
        }
        return newsArticleDTOList;
    }

    public List<KeywordDTO> get_keyword_items_by_id(Long news_article_id){
        NewsArticle news_article = newsArticleRepo.findById(news_article_id).get();
        List<Keyword> keywords = news_article.getKeywords();
        keywords.sort((k1, k2) -> Double.compare(k2.getScore(), k1.getScore()));

        List<KeywordDTO> result = new ArrayList<>();
        for(Keyword keyword : keywords){
            result.add(new KeywordDTO(keyword));
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
    //도메인별 뉴스만화 NewsArticleDTO 10개씩 가져오기
    public List<NewsArticleDTO> get_news_article_by_domain_for_comics(Domain domain){
        List<NewsArticleDTO> newsArticle_for_comics = new ArrayList<>();
        List<NewsArticleDTO> newsArticleDTOList = new ArrayList<>();
        newsArticleDTOList = get_news_Article_by_domain(domain);
        for(int i = 0; i < 10; i++){
            int rand_num = (int)Math.random();
            newsArticle_for_comics.add(newsArticleDTOList.get(rand_num));
        }
        return newsArticle_for_comics;
    }
}
