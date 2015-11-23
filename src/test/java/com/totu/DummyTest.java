package com.totu;


import com.totu.service.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class DummyTest {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
        Random randomGenerator = new Random();

        System.out.println(StringUtils.substring(RandomUtil.generateActivationKey(), 0, 17));

        String time = System.currentTimeMillis() + "_0." + StringUtils.substring(RandomUtil.generateActivationKey(), 0, 16);
        System.out.println(time);
    }
}
