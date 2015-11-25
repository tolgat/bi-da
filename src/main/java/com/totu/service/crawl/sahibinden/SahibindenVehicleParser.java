package com.totu.service.crawl.sahibinden;

import com.totu.domain.market.Site;
import com.totu.domain.market.Vehicle;
import com.totu.repository.market.VehicleRepository;
import com.totu.service.crawl.AbstractVehicleParser;
import com.totu.service.crawl.SiteParser;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class SahibindenVehicleParser extends AbstractVehicleParser implements SiteParser {

    private static final Logger LOG = LoggerFactory.getLogger(SahibindenVehicleParser.class);


    public SahibindenVehicleParser(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public void parseItem(String url) {
        StringBuilder processLogSb = new StringBuilder();

        try {
            Vehicle item = new Vehicle();
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
                        else if (equalsIgnoreCase(tag, "Marka")) item.setMarka(val);
                        else if (equalsIgnoreCase(tag, "Seri")) item.setSeri(val);
                        else if (equalsIgnoreCase(tag, "Model")) item.setModel(val);
                        else if (equalsIgnoreCase(tag, "Yıl")) item.setYil(val);
                        else if (equalsIgnoreCase(tag, "Yakıt")) item.setYakit(val);
                        else if (equalsIgnoreCase(tag, "Vites")) item.setVites(val);
                        else if (equalsIgnoreCase(tag, "Km")) item.setKm(val);
                        else if (equalsIgnoreCase(tag, "Kasa Tipi")) item.setKasaTipi(val);
                        else if (equalsIgnoreCase(tag, "Motor Hacmi")) item.setMotorHacmi(val);
                        else if (equalsIgnoreCase(tag, "Motor Gücü")) item.setMotorGucu(val);
                        else if (equalsIgnoreCase(tag, "Çekiş")) item.setCekis(val);
                        else if (equalsIgnoreCase(tag, "Renk")) item.setRenk(val);
                        else if (equalsIgnoreCase(tag, "Garanti")) item.setGaranti(val);
                        else if (equalsIgnoreCase(tag, "Plaka / Uyruk")) item.setPlakaUyruk(val);
                        else if (equalsIgnoreCase(tag, "Kimden")) item.setKimden(val);
                        else if (equalsIgnoreCase(tag, "Takas")) item.setTakas(val);
                        else if (equalsIgnoreCase(tag, "Durumu")) item.setDurumu(val);
                    }

                    SahibindenHelper.parseOtherProperties(processLogSb, item, docWhole);

                    item.setProcessLog(processLogSb.toString());
                    saveOne(item);
                }
            }
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
    }


}
