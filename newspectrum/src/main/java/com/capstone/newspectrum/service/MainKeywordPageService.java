package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.MainBlockDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import com.capstone.newspectrum.repository.NewsClusterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainKeywordPageService {
    @Autowired
    private NewsArticleRepo news_article_repo;
    private NewsClusterRepo news_cluster_repo;

    // start_date와 end_date 사이에 출시된 뉴스의 수
    public int get_total_news_cnt(LocalDateTime start_date,
                                  LocalDateTime end_date){
        List<NewsArticle> news_articles = news_article_repo.findByCreatedDateBetween(start_date, end_date);
        return news_articles.size();
    }

    // start_date와 end_date 사이에 출시되었으며 도메인이 domain인 뉴스의 수
    public int get_total_news_cnt_by_domain(LocalDateTime start_date,
                                            LocalDateTime end_date,
                                            Domain domain){
        List<NewsArticle> news_articles = news_article_repo.findByCreatedDateBetweenAndDomain(start_date, end_date, domain);
        return news_articles.size();
    }

    /*
    오늘의 이슈 리스트 가져오기
    1. List<TodayIssueDTO> today_issue_list 생성
    2. news_cluster_repo.findByNewsArticle_CreatedDateBetween를 사용하여 List<NewsCluster> clusters 생성
    3. clusters의 데이터를 Map<String(cluster_id), List<NewsCluster>> cluster_map 로 분리하여 저장
    4. for (String clusterId : cluster_map.keySet()) 반복문
    4-1. List<NewsArticle> news_articles 생성
    4-2. Map<String, Integer> keyword_cnt_map 생성
    4-3. for (NewsCluster cluster : cluster_map.get(clusterId)) 반복문
    4-3-1. news_articles에 cluster.news_article 추가
    4-3-2. for(Keyword keyword : cluster.news_article.keywords) 반복문
    4-3-2-1. keyword.keyword의 카운트를 keyword_cnt_map에 저장
    4-4. keyword_cnt_map의 key를 value가 높은 순서대로 정렬하여 List<KeywordDTO> keywords로 저장
    4-5. keywords와 동일한 순서로 value 값들을 List<Integer> keywords_count로 저장
    4-6. (news_articles, keywords, keywords_count)를 사용하여 TodayIssueDTO today_issue 생성
    */
    public List<MainBlockDTO> get_today_issue_list(LocalDateTime start_date,
                                                   LocalDateTime end_date){
        List<MainBlockDTO> today_issue_list = new ArrayList<>();

        // 이후 코드 구현

        return today_issue_list;
    }
    public List<MainBlockDTO> get_today_issue_list_with_domain(LocalDateTime start_date,
                                                               LocalDateTime end_date,
                                                               Domain domain){
        List<MainBlockDTO> today_issue_list = new ArrayList<>();

        // 이후 코드 구현

        return today_issue_list;
    }



}
