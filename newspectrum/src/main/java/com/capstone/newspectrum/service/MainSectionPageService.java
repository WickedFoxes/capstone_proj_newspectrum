package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
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
public class MainSectionPageService {
    @Autowired
    private NewsArticleRepo news_article_repo;
    @Autowired
    private NewsClusterRepo news_cluster_repo;

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
        Map<String, NewsCluster> cluster_map = new HashMap<>();

//        for(NewsCluster cluster : news_clusters){
//            cluster_map.put(cluster.getClusterId(), cluster);
//        }
//        Set<Map.Entry<Long,NewsCluster>> entrySet = cluster_map.entrySet();
//        for (Map.Entry e : entrySet){
//            IssueDTO issue = new IssueDTO();
//            issue.setCluster_cnt(entrySet.size());
//            issue.setCluster_title(issue.getCluster_title((NewsCluster) e.getValue()));
//            issue.setCreated_date(end_date);
//
//            List<NewsArticleDTO> news_articles = news_article_repo.findNewsArticleForNewsArticleDTO(); //repo에서 쿼리 완성할 것. DTO에 담길 어트리뷰트 가져오는.
//            for (NewsArticleDTO newsArticle : news_articles) {
//                news_articles.add(new NewsArticleDTO());
//                issue.setNews_articles(news_articles);
//            }
//            issues.add(issue);
//        }
        return issues;
    }

    public List<TodayKeywordItemDTO> get_today_keyword_items_by_domain(LocalDateTime start_date,
                                                            LocalDateTime end_date,
                                                            Domain domain){
        List<TodayKeywordItemDTO> today_keyword_items = new ArrayList<>();
        
        // 코드 구현
        
        return today_keyword_items;
    }


}