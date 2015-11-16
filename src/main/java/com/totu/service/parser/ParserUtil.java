package com.totu.service.parser;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by tolga on 16.11.2015.
 */
public class ParserUtil {
    public static String nbspToBlank(String s){
        return StringUtils.replace(s, "&nbsp;", "");
    }
}
