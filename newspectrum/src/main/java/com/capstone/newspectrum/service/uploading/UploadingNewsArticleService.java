package com.capstone.newspectrum.service.uploading;

import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.model.NewsArticle;
import com.capstone.newspectrum.repository.NewsArticleRepo;

public class UploadingNewsArticleService {
    private NewsArticleRepo newsArticleRepo;
    public NewsArticleDTO saveNewsArticle(NewsArticleDTO newsArticleDTO){
        newsArticleRepo.save(new NewsArticle(newsArticleDTO));
        return newsArticleDTO;
    }
}
