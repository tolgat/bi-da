package com.totu.service.parser.estate;

import com.totu.domain.Price;
import com.totu.domain.market.Estate;
import com.totu.domain.market.Site;
import com.totu.repository.market.EstateRepository;
import com.totu.service.parser.ParserUtil;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        LOG.debug("List page: " + url);

        Set<String> items = new HashSet<>();
        Set<String> nextPages = new HashSet<>();

        try {
            Document doc = Utils.getJsoupDocument(url);
            Elements links = doc.select("a[href]");

            if (links != null) {
                for (Element link : links) {
                    String absUrl = link.attr("abs:href");

                    if (equalsIgnoreCase("Sonraki", link.text())) {
                        nextPages.add(absUrl);
                    }

                    if (StringUtils.contains(absUrl, "sahibinden.com/ilan/")) {
                        items.add(absUrl);
                    }
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
        StringBuilder processLogSb = new StringBuilder();

        try {
            Estate item = new Estate();
            Document docWhole = Utils.getJsoupDocument(url);
            if (docWhole != null) {
                Element part = docWhole.getElementById("classifiedDetail");
                if (part != null) {
                    item.setResourceSite(Site.SAHIBINDEN);
                    item.setUrl(url);

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
                        item.setCity(locations.get(0).text());
                        item.setDistrict(locations.get(1).text());
                        item.setNeighborhood(locations.get(2).text());
                    } catch (Exception e) {
                        Utils.appendProcessLog(processLogSb, "Location alinamadi", e);
                    }

                    // Ana özellikler
                    Elements lis = part.getElementsByClass("classifiedInfoList").select("li");
                    for (Element li : lis) {
                        String tag = null;
                        String val = null;

                        try {
                            tag = StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(1).childNodes().get(0).toString()));
                        } catch (Exception e) {
                            Utils.appendProcessLog(processLogSb, "Tag alinamadi", e);
                        }
                        try {
                            val = StringUtils.trim(ParserUtil.nbspToBlank(li.childNodes().get(3).childNodes().get(0).toString()));
                        } catch (Exception e) {
                            Utils.appendProcessLog(processLogSb, "Value alinamadi", e);
                        }

                        if (equalsIgnoreCase(tag, "İlan No")) item.setRemoteId(val);
                        else if (equalsIgnoreCase(tag, "İlan Tarihi")) item.setPublishDateStr(val);
                        else if (equalsIgnoreCase(tag, "Tipi") || equalsIgnoreCase(tag, "Emlak Tipi"))
                            item.setType(val);
                        else if (equalsIgnoreCase(tag, "m²")) item.setM2(val);
                        else if (equalsIgnoreCase(tag, "m² Fiyatı")) item.setM2Price(val);
                        else if (equalsIgnoreCase(tag, "Oda Sayısı") || equalsIgnoreCase(tag, "Bölüm & Oda Sayısı"))
                            item.setRooms(val);
                        else if (equalsIgnoreCase(tag, "Bina Yaşı")) item.setAge(val);
                        else if (equalsIgnoreCase(tag, "Bulunduğu Kat")) item.setFloor(val);
                        else if (equalsIgnoreCase(tag, "Kat Sayısı")) item.setTotalFloors(val);
                        else if (equalsIgnoreCase(tag, "Banyo Sayısı")) item.setBathrooms(val);
                        else if (equalsIgnoreCase(tag, "Eşyalı")) item.setWithFurniture(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Site İçerisinde")) item.setInSite(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Aidat (TL)")) item.setSiteMonthlyFee(val);
                        else if (equalsIgnoreCase(tag, "İmar Durumu")) item.setImar(val);
                        else if (equalsIgnoreCase(tag, "Krediye Uygun") || equalsIgnoreCase(tag, "Krediye Uygunluk"))
                            item.setCreditable(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Kimden")) item.setSeller(val);
                        else if (equalsIgnoreCase(tag, "Ada No")) item.setAda(val);
                        else if (equalsIgnoreCase(tag, "Parsel No")) item.setParsel(val);
                        else if (equalsIgnoreCase(tag, "Pafta No")) item.setPafta(val);
                        else if (equalsIgnoreCase(tag, "Kaks (Emsal)")) item.setKaks(val);
                        else if (equalsIgnoreCase(tag, "Gabari")) item.setGabari(val);
                        else if (equalsIgnoreCase(tag, "Tapu Durumu")) item.setTapu(val);
                        else if (equalsIgnoreCase(tag, "Takas")) item.setTakas(val);
                        else if (equalsIgnoreCase(tag, "Bir Kattaki Daire")) item.setNumberOfHouseInAFloor(val);
                        else if (equalsIgnoreCase(tag, "Isıtma tipi") || equalsIgnoreCase(tag, "Isıtma"))
                            item.setHeating(val);
                        else if (equalsIgnoreCase(tag, "Dönem")) item.setDonem(val);
                        else if (equalsIgnoreCase(tag, "Süre")) item.setSure(val);

                        //TODO: turistik-tesis attr'lari eklenecek
                    }

                    // Diğer özellikler;
                    try {
                        Map<String, String> propMap = new HashMap<>();
                        Element classifiedProps = docWhole.getElementById("classifiedProperties");
                        Elements propUls = classifiedProps.select("ul");
                        for (Element propUl : propUls) {
                            Elements propLis = propUl.select("li.selected");
                            for (Element propLi : propLis) {
                                String value = StringUtils.trim(ParserUtil.nbspToBlank(propLi.text()));
                                propMap.put(value, null);
                            }
                        }
                        item.setProperties(propMap);
                    } catch (Exception e) {
                        Utils.appendProcessLog(processLogSb, "Other properties alinamadi", e);
                    }

                    item.setProcessLog(processLogSb.toString());
                    saveOne(item);
                }
            }
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
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
            LOG.debug("deger sayi degil: " + s);
        }
        return price;
    }


}
