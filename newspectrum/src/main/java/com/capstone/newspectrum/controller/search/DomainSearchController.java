package com.capstone.newspectrum.controller.search;

import com.capstone.newspectrum.service.search.DomainSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DomainSearchController {
    @Autowired
    private DomainSearchService domainSearchService;
    @RequestMapping("/domain")

}
