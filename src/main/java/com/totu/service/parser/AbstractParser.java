package com.totu.service.parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.IOException;

public abstract class AbstractParser {

    public abstract void parse(String url);

    protected abstract void parseListPage(String url);

    protected abstract void parseItem(String url);


    protected Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
            .timeout(30 * 1000)
            .referrer("http://www.google.com")
            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
            .cookie("auth", "token")
            .get();
    }

    protected static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }

    protected static void print(Logger logger, String msg, Object... args) {
        logger.debug(String.format(msg, args));
    }


}
