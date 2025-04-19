package com.capstone.newspectrum.service.search;

import com.capstone.newspectrum.repository.NewsArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImple implements SearchServiceInterface {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
}
