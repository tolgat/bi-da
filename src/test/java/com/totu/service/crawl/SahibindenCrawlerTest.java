package com.totu.service.crawl;


import com.totu.service.crawl.sahibinden.SahibindenCrawler;
import org.testng.annotations.Test;

public class SahibindenCrawlerTest {

    @Test
    public void crawlTest() {

        SahibindenCrawler sahibindenCrawler = new SahibindenCrawler();
        sahibindenCrawler.crawl();


    }
}
