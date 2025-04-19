package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.dto.TodayRelatedKeywordDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
import com.capstone.newspectrum.repository.KeywordRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.repository.NewsClusterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


// get_total_news_cnt (완료)
// get_total_cluster_cnt (완료)
// get_total_news_cnt_by_domain (완료)

// 2025-04-17 저녁 10시까지
// get_issue_by_domain
// get_today_keyword_items_by_domain

@Service
public class SectionPageService {
    @Autowired
    private NewsArticleRepo news_article_repo;
    @Autowired
    private NewsClusterRepo news_cluster_repo;
    @Autowired
    private KeywordRepo keywordRepo;

    public int get_total_news_cnt(LocalDateTime start_date,
                                  LocalDateTime end_date){
        List<NewsArticle> news_articles = news_article_repo.findByCreatedDateBetween(start_date, end_date);
        return news_articles.size();
    }

    public int get_total_news_cnt_by_domain(LocalDateTime start_date,
                                            LocalDateTime end_date,
                                            Domain domain){
        List<NewsArticle> news_articles = news_article_repo.findByCreatedDateBetweenAndDomain(start_date, end_date, domain);
        return news_articles.size();
    }

    public int get_total_cluster_cnt(LocalDateTime start_date,
                                     LocalDateTime end_date){
        List<NewsCluster> news_clusters = news_cluster_repo.findByNewsArticle_CreatedDateBetween(start_date, end_date);

        Set<String> cnt_set = new HashSet<>();
        for(NewsCluster cluster : news_clusters) {
            cnt_set.add(cluster.getClusterId());
        }
        return cnt_set.size();
    }

    public int get_total_cluster_cnt_by_domain(LocalDateTime start_date,
                                               LocalDateTime end_date,
                                               Domain domain){
        List<NewsCluster> news_clusters = news_cluster_repo.findByNewsArticle_CreatedDateBetweenAndDomain(start_date, end_date, domain);

        Set<String> cnt_set = new HashSet<>();
        for(NewsCluster cluster : news_clusters) {
            cnt_set.add(cluster.getClusterId());
        }
        return cnt_set.size();
    }

    // 오류가 있을 수 있는 부분을 주석처리 했습니다.
    public List<IssueDTO> get_issue_by_domain(LocalDateTime start_date,
                                              LocalDateTime end_date,
                                              Domain domain){
        List<NewsCluster> news_clusters = news_cluster_repo.findByNewsArticle_CreatedDateBetween(start_date, end_date);
        List<IssueDTO> issues = new ArrayList<>();
        Map<String, List<NewsCluster>>clusterMap = new HashMap<>();

        // Domain으로 필터링
        List<NewsCluster> clusterByDomain = news_clusters.stream()
                .filter(cluster -> cluster.getNews_article().getDomain().equals(domain))
                .toList();

        // 클러스터 ID 기준으로 묶기
        for (NewsCluster cluster : clusterByDomain) {
            String clusterId = cluster.getClusterId();
            clusterMap.computeIfAbsent(clusterId, k -> new ArrayList<>()).add(cluster);
        }
        for (Map.Entry<String, List<NewsCluster>> entry : clusterMap.entrySet()) {
            String clusterId = entry.getKey();
            List<NewsCluster> clusterGroup = entry.getValue();

            // NewsArticleDTO 리스트로 변환
            List<NewsArticleDTO> newsArticleDTOList = clusterGroup.stream()
                    .map(c -> {
                        NewsArticle article = c.getNews_article();
                        return new NewsArticleDTO(article
                        );
                    })
                    .toList();

            // 대표 제목: 첫 번째 뉴스 제목
            String clusterTitle = newsArticleDTOList.isEmpty() ? "No Title" : newsArticleDTOList.get(0).getTitle();

            // 클러스터 생성일: 첫 번째 클러스터의 createdDate
            LocalDateTime createdDate = clusterGroup.get(0).getCreatedDate();

            // DTO 생성
            IssueDTO issue = new IssueDTO(
                    newsArticleDTOList.size(),
                    clusterTitle,
                    newsArticleDTOList,
                    createdDate
            );

            issues.add(issue);
        }
        return issues;
    }

    public List<TodayKeywordItemDTO> get_today_keyword_items_by_domain(LocalDateTime start_date,
                                                            LocalDateTime end_date,
                                                            Domain domain){
        List<TodayKeywordItemDTO> today_keyword_items = new ArrayList<>();

        List<Keyword> keywordList = keywordRepo.findKeywordByCreatedDateBetween(start_date, end_date);

        //Domin으로 Keyword 필터링
        List<Keyword> keywordFilteredByDomain = keywordList.stream()
                .filter(k -> k.getNews_article().getDomain().equals(domain))
                .toList();

        //필터링된 keyword Map의 List에 추가
        Map<String, List<Keyword>> keywordMap = new HashMap<>();
        for(Keyword keyword : keywordFilteredByDomain){
            String kw = keyword.getKeyword();
            keywordMap.computeIfAbsent(kw, s-> new ArrayList<>()).add(keyword);
        }
        //
        for (Map.Entry<String, List<Keyword>> keywordEntry : keywordMap.entrySet()) {
            String keywordText = keywordEntry.getKey();
            List<Keyword> keywordMap_value_KeywordList = keywordEntry.getValue();

            // keyword 수
            int cnt = keywordMap_value_KeywordList.size();

            // relatedKeyword
            List<Keyword> relatedKeywords = keywordMap_value_KeywordList.stream()
                    .flatMap(k -> k.getRelated_keywords().stream())
                    .map(rel -> rel.getRelated_keyword())
                    .distinct()
                    .toList();

            // relatedArticles
            List<NewsArticleDTO> relatedArticles = keywordMap_value_KeywordList.stream()
                    .map(k -> k.getNews_article())
                    .distinct()
                    .map(article -> new NewsArticleDTO(article))
                    .toList();

            // TodayRelatedKeywordDTO
            TodayRelatedKeywordDTO relatedDTO = new TodayRelatedKeywordDTO(
                    keywordMap_value_KeywordList.get(0).getCreatedDate(),
                    relatedKeywords,
                    relatedArticles
            );

            // TodayKeywordItemDTO
            TodayKeywordItemDTO itemDTO = new TodayKeywordItemDTO(
                    keywordText,
                    cnt,
                    List.of(relatedDTO)
            );
            today_keyword_items.add(itemDTO);
        }
        return today_keyword_items;
    }
}