package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.*;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.enumeration.Media;
import com.capstone.newspectrum.service.MainPageSevice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private MainPageSevice mainPageSevice;

    @GetMapping("/")
    public String main(Model model) throws JsonProcessingException, JsonProcessingException {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);

        Map<String, List<MainBlockDTO>> issuesByDomain = new HashMap<>();
        issuesByDomain.put("정치", mainPageSevice.get_main_block_list_by_domain(today, Domain.정치));
        issuesByDomain.put("경제", mainPageSevice.get_main_block_list_by_domain(today, Domain.경제));
        issuesByDomain.put("사회", mainPageSevice.get_main_block_list_by_domain(today, Domain.사회));
        issuesByDomain.put("연예", mainPageSevice.get_main_block_list_by_domain(today, Domain.연예));
        issuesByDomain.put("스포츠", mainPageSevice.get_main_block_list_by_domain(today, Domain.스포츠));

        // JSON 변환
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String issuesByDomainJson = mapper.writeValueAsString(issuesByDomain);

        model.addAttribute("issuesByDomainJson", issuesByDomainJson);
        return "main";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<MainBlockDTO> mainBlocks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            // 뉴스 기사 생성
            List<NewsArticleDTO> articles = new ArrayList<>();
            for (int j = 1; j <= 20; j++) {
                NewsArticleDTO dto = new NewsArticleDTO();
                dto.setId((long) j);
                dto.setTitle("클러스터 " + i + " 뉴스 제목 " + j);
                dto.setContent("이것은 클러스터 " + i + "의 뉴스 본문 내용 예시입니다.");
                dto.setMedia(Media.values()[j % Media.values().length]); // Enum 순환
                dto.setDomain(Domain.values()[j % Domain.values().length]); // Enum 순환
                dto.setHref("https://example.com/news/" + i + "/" + j);
                dto.setImg_url("https://via.placeholder.com/120x80?text=뉴스" + j);
                dto.setCreatedDate(LocalDateTime.now().minusDays(j));
                dto.setRelated_news_articles(new ArrayList<>());
                dto.setNews_hyperlinks(new ArrayList<>());
                articles.add(dto);
            }

            // 키워드 및 빈도
            List<String> keywords = new ArrayList<>();
            List<Integer> keywordCounts = new ArrayList<>();
            for (int k = 1; k <= 5; k++) {
                keywords.add("키워드" + k);
                keywordCounts.add(rand.nextInt(10) + 1); // 1~10
            }

            // Top Keyword 관련 키워드 시간별 변화
            List<MainBlockTopKeywordDTO> topKeywords = new ArrayList<>();
            String topKeyword = "키워드1";

            List<MainBlockRelatedKeywordsDTO> timeline = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (int t = 0; t < 3; t++) {
                timeline.add(new MainBlockRelatedKeywordsDTO(
                        now.minusDays((3 - t) * 7),
                        now.minusDays((2 - t) * 7),
                        List.of("연관" + (t + 1) + "-1", "연관" + (t + 1) + "-2")
                ));
            }

            MainBlockTopKeywordDTO topKeywordDTO = new MainBlockTopKeywordDTO(topKeyword, timeline);
            topKeywords.add(topKeywordDTO);

            // 최종 MainBlockDTO 구성
            MainBlockDTO block = new MainBlockDTO(
                    Domain.정치,
                    articles,
                    articles.size(),
                    keywords,
                    keywordCounts,
                    topKeywords
            );

            mainBlocks.add(block);
        }

        model.addAttribute("mainBlocks", mainBlocks);
        return "main";
    }


//    @GetMapping("/test")
//    public String main_test(Model model) {
//        List<IssueDTO> issues = new ArrayList<>();
//        List<TodayKeywordItemDTO> todayKeywordItems = new ArrayList<>(); // 실제 서비스에서 가져와야 함
//        LocalDateTime now = LocalDateTime.now();
//        int total_news_cnt = 100; // 실제 서비스에서 가져와야 함
//        int total_cluster_cnt = 10; // 실제 서비스에서 가져와야 함
//
//        // 오늘의 이슈 임시 데이터
//        for (int i = 1; i <= 5; i++) {
//            // 클러스터 타이틀 및 생성일 설정
//            String clusterTitle = "이슈 제목 " + i;
//            LocalDateTime createdDate = LocalDateTime.now().minusDays(i); // 예시로 하루씩 과거로 설정
//            List<NewsArticleDTO> newsArticles = new ArrayList<>();
//
//            for (int j = 1; j <= 10; j++) {
//                NewsArticleDTO article = new NewsArticleDTO();
//                article.setTitle("기사 제목 " + i + "-" + j);
//                article.setImg_url("https://via.placeholder.com/150?text=Image+" + i + "-" + j);
//                article.setHref("https://example.com/article" + i + "-" + j);
//                article.setMedia(Media.sbs); // Media는 enum 타입이므로 실제 enum 값 사용
//                article.setCreatedDate(LocalDateTime.now().minusHours(j)); // 생성시간 예시
//                newsArticles.add(article);
//            }
//
//            IssueDTO issue = new IssueDTO(
//                    newsArticles.size(),      // 클러스터 내 뉴스 개수
//                    clusterTitle,             // 클러스터 타이틀
//                    newsArticles,             // 뉴스 리스트
//                    createdDate               // 생성일
//            );
//            issues.add(issue);
//        }
//
//        //오늘의 키워드 임시 데이터
//        for (int i = 1; i <= 10; i++) {
//            String keywordName = "키워드" + i;
//            int count = 50 + i;
//            List<TodayRelatedKeywordDTO> relatedList = new ArrayList<>();
//
//            for (int j = 0; j < 4; j++) {
//                LocalDateTime date = now.minusWeeks(j);
//                List<String> keywords = new ArrayList<>();
//                List<NewsArticleDTO> articles = new ArrayList<>();
//                for (int k = 1; k <= 10; k++) {
//                    keywords.add("연관" + i + "-" + j + "-" + k);
//                }
//                for (int k = 1; k <= 5; k++) {
//                    NewsArticleDTO dto = new NewsArticleDTO();
//                    dto.setTitle("[" + keywordName + "] 뉴스 " + k + " (" + date.toLocalDate() + ")");
//                    dto.setImg_url("https://via.placeholder.com/100?text=" + keywordName + "+" + k);
//                    dto.setMedia(Media.jtbc);
//                    articles.add(dto);
//                }
//                relatedList.add(new TodayRelatedKeywordDTO(date, keywords, articles));
//            }
//
//            todayKeywordItems.add(new TodayKeywordItemDTO(keywordName, count, relatedList));
//        }
//
//
//        model.addAttribute("issues", issues);
//        model.addAttribute("today_keyword_items", todayKeywordItems);
//        model.addAttribute("total_news_cnt", total_news_cnt);
//        model.addAttribute("total_cluster_cnt", total_cluster_cnt);
//        //model.addAttribute("today_keyword_items", today_keyword_items);
//        return "main";
//    }


}
