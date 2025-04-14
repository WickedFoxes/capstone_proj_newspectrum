package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.service.MainPageSevice;
import com.capstone.newspectrum.dto.MainBlockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MainPageSevice mainPageSevice;

    @GetMapping("/")
    public String main(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        List<MainBlockDTO> today_issues = mainPageSevice.get_today_issue_list(today);

        model.addAttribute("today_issues", today_issues);
        return "main";
    }

//    @GetMapping("/")
//    public String main(Model model) {
//        List<IssueDTO> issues = new ArrayList<>();
//        int total_news_cnt = 0; // service를 통해 가져와야 함
//        int total_cluster_cnt = 0; // service를 통해 가져와야 함
//        List<TodayKeywordItemDTO> today_keyword_items = null; // service를 통해 가져와야 함
//
//        model.addAttribute("issues", issues);
//        model.addAttribute("total_news_cnt", total_news_cnt);
//        model.addAttribute("total_cluster_cnt", total_cluster_cnt);
//        model.addAttribute("today_keyword_items", today_keyword_items);
//        return "main";
//    }
//
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
