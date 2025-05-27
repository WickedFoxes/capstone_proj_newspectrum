package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.*;
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

    public List<IssueDTO> get_issue_by_domain(LocalDateTime start_date,
                                              LocalDateTime end_date,
                                              Domain domain){
        List<NewsCluster> news_clusters = news_cluster_repo.findByNewsArticle_CreatedDateBetweenAndDomain(start_date, end_date, domain);
        List<IssueDTO> issues = new ArrayList<>();
        Map<String, List<NewsCluster>>clusterMap = new HashMap<>();

        // 클러스터 ID 기준으로 묶기
        for (NewsCluster cluster : news_clusters) {
            String clusterId = cluster.getClusterId();
            clusterMap.computeIfAbsent(clusterId, k -> new ArrayList<>()).add(cluster);
        }
        // 3. Top10 cluster만을 추출
        List<List<NewsCluster>> top10Clusters = clusterMap.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .limit(10)
                .map(Map.Entry::getValue)
                .toList();
        for(List<NewsCluster> temp_cluster : top10Clusters){
            System.out.println("클러스터의 크기 : " + temp_cluster.size());
        }


        for (List<NewsCluster> clusters : top10Clusters) {
            String clusterId = clusters.get(0).getClusterId();

            // NewsArticleDTO 리스트로 변환
            List<NewsArticleDTO> newsArticleDTOList = clusters.stream()
                    .map(c -> new NewsArticleDTO(c.getNews_article()))
                    .sorted(Comparator.comparing(NewsArticleDTO::getCreatedDate).reversed())
                    .toList();

            // 대표 제목: 첫 번째 뉴스 제목
            String clusterTitle = newsArticleDTOList.isEmpty() ? "No Title" : newsArticleDTOList.get(0).getTitle();

            // 클러스터 생성일: 첫 번째 클러스터의 createdDate
            LocalDateTime createdDate = clusters.get(0).getCreatedDate();

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

        List<Keyword> keywordList = keywordRepo.findKeywordByNewsArticle_CreatedDateBetweenAndDomain(start_date, end_date, domain);

        //keywordCnt Map 카운트
        Map<String, Integer> keywordCntMap = new HashMap<>();
        for (Keyword keyword : keywordList) {
            String kw = keyword.getKeyword();
            keywordCntMap.put(kw, keywordCntMap.getOrDefault(kw, 0) + 1);
        }
        // top10 키워드 추출
        List<Map.Entry<String, Integer>> top10Entries = keywordCntMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .toList();

        for (Map.Entry<String, Integer> keywordEntry : top10Entries) {
            String keywordText = keywordEntry.getKey();
            int keyword_cnt = keywordEntry.getValue();
            List<TodayRelatedKeywordDTO> today_related_keyword_items = new ArrayList<>();

            LocalDateTime realted_start_date = start_date;
            LocalDateTime realted_end_date = end_date;
            System.out.println(keywordText+" : "+keyword_cnt);

            for(int i=0; i<4; i++){
                List<Keyword> related_keywords = keywordRepo.findRelatedKeywordsByKeywordAndDateAndDomain(
                        keywordText,
                        realted_start_date,
                        realted_end_date,
                        domain
                );
                System.out.println("related_keywords : "+related_keywords.size());
                List<String> top_related_Keywords = related_keywords.stream()
                        .map(Keyword::getKeyword)
                        .distinct()                         // 중복 제거
                        .limit(7)                          // 7개 제한
                        .toList();      // List<String>으로 수집
//                    System.out.println("관련 있는 키워드 리스트 : "+top10_related_Keywords);

                // 중복 제거를 위해 ID에 Set 적용
                Set<Long> articleIdSet = new HashSet<>();
                List<NewsArticleDTO> total_related_articles = new ArrayList<>();
                for(String related_keyword : top_related_Keywords){
                    List<NewsArticle> related_articles = news_article_repo.findNewsArticlesWithBothKeywords(
                            keywordText,
                            related_keyword,
                            realted_start_date,
                            realted_end_date,
                            domain
                    );
                    for(NewsArticle article :related_articles){
                        if (!articleIdSet.contains(article.getId())) {
                            articleIdSet.add(article.getId());
                            total_related_articles.add(new NewsArticleDTO(article)); // 중복 방지
                        }
                    }
                }
                total_related_articles.sort(Comparator.comparing(NewsArticleDTO::getCreatedDate).reversed());

                // TodayRelatedKeywordDTO
                TodayRelatedKeywordDTO relatedDTO = new TodayRelatedKeywordDTO(
                        realted_start_date,
                        realted_end_date,
                        top_related_Keywords,
                        total_related_articles
                );

                today_related_keyword_items.add(relatedDTO);
                realted_start_date = realted_start_date.minusDays(7);
                realted_end_date = realted_end_date.minusDays(7);
            }

            // TodayKeywordItemDTO
            TodayKeywordItemDTO itemDTO = new TodayKeywordItemDTO(
                    keywordText,
                    keyword_cnt,
                    today_related_keyword_items
            );
            today_keyword_items.add(itemDTO);
        }
        return today_keyword_items;
    }

    public List<NewsArticleDTO> get_news_by_domain(LocalDateTime start_date,
                                                LocalDateTime end_date,
                                                Domain domain) {
        List<NewsArticle> newsArticle = news_article_repo.findByCreatedDateBetweenAndDomain(start_date, end_date, domain);
        List<NewsArticleDTO> newsArticleDTOList = new ArrayList<>();

        for(NewsArticle news_article : newsArticle){
            newsArticleDTOList.add(new NewsArticleDTO(news_article));
        }

        return newsArticleDTOList;
    }

}