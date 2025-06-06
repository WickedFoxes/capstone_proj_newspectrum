package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.enumeration.CheckType;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.enumeration.Media;
import com.capstone.newspectrum.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Media media;
    private Domain domain;
    private String href;
    private String img_url;
    private LocalDateTime createdDate;
    private List<Long> related_news_articles;
    private List<String> keywords;
    private String comics_url;
    private String summary;
    private List<ContentCheckDTO> title_checks;
    private Map<String, Map<String, List<ContentCheckDTO>>> content_checks;

    public NewsArticleDTO() {
    }
    public NewsArticleDTO(NewsArticle news_article){
        this.id = news_article.getId();
        this.title = news_article.getTitle().replace("\n", "");
        this.content = news_article.getContent();
        this.media = news_article.getMedia();
        this.domain = news_article.getDomain();
        this.href = news_article.getHref();
        this.img_url = news_article.getImg_url();
        this.createdDate = news_article.getCreatedDate();
        this.related_news_articles = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.comics_url = news_article.getComics_url();
        this.summary = news_article.getSummary();
        this.title_checks = new ArrayList<>();
        this.content_checks = new HashMap<>();

        for (NewsArticleRelation news_article_relation : news_article.getRelated_news_articles()){
            related_news_articles.add(news_article_relation.getNews_article().getId());
        }
        for (Keyword keyword : news_article.getKeywords()){
            this.keywords.add(keyword.getKeyword());
        }

        Map<String, List<ContentCheckDTO>> items = new HashMap<>();
        for(ContentCheck contentCheck : news_article.getContentChecks()){
            String checkTypeName = contentCheck.getContent_check_type().name(); // enum 이름을 문자열로
            if (checkTypeName.startsWith("title_")) {
                this.title_checks.add(new ContentCheckDTO(contentCheck));
            }
            else{
                String keyword = contentCheck.getKeyword().replace("\n", "");
                ContentCheckDTO dto = new ContentCheckDTO(contentCheck); // 필요에 따라 생성자 사용

                items
                    .computeIfAbsent(keyword, k -> new ArrayList<>())
                    .add(dto);
            }
        }
        // title_check 내림차순 정렬
        title_checks.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        // items 내림차순 정렬
        for (List<ContentCheckDTO> dtoList : items.values()) {
            dtoList.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        }

        // content_checks 생성
        for (List<ContentCheckDTO> dtoList : items.values()) {
            ContentCheckDTO content = dtoList.get(0);
            content_checks
                    .computeIfAbsent(content.getContent_check_type().name(), t -> new HashMap<>()) // type 레벨 리스트 생성
                    .computeIfAbsent(content.getKeyword(), k -> dtoList); // keyword 레벨 map 생성
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Long> getRelated_news_articles() {
        return related_news_articles;
    }

    public void setRelated_news_articles(List<Long> related_news_articles) {
        this.related_news_articles = related_news_articles;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public String getComics_url(){
        return comics_url;
    }
    public void setComics_url(String comics_url){
        this.comics_url = comics_url;
    }
    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }

    public List<ContentCheckDTO> getTitle_checks() {
        return title_checks;
    }

    public void setTitle_checks(List<ContentCheckDTO> title_checks) {
        this.title_checks = title_checks;
    }

    public Map<String, Map<String, List<ContentCheckDTO>>> getContent_checks() {
        return content_checks;
    }

    public void setContent_checks(Map<String, Map<String, List<ContentCheckDTO>>> content_checks) {
        this.content_checks = content_checks;
    }
}
