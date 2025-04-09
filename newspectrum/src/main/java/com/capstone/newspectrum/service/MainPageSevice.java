package com.capstone.newspectrum.service;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.model.NewsCluster;
import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


// 2025-04-10 저녁 10시까지
// get_total_news_cnt, get_total_cluster_cnt, get_total_news_cnt_by_domain, get_total_cluster_cnt_by_domain 구현

@Service
public class MainPageSevice {
    @Autowired
    NewsArticleRepo news_article_repo;
//    NewsClusterRepo news_clustser_repo;

//    public int get_total_news_cnt(LocalDateTime start_date,
//                                  LocalDateTime end_date){
//        List<NewsArticle> news_articles = news_article_repo.findAllByCreatedDate(start_date, end_date);
//
//        return news_articles.size();
//    }
//    public int get_total_cluster_cnt(LocalDateTime start_date,
//                                     LocalDateTime end_date){
//        // 뉴스의 생성일이랑 클러스터의 생성일이 다를 수 있다
//        List<NewsCluster> news_clusters = news_clustser_repo.find_cluster_by_date(start_date, end_date);
//
//        Set<String> cnt_map = new HashSet<>();
//        for(NewsCluster cluster : news_clusters) cnt_map.add(cluster.cluster_id);
//
//        return cnt_map.size();
//    }
//    public int get_total_news_cnt_by_domain(LocalDateTime start_date,
//                                            LocalDateTime end_date,
//                                            Domain domain){
//        List<NewsArticle> news_articles = news_article_repo.findAllByCreatedDate(start_date, end_date);
//
//        int cnt = 0;
//        for(news_articles.get_domain().equals(domain)){
//            cnt += 1;
//        }
//
//        return cnt;
//    }
//    public int get_total_cluster_cnt_by_domain(LocalDateTime start_date,
//                                     LocalDateTime end_date,
//                                     Domain domain){
//        // 뉴스의 생성일이랑 클러스터의 생성일이 다를 수 있다
//        List<NewsCluster> news_clusters = news_clustser_repo.find_cluster_by_date(start_date, end_date);
//
//        Set<String> cnt_map = new HashSet<>();
//        for(NewsCluster cluster : news_clusters){
//            if(cluster.getNewsArticle.getDomain().equals(domain)){
//                cnt_map.add(cluster.cluster_id);
//            }
//        }
//        return cnt_map.size();
//    }
//    public List<IssueDTO> get_issue(LocalDateTime start_date,
//                                    LocalDateTime end_date){
//        List<NewsCluster> news_clusters = news_clustser_repo.find_cluster_by_date(start_date, end_date);
//
//        List<IssueDTO> issues = new ArrayList<>();
//        Map<String, NewsCluster> cluster_map = new HashMap<>();
//        for(NewsCluster cluster : news_clusters){
//            cnt_map.add(cluster.cluster_id, cluster);
//        }
//
//        (key, value : cluster_map){
//            IssueDTO issue = new IssueDTO();
//            issue.setCluster_cnt(value.size());
//            issue.setCluster_title(value[0].title);
//            issue.setCreated_date(end_date);
//
//            List<NewsArticleDTO> news_articles = new ArrayList<>();
//            for(NewsCluster news_cluster : value){
//                news_articles.append(new NewsArticleDTO(news_cluster.getNewsArticle()));
//            }
//            issue.setNews_articles(news_articles);
//            issues.append(issue);
//        }
//
//        return issues;
//    }
//    public List<IssueDTO> get_issue_domain(LocalDateTime start_date,
//                                    LocalDateTime end_date,Domain domain){}

}
