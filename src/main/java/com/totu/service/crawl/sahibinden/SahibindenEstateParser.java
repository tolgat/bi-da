package com.totu.service.crawl.sahibinden;

import com.totu.domain.market.Estate;
import com.totu.domain.market.Site;
import com.totu.repository.market.EstateRepository;
import com.totu.service.crawl.AbstractEstateParser;
import com.totu.service.crawl.SiteParser;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class SahibindenEstateParser extends AbstractEstateParser implements SiteParser {

    private static final Logger LOG = LoggerFactory.getLogger(SahibindenEstateParser.class);


    public SahibindenEstateParser(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    @Override
    public void parseItem(String url) {
        StringBuilder processLogSb = new StringBuilder();

        try {
            Estate item = new Estate();

            Document docWhole = Utils.getJsoupDocument(url);
            if (docWhole != null) {
                Element part = docWhole.getElementById("classifiedDetail");
                if (part != null) {
                    item.setResourceSite(Site.SAHIBINDEN);
                    item.setUrl(url);

                    SahibindenHelper.parseCommonItem(processLogSb, item, part);

                    // Ana özellikler
                    Elements lis = part.getElementsByClass("classifiedInfoList").select("li");
                    for (Element li : lis) {
                        String tag = SahibindenHelper.getMainPropertyTag(processLogSb, li);
                        String val = SahibindenHelper.getMainPropertyValue(processLogSb, li);

                        if (equalsIgnoreCase(tag, "İlan No")) item.setRemoteId(val);
                        else if (equalsIgnoreCase(tag, "İlan Tarihi")) item.setPublishDateAsStr(val);
                        else if (equalsIgnoreCase(tag, "Tipi") || equalsIgnoreCase(tag, "Emlak Tipi"))
                            item.setTip(val);
                        else if (equalsIgnoreCase(tag, "m²")) item.setM2(val);
                        else if (equalsIgnoreCase(tag, "m² Fiyatı")) item.setM2Fiyat(val);
                        else if (equalsIgnoreCase(tag, "Oda Sayısı") || equalsIgnoreCase(tag, "Bölüm & Oda Sayısı"))
                            item.setOda(val);
                        else if (equalsIgnoreCase(tag, "Bina Yaşı")) item.setYas(val);
                        else if (equalsIgnoreCase(tag, "Bulunduğu Kat")) item.setKat(val);
                        else if (equalsIgnoreCase(tag, "Kat Sayısı")) item.setToplamKat(val);
                        else if (equalsIgnoreCase(tag, "Banyo Sayısı")) item.setBanyo(val);
                        else if (equalsIgnoreCase(tag, "Eşyalı")) item.setEsyali(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Site İçerisinde"))
                            item.setSiteIcınde(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Aidat (TL)")) item.setAidat(val);
                        else if (equalsIgnoreCase(tag, "İmar Durumu")) item.setImar(val);
                        else if (equalsIgnoreCase(tag, "Krediye Uygun") || equalsIgnoreCase(tag, "Krediye Uygunluk"))
                            item.setKreidyeUygun(Utils.convertToBoolean(val));
                        else if (equalsIgnoreCase(tag, "Kimden")) item.setKimden(val);
                        else if (equalsIgnoreCase(tag, "Ada No")) item.setAda(val);
                        else if (equalsIgnoreCase(tag, "Parsel No")) item.setParsel(val);
                        else if (equalsIgnoreCase(tag, "Pafta No")) item.setPafta(val);
                        else if (equalsIgnoreCase(tag, "Kaks (Emsal)")) item.setKaks(val);
                        else if (equalsIgnoreCase(tag, "Gabari")) item.setGabari(val);
                        else if (equalsIgnoreCase(tag, "Tapu Durumu")) item.setTapu(val);
                        else if (equalsIgnoreCase(tag, "Takas")) item.setTakas(val);
                        else if (equalsIgnoreCase(tag, "Bir Kattaki Daire")) item.setKattakiDaireSayisi(val);
                        else if (equalsIgnoreCase(tag, "Isıtma tipi") || equalsIgnoreCase(tag, "Isıtma"))
                            item.setIsinma(val);
                        else if (equalsIgnoreCase(tag, "Dönem")) item.setDonem(val);
                        else if (equalsIgnoreCase(tag, "Süre")) item.setSure(val);

                        //TODO: turistik-tesis attr'lari eklenecek
                    }

                    SahibindenHelper.parseOtherProperties(processLogSb, item, docWhole);

                    item.setProcessLog(processLogSb.toString());
                    saveOne(item);
                }
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
