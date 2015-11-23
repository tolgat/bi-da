package com.totu.service.crawl;


import com.totu.IntegrationTestAbstractTestNg;
import com.totu.service.crawl.sahibinden.SahibindenCrawler;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class CrawlerServiceTest  extends IntegrationTestAbstractTestNg {

    @Inject
    CrawlerService crawlerService;

    @Test
    public void testAll() throws Exception {
        crawlerService.crawl();
    }

}
