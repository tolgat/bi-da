package com.totu.service.parser;

import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class ParserServiceTest {

    @Test
    public void testParseSite1() throws Exception {
        ParserService parserService = new ParserService();
        parserService.parseSite1();
    }
}
