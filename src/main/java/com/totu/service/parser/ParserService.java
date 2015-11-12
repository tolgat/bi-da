package com.totu.service.parser;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class ParserService {

    int i = 0;
    Set<String> items = new HashSet<String>();

    public void parseSite1() {

        String url1 = "http://www.sahibinden.com/satilik-daire?pagingSize=50&address_quarter=23179&address_quarter=23089&pagingOffset=150&address_town=440&address_town=447&address_country=1&address_city=34";
        parseListPage(url1);


        int itemCount = 0;
        print("AbstractItem URLS: ");
        for (String itemUrl : items) {


            print(itemUrl, null);
            parseItem(itemUrl);
            itemCount++;
            if(itemCount==1){
                break;
            }
        }


    }

    private void parseItem(String url) {
        try {
            Document doc = getDocument(url);
            Elements lis = doc.getElementsByClass("classifiedInfoList > ul");
            for(Element li: lis){
                print("title" + li.select("strong").text());
                print("value" + li.select("span").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseListPage(String url) {
        i++;
        if (i > 1) return;

        try {
            Document doc = getDocument(url);

            Elements links = doc.select("a[href]");

            print("\nLinks size: (%d)", links.size());
            for (Element link : links) {
                String absUrl = link.attr("abs:href");

                if (StringUtils.equals("Sonraki", link.text())) {
                    print(" * a: <%s>  (%s)", absUrl, trim(link.text(), 35));
                    parseListPage(absUrl);
                }

                if (StringUtils.contains(absUrl, "sahibinden.com/ilan/")) {
                    items.add(absUrl);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
            .timeout(30 * 1000)
            .userAgent("Mozilla")
            .cookie("auth", "token")
            .get();
    }


    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }


    //Elements newsHeadlines = doc.select("#mp-itn b a");
}
