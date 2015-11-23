package com.totu.service.parser.estate;

import com.totu.domain.Price;
import com.totu.domain.market.Estate;
import com.totu.domain.market.Site;
import com.totu.repository.market.EstateRepository;
import com.totu.service.parser.ParserUtil;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class SahibindenEstateParser extends AbstractEstateParser {

    private static final Logger LOG = LoggerFactory.getLogger(SahibindenEstateParser.class);


    public SahibindenEstateParser(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    @Override
    public void parse(String url) {
        parseListPage(url);
    }

    @Override
    protected void parseListPage(String url) {

        print(LOG, "List page: %s", url);

        Set<String> items = new HashSet<>();
        Set<String> nextPages = new HashSet<>();

        try {
            Document doc = getDocument(url);
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String absUrl = link.attr("abs:href");

                if (equalsIgnoreCase("Sonraki", link.text())) {
                    nextPages.add(absUrl);
                }

                if (StringUtils.contains(absUrl, "sahibinden.com/ilan/")) {
                    items.add(absUrl);
                }
            }

            items.forEach(this::parseItem);

            nextPages.forEach(this::parseListPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void parseItem(String url) {

        try {
            Estate item = new Estate();
            Document docWhole = getDocument(url);
            Element part = docWhole.getElementById("classifiedDetail");
            item.setResourceSite(Site.SAHIBINDEN);
            item.setUrl(url);
            item.setTitle(part.getElementsByClass("classifiedDetailTitle").select("h1").text());

            // price
            Price price = parsePrice(part.getElementsByClass("classifiedInfo").select("h3").get(0).text());
            item.setPrice(price.getPrice());
            item.setCurrency(price.getCurrency());

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
                if (equalsIgnoreCase(tag, "İlan No")) item.setRemoteId(val);
                else if (equalsIgnoreCase(tag, "İlan Tarihi")) item.setPublishDateStr(val);
                else if (equalsIgnoreCase(tag, "Emlak Tipi")) item.setType(val);
                else if (equalsIgnoreCase(tag, "m²")) item.setM2(val);
                else if (equalsIgnoreCase(tag, "Oda Sayısı")) item.setRooms(val);
                else if (equalsIgnoreCase(tag, "Bina Yaşı")) item.setAge(val);
                else if (equalsIgnoreCase(tag, "Bulunduğu Kat")) item.setFloor(val);
                else if (equalsIgnoreCase(tag, "Kat Sayısı")) item.setTotalFloors(val);
                else if (equalsIgnoreCase(tag, "Banyo Sayısı")) item.setBathrooms(val);
                else if (equalsIgnoreCase(tag, "Eşyalı")) item.setWithFurniture(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Site İçerisinde")) item.setInSite(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Aidat (TL)")) item.setSiteMonthlyFee(val);
                else if (equalsIgnoreCase(tag, "Krediye Uygun")) item.setCreditable(Utils.convertToBoolean(val));
                else if (equalsIgnoreCase(tag, "Kimden")) item.setSeller(val);
            }

            // Diğer özellikler;
            Map<String, String> propMap = new HashMap<>();
            Element classifiedProps = docWhole.getElementById("classifiedProperties");
            if(classifiedProps!=null) {
                Elements propUls = classifiedProps.select("ul");
                if (propUls != null) {
                    for (Element propUl : propUls) {
                        Elements propLis = propUl.select("li.selected");
                        for (Element propLi : propLis) {
                            String value = StringUtils.trim(ParserUtil.nbspToBlank(propLi.text()));
                            propMap.put(value, null);
                        }
                    }
                    item.setProperties(propMap);
                }
            }
            saveOne(item);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Price parsePrice(String s) {

        Price price = new Price();

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
        } else {
            print(LOG, "deger sayi degil %s", s);
        }
        return price;
    }


}
