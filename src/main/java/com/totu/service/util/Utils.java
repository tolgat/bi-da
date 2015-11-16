package com.totu.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tolga on 16.11.2015.
 */
public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    /**
     * Objeyi Json String'e çevirir. Exception oluşursa null döner
     */
    public static String convertObjectToJsonStringSafe(Object obj) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.error("convertObjectToJsonStringSafe error: " + e.toString(), e);
            return null;
        }
    }

    public static Integer parseInteger(String s, String fieldName) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            LOG.error(fieldName + " sayiya cevrilemedi: " + s);
        }
        return null;
    }

    /**
     * "evet" içerirse true döner. Bunun dışında false
     */

    public static boolean convertToBoolean(String s){
        s = StringUtils.trim(s);
        s = StringUtils.lowerCase(s);
        if(StringUtils.equals(s, "evet")){
            return true;
        }

        return false;
    }
}
