package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.Keyword;
import com.capstone.newspectrum.model.NewsCluster;
import com.capstone.newspectrum.repository.KeywordRepo;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.repository.NewsClusterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


// 2025-04-10 저녁 10시까지
// get_total_news_cnt, get_total_cluster_cnt, get_total_news_cnt_by_domain, get_total_cluster_cnt_by_domain 구현

@Service
public class MainPageService {
    @Autowired
    private NewsArticleRepo news_article_repo;
    @Autowired
    private NewsClusterRepo news_cluster_repo;
    @Autowired
    private KeywordRepo keyword_repo;

    public Map<String, MainBlockDTO> get_main_block_list(LocalDateTime today){
        Map<String, MainBlockDTO> issuesByDomain = new HashMap<>();
        issuesByDomain.put("정치", get_main_block_list_by_domain(today, Domain.정치));
        issuesByDomain.put("경제", get_main_block_list_by_domain(today, Domain.경제));
        issuesByDomain.put("사회", get_main_block_list_by_domain(today, Domain.사회));
        issuesByDomain.put("연예", get_main_block_list_by_domain(today, Domain.연예));
        issuesByDomain.put("스포츠", get_main_block_list_by_domain(today, Domain.스포츠));

        return issuesByDomain;
    }

    public MainBlockDTO get_main_block_list_by_domain(LocalDateTime today,
                                                            Domain domain){
        List<MainBlockDTO> mainBlockList = new ArrayList<>();

        // 1. 뉴스 클러스터 조회
        List<NewsCluster> today_clusters = news_cluster_repo
                .findByNewsArticle_CreatedDateBetweenAndDomain(today.minusHours(48), today, domain);
//        System.out.println("news_article size : " + today_clusters.size());

        // 2. 키워드 별 뉴스 리스트 구하기
        Map<String, List<NewsArticleDTO>> keywordNewsArticleMap = new HashMap<>();
        for (NewsCluster cluster : today_clusters) {
            for(Keyword keyword : cluster.getNews_article().getKeywords()){
                keywordNewsArticleMap
                        .computeIfAbsent(keyword.getKeyword(), k -> new ArrayList<>())
                        .add(new NewsArticleDTO(cluster.getNews_article()));
            }
        }

        // 3. TopK 클러스터 구하기
        // 3-1. 클러스터 ID로 그룹핑
        Map<String, List<NewsCluster>> clusterMap = new HashMap<>();
        for (NewsCluster cluster : today_clusters) {
            String clusterId = cluster.getClusterId();
            clusterMap.computeIfAbsent(clusterId, k -> new ArrayList<>()).add(cluster);
        }
        // 3-2. TopK cluster만을 추출
        int k = Math.min(10, clusterMap.size());
        List<List<NewsCluster>> topKClusters = clusterMap.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .limit(k)
                .map(Map.Entry::getValue)
                .toList();
//        for(List<NewsCluster> temp_cluster : topKClusters){
//            System.out.println("클러스터의 크기 : " + temp_cluster.size());
//        }


        // 4. 클러스터를 2개 선택하기
        // - 각 클러스터의 TOP5 키워드가 모두 겹치지 않아야 함
        List<Map<String, Integer>> clusterTopKeywords = new ArrayList<>();
        // 4-1. 클러스터 탐색
        for (List<NewsCluster> clusters : topKClusters) {
            HashMap<String, Integer> keywordCnt = new HashMap<>();

            // 4-2. 모든 키워드 모으기
            List<String> all_of_keywords = new ArrayList<>();
            for(NewsCluster cluster : clusters){
                NewsArticleDTO artice = new NewsArticleDTO(cluster.getNews_article());
                all_of_keywords.addAll(artice.getKeywords());
            }

            // 4-3. 키워드 카운트에 추가
            for(String keyword : all_of_keywords){
                keywordCnt.put(keyword, keywordCnt.getOrDefault(keyword, 0) + 1);
            }

            // 4-4. 키워드 추출
            Map<String, Integer> top = keywordCnt.entrySet().stream()
                    .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                    .limit(10)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1, // 중복 키 처리 (필요없지만 명시)
                            LinkedHashMap::new // 순서를 유지한 Map
                    ));
            clusterTopKeywords.add(top);
        }

        if (clusterTopKeywords.size() < 2) {
            // 클러스터가 2개 미만이면 빈 DTO라도 반환
            return new MainBlockDTO(
                    domain,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
        // 4-4 키워드가 겹치지 않는 클러스터 인덱스 구하기
        Map<String, Integer> cluster_keyword_0 = clusterTopKeywords.get(0);
        Map<String, Integer> cluster_keyword_1 = clusterTopKeywords.get(1);
        int n = topKClusters.size();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                Set<String> allKeywords = new HashSet<>();
                List<String> top5Keywords_0 = cluster_keyword_0.entrySet().stream()
                        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())) // 값 기준 내림차순
                        .limit(5)
                        .map(Map.Entry::getKey) // 키만 추출
                        .toList(); // 리스트로 수집
                List<String> top5Keywords_1 = cluster_keyword_1.entrySet().stream()
                        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())) // 값 기준 내림차순
                        .limit(5)
                        .map(Map.Entry::getKey) // 키만 추출
                        .toList(); // 리스트로 수집

                allKeywords.addAll(top5Keywords_0);
                allKeywords.addAll(top5Keywords_1);

                // 세 클러스터의 키워드가 2*5=10 일 때, 두 클러스터는 겹치는 키워드가 없음
                if (allKeywords.size() == 10) {
                    cluster_keyword_0 = clusterTopKeywords.get(i);
                    cluster_keyword_1 = clusterTopKeywords.get(j);
                    break;
                }
            }
        }

        // 5. MainBlockDTO 구하기
        List<String> keywords = new ArrayList<>();
        List<Integer> keywords_cnt = new ArrayList<>();
        List<MainBlockTopKeywordDTO> main_block_top_keywords = new ArrayList<>();

        HashMap<String, Integer> selected_keywords = new HashMap<>();
        cluster_keyword_0.forEach((keyword, count) ->
                selected_keywords.put(keyword, selected_keywords.getOrDefault(keyword, 0) + count)
        );
        cluster_keyword_1.forEach((keyword, count) ->
                selected_keywords.put(keyword, selected_keywords.getOrDefault(keyword, 0) + count)
        );

        selected_keywords.forEach((keyword, count) -> {
            keywords.add(keyword);
            keywords_cnt.add(count);
            main_block_top_keywords.add(new MainBlockTopKeywordDTO(
                    keyword,
                    keywordNewsArticleMap.get(keyword)
            ));
        });

        // 최종 TodayIssueDTO 생성 및 추가
        return new MainBlockDTO(
                domain,
                keywords,
                keywords_cnt,
                main_block_top_keywords
        );

    }
    private boolean checkKeywordOverlap(List<String> a, List<String> b, int k) {
        Set<String> keywordsA = new HashSet<>(a);
        Set<String> keywordsB = new HashSet<>(b.subList(0, k));
        keywordsA.retainAll(keywordsB);  // 교집합
        return keywordsA.size() < k/2;
    }
}
