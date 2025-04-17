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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// 2025-04-10 저녁 10시까지
// get_total_news_cnt, get_total_cluster_cnt, get_total_news_cnt_by_domain, get_total_cluster_cnt_by_domain 구현

@Service
public class MainPageSevice {
    @Autowired
    private NewsArticleRepo news_article_repo;
    @Autowired
    private NewsClusterRepo news_cluster_repo;
    @Autowired
    private KeywordRepo keyword_repo;


    public List<MainBlockDTO> get_main_block_list(LocalDateTime today){
        List<MainBlockDTO> mainBlockList = new ArrayList<>();

        // 1. 뉴스 클러스터 조회
        List<NewsCluster> today_clusters = news_cluster_repo
                .findByNewsArticle_CreatedDateBetween(today.minusHours(48), today);
//        System.out.println("news_article size : " + today_clusters.size());

        // 2. 클러스터 ID로 그룹핑
        Map<String, List<NewsCluster>> clusterMap = new HashMap<>();
        for (NewsCluster cluster : today_clusters) {
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
//        for(List<NewsCluster> temp_cluster : top10Clusters){
//            System.out.println("클러스터의 크기 : " + temp_cluster.size());
//        }

        // 3. 클러스터별 MainBlock 생성
        for (List<NewsCluster> clusters : top10Clusters) {
            List<NewsArticleDTO> newsArticles = new ArrayList<>();
            Map<String, Integer> keywordCntMap = new HashMap<>();

            for (NewsCluster cluster : clusters) {
                NewsArticle article = cluster.getNews_article();

                // 3-1. 뉴스 기사 DTO 추가
                newsArticles.add(new NewsArticleDTO(article));

                // 3-2. 키워드 카운팅
                for (Keyword keyword : article.getKeywords()) {
                    String word = keyword.getKeyword();
                    keywordCntMap.put(word, keywordCntMap.getOrDefault(word, 0) + 1);
                }
            }

            // 4. 키워드 정렬 (value 기준 내림차순)
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(keywordCntMap.entrySet());
            sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            // 5. 키워드 리스트 및 카운트 리스트 생성
            List<String> keywords = new ArrayList<>();
            List<Integer> keywordsCount = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                keywords.add(entry.getKey());
                keywordsCount.add(entry.getValue());
            }
//            System.out.println("### 키워드 테스트 ###");
//            for(int i=0; i<5; i++){
//                System.out.println(keywords.get(i) + " : " + keywordsCount.get(i));
//            }

            // 6. MainBlockTopKeywordDTO 리스트 생성
            List<MainBlockTopKeywordDTO> mainBlockKeywords = new ArrayList<>();
            int N = 5;
            for(String keyword : keywords.subList(0,N)){
                LocalDateTime realted_start_date = today.minusDays(7);
                LocalDateTime realted_end_date = today;

                List<MainBlockRelatedKeywordsDTO> related_keywords_timelines = new ArrayList<>();
                for(int i=0; i<4; i++){
                    List<Keyword> related_keywords = keyword_repo
                            .findRelatedKeywordsByKeywordAndDate(keyword, realted_start_date, realted_end_date);

                    List<String> top10_related_Keywords = related_keywords.stream()
                            .map(Keyword::getKeyword)        // String 추출
                            .distinct()                         // 중복 제거
                            .limit(10)                          // 10개 제한
                            .toList();      // List<String>으로 수집
//                    System.out.println("관련 있는 키워드 리스트 : "+top10_related_Keywords);


                    related_keywords_timelines.add(new MainBlockRelatedKeywordsDTO(
                            realted_start_date,
                            realted_end_date,
                            top10_related_Keywords
                    ));
                    realted_start_date = realted_start_date.minusDays(7);
                    realted_end_date = realted_end_date.minusDays(7);
                }
                mainBlockKeywords.add(new MainBlockTopKeywordDTO(
                        keyword,
                        related_keywords_timelines
                ));
            }

            // 최종 TodayIssueDTO 생성 및 추가
            mainBlockList.add(new MainBlockDTO(
                    newsArticles,
                    clusters.size(),
                    keywords,
                    keywordsCount,
                    mainBlockKeywords
            ));
        }

        return mainBlockList;
    }
}
