package com.totu.service.parser;

import org.apache.commons.lang3.StringUtils;


public class ParserUtil {
    public static String nbspToBlank(String s) {
        return StringUtils.replace(s, "&nbsp;", "");
    }
}
