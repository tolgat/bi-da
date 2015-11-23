package com.totu.service.util;


import org.testng.annotations.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;

public class UtilsTest {

    @Test
    public void dummyTest() {

        Date d;
        d = Utils.parseDate("03 Şubat 2015");


        String[] dateTexts = {"03 Şubat 2015", "6 Kasım 2002","04 Ocak 2015"};
        for (String str : dateTexts) {
            Utils.parseDate(str);
        }
    }
}
