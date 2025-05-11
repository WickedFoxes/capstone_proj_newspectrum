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
    private MainPageSevice mainPageService;

    @GetMapping("/")
    public String main(Model model) throws JsonProcessingException, JsonProcessingException {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);

        Map<String, MainBlockDTO> issuesByDomain = mainPageService.get_main_block_list(today);

        model.addAttribute("issuesByDomainJson", issuesByDomain);
        return "main";
    }

}
