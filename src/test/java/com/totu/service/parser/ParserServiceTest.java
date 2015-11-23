package com.totu.service.parser;

import com.totu.IntegrationTestAbstractTestNg;
import org.testng.annotations.Test;

import javax.inject.Inject;


public class ParserServiceTest extends IntegrationTestAbstractTestNg {

    @Inject
    ParserService parserService;

    @Test
    public void testAll() throws Exception {
        parserService.parseAll();
    }
}
