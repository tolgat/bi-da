package com.totu.service.crawl.sahibinden;

import com.jayway.jsonpath.JsonPath;
import com.totu.service.crawl.AbstractCrawler;
import com.totu.service.crawl.sahibinden.domain.*;
import com.totu.service.util.Utils;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class SahibindenCrawler extends AbstractCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(SahibindenCrawler.class);

    public void crawl() {

        List<City> cityList = crawlCities();
        cityList.forEach(city -> {
            List<Town> townList = crawlTowns(city.getId().toString());
            townList.forEach(town -> {
                crawlDistricts(town.getId().toString());
            });

        });

    }

    private List<City> crawlCities() {
        final String CITIES_URL = "http://www.sahibinden.com/ajax/location/loadCitiesByCountryId?vcIncluded=true&lang=tr&time=1448019250372_0.45807269122451544&address_country=1&_=1448019224207";
        List<City> cityList = new ArrayList<>();

        try {
            Document doc = getDocument(CITIES_URL);
            JSONArray cities = JsonPath.parse(doc.body().text()).read("$.data.1");

            cities.forEach((item) -> {
                LinkedHashMap<String, Object> cityMap = (LinkedHashMap<String, Object>) item;
                City city = new City();
                city.setId(Utils.objectToInteger(cityMap.get("id")));
                city.setName(Utils.objectToString(cityMap.get("name")));
                city.setTag(Utils.objectToString(cityMap.get("tag")));
                city.setDisplayOrder(Utils.objectToInteger(cityMap.get("displayOrder")));
                city.setSortOrder(Utils.objectToInteger(cityMap.get("sortOrder")));

                //detail
                LinkedHashMap detailMap = (LinkedHashMap) cityMap.get("detail");
                if(detailMap != null) {
                    city.setLocationId(Utils.objectToInteger(detailMap.get("location_id")));
                    city.setLat(Utils.objectToDouble(detailMap.get("lat")));
                    city.setLon(Utils.objectToDouble(detailMap.get("lon")));
                    city.setZoom(Utils.objectToInteger(detailMap.get("zoom")));
                }

                // country
                LinkedHashMap countryMap = (LinkedHashMap) cityMap.get("country");
                if(countryMap != null) {
                    Country country = new Country();
                    country.setId(Utils.objectToInteger(countryMap.get("id")));
                    country.setName(Utils.objectToString(countryMap.get("name")));
                    country.setAbbreviation(Utils.objectToString(countryMap.get("abbreviation")));
                    country.setLanguage(Utils.objectToString(countryMap.get("language")));
                    country.setDisplayOrder(Utils.objectToInteger(countryMap.get("displayOrder")));
                    country.setSortOrder(Utils.objectToInteger(countryMap.get("sortOrder")));
                    city.setCountry(country);
                }

                cityList.add(city);
            });

            cityList.forEach(city -> LOG.debug(String.format("city: %s", city)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityList;

    }

    private List<Town> crawlTowns(String cityId) {
        {
            final String TOWNS_URL = "http://www.sahibinden.com/ajax/location/loadTownsByCityIds?lang=tr&time=1448275348141_0.7550221642013639&address_city=MY_ADDRESS_ID&_=1448275310831";
            List<Town> townList = new ArrayList<>();

            try {
                Document doc = getDocument(StringUtils.replace(TOWNS_URL, "MY_ADDRESS_ID", cityId));
                JSONArray districts = JsonPath.parse(doc.body().text()).read("$.data." + cityId);

                districts.forEach((item) -> {
                    LinkedHashMap<String, Object> districtMap = (LinkedHashMap<String, Object>) item;


                    Town town = new Town();
                    town.setId(Utils.objectToInteger(districtMap.get("id")));
                    town.setName(Utils.objectToString(districtMap.get("name")));

                    //detail
                    LinkedHashMap detailMap = (LinkedHashMap) districtMap.get("detail");
                    if(detailMap != null) {
                        town.setLocationId(Utils.objectToInteger(detailMap.get("location_id")));
                        town.setLat(Utils.objectToDouble(detailMap.get("lat")));
                        town.setLon(Utils.objectToDouble(detailMap.get("lon")));
                        town.setZoom(Utils.objectToInteger(detailMap.get("zoom")));
                    }
                    townList.add(town);
                });

                townList.forEach(district -> LOG.debug(String.format("district: %s", district.toString())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return townList;
        }
    }

    private void crawlDistricts(String townId) {
        {
            final String NEIGHBOURHOOD_URL = "http://www.sahibinden.com/ajax/location/loadDistrictsByTownIds?lang=tr&time=1448276763477_0.4826449004467577&address_town=MY_ADDRESS_ID&_=1448275310835";

            try {
                Document doc = getDocument(StringUtils.replace(NEIGHBOURHOOD_URL, "MY_ADDRESS_ID", townId));
                JSONArray districts = JsonPath.parse(doc.body().text()).read("$.data." + townId);

                districts.forEach((districtItem) -> {

                    LinkedHashMap<String, Object> districtMap = (LinkedHashMap<String, Object>) districtItem;


                    District district = new District();
                    district.setId(Utils.objectToInteger(districtMap.get("id")));
                    district.setName(Utils.objectToString(districtMap.get("name")));

                    // quarter (asıl mahalle)
                    JSONArray quarters = (JSONArray) districtMap.get("quarters");
                    quarters.forEach((quarterItem) -> {
                        LinkedHashMap<String, Object> quarterMap = (LinkedHashMap<String, Object>) quarterItem;

                        Quarter quarter = new Quarter();
                        quarter.setId(Utils.objectToInteger(quarterMap.get("id")));
                        quarter.setName(Utils.objectToString(quarterMap.get("name")));
                        quarter.setKmlId(Utils.objectToInteger(quarterMap.get("kmlId")));

                        //detail
                        LinkedHashMap detailMap = (LinkedHashMap) quarterMap.get("detail");
                        if(detailMap != null) {
                            quarter.setLocationId(Utils.objectToInteger(detailMap.get("location_id")));
                            quarter.setLat(Utils.objectToDouble(detailMap.get("lat")));
                            quarter.setLon(Utils.objectToDouble(detailMap.get("lon")));
                            quarter.setZoom(Utils.objectToInteger(detailMap.get("zoom")));
                        }
                        LOG.debug(String.format("quarter: %s", quarter.toString()));
                    });




                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
            .timeout(30 * 1000)
            .referrer("http://www.google.com")
            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
            .cookie("auth", "token")
            .ignoreContentType(true)
            .get();
    }


}
