package com.totu.service.crawl.sahibinden;

import com.jayway.jsonpath.JsonPath;
import com.totu.repository.market.EstateRepository;
import com.totu.service.crawl.AbstractCrawler;
import com.totu.service.crawl.sahibinden.domain.*;
import com.totu.service.parser.estate.SahibindenEstateParser;
import com.totu.service.util.RandomUtil;
import com.totu.service.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * JSON sonuclarini alıp kategoriye göre parse eden class'ı çağırır.
 */
public class SahibindenCrawler extends AbstractCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(SahibindenCrawler.class);

    private static final String TR_CODE = "1";

    private final EstateRepository estateRepository;

    public SahibindenCrawler(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public void crawl() {
        crawlEstate();
    }


    private void crawlEstate() {

        // Emlak
        final List<String> categories = Arrays.asList(
            "satilik",
            "kiralik",
            "gunluk-kiralik",
            "satilik-isyeri",
            "kiralik-isyeri",
            "devren-isyeri",
            "satilik-arsa",
            "kiralik-arsa",
            "satilik-bina",
            "kiralik-bina",
            "devremulk",
            "emlak-turistik-tesis-satilik",
            "emlak-turistik-tesis-kiralik");

        String ESTATE_CODE = "3518";
        LinkedHashMap<String, Integer> estateSubCategories = crawlSubCategories(ESTATE_CODE);
        if (estateSubCategories != null) {
            estateSubCategories.forEach((category, value) -> LOG.debug(category + ": " + value));
        }

        List<City> cityList = crawlCities();
        if (cityList != null) {
            cityList.forEach(city -> {

                List<Town> townList = crawlTowns(city.getId().toString());
                if (townList != null) {
                    townList.forEach(town -> {

                        List<District> districtList = crawlDistricts(town);
                        if (districtList != null) {
                            districtList.forEach(district -> {

                                if (district.getQuarterList() != null) {
                                    district.getQuarterList().forEach(quarter -> {

                                        // her bir kategori icin parser'i cagir
                                        categories.forEach(category -> {
                                            String parseUrl = "http://www.sahibinden.com/" + category +
                                                "?address_town=" + town.getId() +
                                                "&address_city=" + city.getId() +
                                                "&address_quarter=" + quarter.getId();


                                            //FIXME: sadece istanbul/ataşehir'i test edelim
                                            if (city.getId() == 1 && quarter.getId() == 293) {
                                                LOG.debug("parse " + parseUrl);
                                                SahibindenEstateParser sahibindenEstateParser = new SahibindenEstateParser(estateRepository);
                                                sahibindenEstateParser.parse(parseUrl);
                                            }
                                        });
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }

    }


    private List<City> crawlCities() {

        List<City> cityList = new ArrayList<>();

        try {
            Document doc = Utils.getJsoupDocument(getUrl(URL_TYPE.CITY, TR_CODE));
            List<LinkedHashMap<String, Object>> cities = JsonPath.parse(doc.body().text()).read("$.data." + TR_CODE);

            cities.forEach((cityMap) -> {
                City city = new City();
                city.setId(Utils.objectToLong(cityMap.get("id")));
                city.setName(Utils.objectToString(cityMap.get("name")));
                city.setTag(Utils.objectToString(cityMap.get("tag")));
                city.setDisplayOrder(Utils.objectToInteger(cityMap.get("displayOrder")));
                city.setSortOrder(Utils.objectToInteger(cityMap.get("sortOrder")));

                //detail
                LinkedHashMap detailMap = (LinkedHashMap) cityMap.get("detail");
                if (detailMap != null) {
                    city.setLocationId(Utils.objectToLong(detailMap.get("location_id")));
                    city.setLat(Utils.objectToDouble(detailMap.get("lat")));
                    city.setLon(Utils.objectToDouble(detailMap.get("lon")));
                    city.setZoom(Utils.objectToLong(detailMap.get("zoom")));
                }

                // country
                LinkedHashMap countryMap = (LinkedHashMap) cityMap.get("country");
                if (countryMap != null) {
                    Country country = new Country();
                    country.setId(Utils.objectToLong(countryMap.get("id")));
                    country.setName(Utils.objectToString(countryMap.get("name")));
                    country.setAbbreviation(Utils.objectToString(countryMap.get("abbreviation")));
                    country.setLanguage(Utils.objectToString(countryMap.get("language")));
                    country.setDisplayOrder(Utils.objectToInteger(countryMap.get("displayOrder")));
                    country.setSortOrder(Utils.objectToInteger(countryMap.get("sortOrder")));
                    city.setCountry(country);
                }

                cityList.add(city);
            });

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
        return cityList;

    }

    private List<Town> crawlTowns(String cityId) {

        List<Town> townList = new ArrayList<>();

        try {
            Document doc = Utils.getJsoupDocument(getUrl(URL_TYPE.TOWN, cityId));
            List<LinkedHashMap<String, Object>> districts = JsonPath.parse(doc.body().text()).read("$.data." + cityId);

            districts.forEach((districtMap) -> {

                Town town = new Town();
                town.setId(Utils.objectToLong(districtMap.get("id")));
                town.setName(Utils.objectToString(districtMap.get("name")));

                //detail
                LinkedHashMap detailMap = (LinkedHashMap) districtMap.get("detail");
                if (detailMap != null) {
                    town.setLocationId(Utils.objectToLong(detailMap.get("location_id")));
                    town.setLat(Utils.objectToDouble(detailMap.get("lat")));
                    town.setLon(Utils.objectToDouble(detailMap.get("lon")));
                    town.setZoom(Utils.objectToLong(detailMap.get("zoom")));
                }
                townList.add(town);
            });

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
        return townList;

    }

    private List<District> crawlDistricts(Town town) {
        List<District> districtList = new ArrayList<>();

        try {
            Document doc = Utils.getJsoupDocument(getUrl(URL_TYPE.DISTRICT, town.getId().toString()));
            List<LinkedHashMap<String, Object>> districts = JsonPath.parse(doc.body().text()).read("$.data." + town.getId().toString());

            districts.forEach((districtMap) -> {
                List<Quarter> quarterList = new ArrayList<>();

                District district = new District();
                district.setId(Utils.objectToLong(districtMap.get("id")));
                district.setName(Utils.objectToString(districtMap.get("name")));

                // quarter (asıl mahalle)
                @SuppressWarnings("unchecked")
                List<LinkedHashMap<String, Object>> quarters = (List<LinkedHashMap<String, Object>>) districtMap.get("quarters");
                quarters.forEach((quarterMap) -> {
                    Quarter quarter = new Quarter();
                    quarter.setId(Utils.objectToLong(quarterMap.get("id")));
                    quarter.setName(Utils.objectToString(quarterMap.get("name")));
                    quarter.setKmlId(Utils.objectToLong(quarterMap.get("kmlId")));

                    //detail
                    LinkedHashMap detailMap = (LinkedHashMap) quarterMap.get("detail");
                    if (detailMap != null) {
                        quarter.setLocationId(Utils.objectToLong(detailMap.get("location_id")));
                        quarter.setLat(Utils.objectToDouble(detailMap.get("lat")));
                        quarter.setLon(Utils.objectToDouble(detailMap.get("lon")));
                        quarter.setZoom(Utils.objectToLong(detailMap.get("zoom")));
                    }
                    quarterList.add(quarter);

                });
                district.setQuarterList(quarterList);
                districtList.add(district);
            });

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
        return districtList;
    }

    private LinkedHashMap<String, Integer> crawlSubCategories(String parentCategoryId) {
        try {
            Document doc = Utils.getJsoupDocument(getUrl(URL_TYPE.SUB_CATEGORY, parentCategoryId));
            return JsonPath.parse(doc.body().text()).read("$.data.mapping");
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }


    private enum URL_TYPE {
        CITY, TOWN, DISTRICT, SUB_CATEGORY
    }

    private static String getUrl(URL_TYPE urlType, String id) {
        String url = "";
        long current = System.currentTimeMillis();
        String time = String.valueOf(current) + "_0." + StringUtils.substring(RandomUtil.generateActivationKey(), 0, 15);
        String time2 = String.valueOf(current - Integer.valueOf(RandomUtil.generateKey(6)));

        if (urlType == URL_TYPE.CITY) {
            url = String.format("http://www.sahibinden.com/ajax/location/loadCitiesByCountryId?vcIncluded=true&lang=tr&time=%s&address_country=%s&_=%s", time, id, time2);
        } else if (urlType == URL_TYPE.TOWN) {
            url = String.format("http://www.sahibinden.com/ajax/location/loadTownsByCityIds?lang=tr&time=%s&address_city=%s&_=%s", time, id, time2);
        } else if (urlType == URL_TYPE.DISTRICT) {
            url = String.format("http://www.sahibinden.com/ajax/location/loadDistrictsByTownIds?lang=tr&time=%s&address_town=%s&_=%s", time, id, time2);
        } else if (urlType == URL_TYPE.SUB_CATEGORY) {
            url = String.format("http://www.sahibinden.com/ajax/category/subCategories?lang=tr&time=%s&parentCategoryId=%s&isCustomSelector=true&_=%s", time, id, time2);
        }

        return url;
    }


}
