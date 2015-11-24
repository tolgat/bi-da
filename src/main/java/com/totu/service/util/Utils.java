package com.totu.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static boolean convertToBoolean(String s) {
        s = StringUtils.trim(s);
        s = StringUtils.lowerCase(s);
        if (StringUtils.equals(s, "evet")) {
            return true;
        }

        return false;
    }

    public static boolean equalNotNull(Long long1, Long long2) {
        if (long1 != null && long2 != null) {
            return long1.longValue() == long2.longValue();
        }

        return false;
    }

    public static boolean equalOrBothNull(Long long1, Long long2) {
        if (long1 != null && long2 != null) {
            return long1.longValue() == long2.longValue();
        }
        return true;
    }


    /**
     * Parse edemezse null doner
     */
    public static Date parseDate(String dateAsString) {

        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy", new Locale("tr"));
        try {
            return formatter.parse(dateAsString);
        } catch (ParseException e) {
            LOG.debug("date parse error:", e);
            return null;
        }
    }


    public static String objectToString(Object o) {
        if (o != null) {
            return String.valueOf(o);
        } else {
            return null;
        }
    }

    public static Integer objectToInteger(Object o) {
        if (o == null) {
            return null;
        }

        Integer i;
        try {
            i = Integer.parseInt(String.valueOf(o));
        } catch (NumberFormatException nfe) {
            i = null;
        }
        return i;
    }

    public static Long objectToLong(Object o) {
        if (o == null) {
            return null;
        }

        Long i;
        try {
            i = Long.parseLong(String.valueOf(o));
        } catch (NumberFormatException nfe) {
            i = null;
        }
        return i;
    }

    public static Double objectToDouble(Object o) {
        if (o == null) {
            return null;
        }

        Double i;
        try {
            i = Double.parseDouble(String.valueOf(o));
        } catch (NumberFormatException nfe) {
            i = null;
        }
        return i;
    }

    //.userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
    public static Document getJsoupDocument(String url) throws IOException {
        return Jsoup.connect(url)
            .timeout(30 * 1000)
            .referrer("http://www.google.com")
            .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
            .cookie("auth", "token")
            .ignoreContentType(true)
            .get();
    }

    public static void appendProcessLog(StringBuilder sb, String msg, Exception e) {
        sb.append("[").append(msg).append(":").append(ExceptionUtils.getRootCauseMessage(e)).append("] ");

    }
}
