package com.capstone.newspectrum;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class SectionPageServiceTest {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
    @Autowired
    private NewsClusterRepo newsClusterRepo;
    @Autowired
    private KeywordRepo keywordRepo;
    @Test
    void getIssueByDomainTest(){
        System.out.println("""
                ######################################################################################
                getIssueTest
                ######################################################################################
                """);
        LocalDateTime start = LocalDateTime.of(2025,02,01,00,00);
        LocalDateTime end = LocalDateTime.of(2025,02,07,23,59);
        Domain domain = Domain.경제;

        List<NewsCluster> newsClusterList = newsClusterRepo.findByNewsArticle_CreatedDateBetween(start, end);

        Map<String, List<NewsCluster>> clusterMap = new HashMap<>();

        List<IssueDTO> issue = new ArrayList<>();

        List<NewsCluster> newsClusterFilteredByDomain = newsClusterList.stream()
                .filter(cluster -> cluster.getNews_article().getDomain().equals(domain))
                .toList();
        //######newsClusterFilteredByDomain 출력해보기##########
//        for(NewsCluster newsCluster: newsClusterFilteredByDomain){
//            System.out.println(newsCluster.getNews_article().getTitle());
//        }

        for (NewsCluster cluster : newsClusterFilteredByDomain) {
            String clusterId = cluster.getClusterId();
            clusterMap.computeIfAbsent(clusterId, k -> new ArrayList<>()).add(cluster);
        }
//#######################clusterMap value가 List<NewsCluster>이므로, entrySet이용해 한번 더 for문으로 NewsArticle꺼내서 출력####
//        for(Map.Entry<String, List<NewsCluster>> testEntry: clusterMap.entrySet()){
//            String clusterId = testEntry.getKey();
//            List<NewsCluster> clusterGroup = testEntry.getValue();
//            for(NewsCluster clusterList_containedCluster: clusterGroup){
//                System.out.println(clusterList_containedCluster.getNews_article().getTitle());
//            }
//            System.out.println(cluster);
//        }
        for (Map.Entry<String, List<NewsCluster>> entry : clusterMap.entrySet()) {
            String clusterId = entry.getKey();
            List<NewsCluster> clusterGroup = entry.getValue();

            //###List<> clusterGroup을 스트림으로 변환하여, 요소를 하나씩 꺼내, NewsArticle article로 변환 후, DTO로 변환###
            List<NewsArticleDTO> newsArticleDTOList = clusterGroup.stream()
                    .map(c -> {
                        NewsArticle article = c.getNews_article();
                        return new NewsArticleDTO(article
                        );
                    })
                    .toList();

            // clusterTitle: 첫 번째 뉴스 제목으로
            String clusterTitle = newsArticleDTOList.isEmpty() ? "No Title" : newsArticleDTOList.get(0).getTitle();

            // 클러스터 생성일: 첫 번째 클러스터의 createdDate
            LocalDateTime createdDate = clusterGroup.get(0).getCreatedDate();

            IssueDTO issueDTO = new IssueDTO(
                    newsArticleDTOList.size(),
                    clusterTitle,
                    newsArticleDTOList,
                    createdDate
            );
            //issue 추가
            issue.add(issueDTO);
        }

        //###########issue 출력해보기#################
        for(IssueDTO issueDTO : issue){
            System.out.println(issueDTO.getCluster_title());
        }
    }

    @Test
    public void getTodayKeywordItemByDomain() {
        System.out.println("""
                ###############################################################################
                getTodayKeywordItemByDomain_Test
                ###############################################################################
                """);

        LocalDateTime start = LocalDateTime.of(2025, 2, 1, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 2, 7, 23, 59);
        Domain domain = Domain.정치;

        List<TodayKeywordItemDTO> today_keyword_item_list = new ArrayList<>();
        List<Keyword> keywordList = keywordRepo.findKeywordByCreatedDateBetween(start, end);

        List<Keyword> keywordFilteredByDomain = keywordList.stream()
                .filter(k -> k.getNews_article().getDomain().equals(domain))
                .toList();

        Map<String, List<Keyword>> keywordMap = new HashMap<>();
        for (Keyword keyword : keywordFilteredByDomain) {
            String kw = keyword.getKeyword();
            keywordMap.computeIfAbsent(kw, s -> new ArrayList<>()).add(keyword);
        }
        for (Map.Entry<String, List<Keyword>> keywordEntry : keywordMap.entrySet()) {
            String keywordText = keywordEntry.getKey();
            List<Keyword> keywordMap_value_KeywordList = keywordEntry.getValue();

            int cnt = keywordMap_value_KeywordList.size();

            // 관련 키워드 목록
            List<Keyword> relatedKeywords = keywordMap_value_KeywordList.stream()
                    .flatMap(k -> k.getRelated_keywords().stream())
                    .map(rel -> rel.getRelated_keyword())
                    .distinct()
                    .toList();

            // 관련 뉴스 기사 리스트
            List<NewsArticleDTO> relatedArticles = keywordMap_value_KeywordList.stream()
                    .map(k -> k.getNews_article())
                    .distinct()
                    .map(article -> new NewsArticleDTO(article))
                    .toList();

            // TodayRelatedKeywordDTO
            TodayRelatedKeywordDTO relatedKeywordDTO = new TodayRelatedKeywordDTO(
                    keywordMap_value_KeywordList.get(0).getCreatedDate(),
                    relatedKeywords,
                    relatedArticles
            );

            // TodayKeywordItemDTO
            TodayKeywordItemDTO itemDTO = new TodayKeywordItemDTO(
                    keywordText,
                    cnt,
                    List.of(relatedKeywordDTO)
            );
            today_keyword_item_list.add(itemDTO);
        }
        //#################출력###########
        for (TodayKeywordItemDTO todayKeywordItemDTOS : today_keyword_item_list) {
            System.out.println(todayKeywordItemDTOS.getToday_keyword());
        }
    }
}
