package com.capstone.newspectrum.dto;

import com.capstone.newspectrum.enumeration.CheckType;
import com.capstone.newspectrum.model.ContentCheck;
import com.capstone.newspectrum.model.NewsArticle;

public class ContentCheckDTO {
    private Long id;
    private NewsArticle news_article;
    private String keyword;
    private CheckType content_check_type;
    private Float score;

    public ContentCheckDTO(ContentCheck contentCheck){
        this.id = contentCheck.getId();
        this.news_article = contentCheck.getNews_article();
        this.keyword = contentCheck.getKeyword();
        this.content_check_type = contentCheck.getContent_check_type();
        this.score = contentCheck.getScore();
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public NewsArticle getNewsArticle(){
        return news_article;
    }
    public void setNews_article(NewsArticle news_article){
        this.news_article = news_article;
    }
    public String getKeyword(){
        return keyword;
    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
    public CheckType getContent_check_type(){
        return content_check_type;
    }
    public void setContent_check_type(CheckType content_check_type){
        this.content_check_type = content_check_type;
    }
    public Float getScore(){
        return score;
    }
    public void setScore(Float score){
        this.score = score;
    }
}
