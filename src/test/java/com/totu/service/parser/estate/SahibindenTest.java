package com.totu.service.parser.estate;

import com.totu.IntegrationTestAbstractTestNg;
import com.totu.repository.market.EstateRepository;
import com.totu.service.crawl.sahibinden.SahibindenEstateParser;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class SahibindenTest  extends IntegrationTestAbstractTestNg {

    @Inject
    EstateRepository estateRepository;


    @Test
    public void testOneItem() throws Exception {
        new SahibindenEstateParser(estateRepository).parseItem("http://www.sahibinden.com/ilan/emlak-konut-satilik-bodrum-akyarlar-satilik-dublex-184779987/detay");
    }
}
