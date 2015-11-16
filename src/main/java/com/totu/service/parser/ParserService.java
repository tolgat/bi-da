package com.totu.service.parser;


import com.totu.domain.market.Estate;
import com.totu.domain.market.Site;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;


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
            if (itemCount == 1) {
                break;
            }
        }


    }

    private Long parsePrice(String s) {
        print("input %s", s);
        s = StringUtils.remove(s, "TL");
        s = StringUtils.remove(s, ".");
        s = StringUtils.trim(s);
        if (NumberUtils.isNumber(s)) {
            return Long.valueOf(s);
        } else {
            print("deger sayi degil %s", s);
        }
        return null;
    }

    private void parseItem(String url) {
        try {
            Estate item = new Estate();
            Document docWhole = getDocument(url);
            Element part = docWhole.getElementById("classifiedDetail");
            item.setResourceSite(Site.SAHIBINDEN);
            item.setTitle(part.getElementsByClass("classifiedDetailTitle").select("h1").text());
            item.setPrice(parsePrice(part.getElementsByClass("classifiedInfo").select("h3").get(0).text()));

            // Lokasyon
            Elements locations = part.getElementsByClass("classifiedInfo").select("h2").get(0).select("a");
            item.setCity(locations.get(0).text());
            item.setDistrict(locations.get(1).text());
            item.setNeighborhood(locations.get(2).text());

            // Ana özellikler
            Elements lis = part.getElementsByClass("classifiedInfoList").select("li");
            for (Element li : lis) {
                String tag = StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(1).childNodes().get(0).toString()));
                String val = StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(3).childNodes().get(0).toString()));
                print(tag);
                if (equalsIgnoreCase(tag, "İlan No")) item.setExternalId(val);
                else if (equalsIgnoreCase(tag, "İlan Tarihi")) item.setPublishDateStr(val);
                else if (equalsIgnoreCase(tag, "Emlak Tipi")) item.setType(val);
                else if (equalsIgnoreCase(tag, "m²")) item.setM2(Utils.parseInteger(val, "m2"));
                else if (equalsIgnoreCase(tag, "Oda Sayısı")) item.setRooms(val);
                else if (equalsIgnoreCase(tag, "Bina Yaşı")) item.setAge(val);
                else if (equalsIgnoreCase(tag, "Bulunduğu Kat")) item.setFloor(Utils.parseInteger(val, "floor"));
                else if (equalsIgnoreCase(tag, "Kat Sayısı")) item.setTotalFloors(Utils.parseInteger(val, "FloorNum"));
                else if (equalsIgnoreCase(tag, "Banyo Sayısı")) item.setBathrooms(Utils.parseInteger(val, "bathrooms"));
                else if (equalsIgnoreCase(tag, "Eşyalı")) item.setWithFurniture(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Site İçerisinde")) item.setInSite(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Aidat (TL)")) item.setSiteMonthlyFee(val);
                else if (equalsIgnoreCase(tag, "Krediye Uygun")) item.setCreditable(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Kimden")) item.setSeller(val);
            }


            // Diğer özellikler;
            Map<String, String> propMap = new HashMap<String, String>();
            Elements propUls = docWhole.getElementById("classifiedProperties").select("ul");
            for (Element propUl : propUls) {
                Elements propLis = propUl.select("li.selected");
                for (Element propLi : propLis) {
                    String value = StringUtils.trim(ParserUtil.nbspToBlank(propLi.text()));
                    propMap.put(value, null);
                }
            }
            item.setProperties(propMap);

            print(Utils.convertObjectToJsonStringSafe(item));

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

                if (equalsIgnoreCase("Sonraki", link.text())) {
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
            .referrer("http://www.google.com")
            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
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
