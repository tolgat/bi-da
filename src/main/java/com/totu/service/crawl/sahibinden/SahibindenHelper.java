package com.totu.service.crawl.sahibinden;

import com.mongodb.BasicDBList;
import com.totu.domain.Price;
import com.totu.domain.market.AbstractItem;
import com.totu.service.crawl.ParserUtil;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SahibindenHelper {

    public static void parseCommonItem(StringBuilder processLogSb, AbstractItem item, Element part) {
        try {
            item.setTitle(part.getElementsByClass("classifiedDetailTitle").select("h1").text());
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Title alinamadi", e);
        }

        // price
        try {
            Price price = parsePrice(part.getElementsByClass("classifiedInfo").select("h3").get(0).text());
            item.setPrice(price.getPrice());
            item.setCurrency(price.getCurrency());
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Price alinamadi", e);
        }

        // Lokasyon
        try {
            Elements locations = part.getElementsByClass("classifiedInfo").select("h2").get(0).select("a");
            item.setIl(locations.get(0).text());
            item.setIlce(locations.get(1).text());
            item.setMahalle(locations.get(2).text());
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Location alinamadi", e);
        }
    }

    public static void parseOtherProperties(StringBuilder processLogSb, AbstractItem item, Document docWhole) {
        // Diğer özellikler;
        try {
            BasicDBList props = new BasicDBList();
            Element classifiedProps = docWhole.getElementById("classifiedProperties");
            Elements propUls = classifiedProps.select("ul");
            for (Element propUl : propUls) {
                Elements propLis = propUl.select("li.selected");
                for (Element propLi : propLis) {
                    String value = StringUtils.trim(ParserUtil.nbspToBlank(propLi.text()));
                    props.add(value);
                }
            }
            item.setProperties(props);
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Other properties alinamadi", e);
        }
    }


    public static String getMainPropertyValue(StringBuilder processLogSb, Element li) {
        try {
            return StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(3).childNodes().get(0).toString()));
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Value alinamadi", e);
        }
        return null;
    }

    public static String getMainPropertyTag(StringBuilder processLogSb, Element li) {
        try {
            return StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(1).childNodes().get(0).toString()));
        } catch (Exception e) {
            Utils.appendProcessLog(processLogSb, "Tag alinamadi", e);
        }
        return null;
    }

    private static Price parsePrice(String s) {

        Price price = new Price();

        s = StringUtils.remove(s, "Kredi Teklifleri");

        if (StringUtils.contains(s, "TL")) {
            s = StringUtils.remove(s, "TL");
            price.setCurrency("TL");
        } else if (StringUtils.contains(s, "$")) {
            s = StringUtils.remove(s, "$");
            price.setCurrency("USD");
        } else if (StringUtils.contains(s, "€")) {
            s = StringUtils.remove(s, "€");
            price.setCurrency("EUR");
        } else if (StringUtils.contains(s, "£")) {
            s = StringUtils.remove(s, "£");
            price.setCurrency("GBP");
        }

        s = StringUtils.remove(s, ".");
        s = StringUtils.trim(s);

        if (NumberUtils.isNumber(s)) {
            price.setPrice(Long.valueOf(s));
        }
        return price;
    }

}
