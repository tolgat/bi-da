package com.totu.service.crawl;


import com.totu.repository.market.EstateRepository;
import com.totu.service.crawl.sahibinden.SahibindenCrawler;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class CrawlerService {

    @Inject
    EstateRepository estateRepository;


    public void crawl() {

        new SahibindenCrawler(estateRepository).crawl();

    }
}
