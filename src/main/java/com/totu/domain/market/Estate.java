package com.totu.domain.market;


import java.util.Map;

public class Estate extends AbstractItem {

    private String publishDateStr;
    private String title;
    private Long price;
    private String currency;
    private String rooms;
    private String m2;
    private String m2Price;
    private String type; // Satılık Daire
    private String age;
    private String floor; // bulundugu kat
    private String totalFloors;
    private String bathrooms;
    private Boolean withFurniture;
    private Boolean inSite;
    private String siteMonthlyFee; //aidat
    private Boolean creditable;
    private String seller;
    private String heating;


    // location
    private String city;
    private String district; // ilçe
    private String neighborhood;

    //
    private String ada;
    private String parsel;
    private String pafta;
    private String kaks;
    private String gabari;
    private String tapu;
    private String imar;
    private String takas;

    private String numberOfHouseInAFloor;


    private String donem;
    private String sure;


    private Map properties;


    @Override
    public String toString() {
        return String.format(
            "AbstractItem[%s, resourceSite= %s, externalId=%s, price=%d, rooms='%s']",
            super.toString(), price, rooms);
    }


    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getM2Price() {
        return m2Price;
    }

    public void setM2Price(String m2Price) {
        this.m2Price = m2Price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(String totalFloors) {
        this.totalFloors = totalFloors;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Boolean getWithFurniture() {
        return withFurniture;
    }

    public void setWithFurniture(Boolean withFurniture) {
        this.withFurniture = withFurniture;
    }

    public Boolean getInSite() {
        return inSite;
    }

    public void setInSite(Boolean inSite) {
        this.inSite = inSite;
    }

    public String getSiteMonthlyFee() {
        return siteMonthlyFee;
    }

    public void setSiteMonthlyFee(String siteMonthlyFee) {
        this.siteMonthlyFee = siteMonthlyFee;
    }

    public Boolean getCreditable() {
        return creditable;
    }

    public void setCreditable(Boolean creditable) {
        this.creditable = creditable;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAda() {
        return ada;
    }

    public void setAda(String ada) {
        this.ada = ada;
    }

    public String getParsel() {
        return parsel;
    }

    public void setParsel(String parsel) {
        this.parsel = parsel;
    }

    public String getPafta() {
        return pafta;
    }

    public void setPafta(String pafta) {
        this.pafta = pafta;
    }

    public String getKaks() {
        return kaks;
    }

    public void setKaks(String kaks) {
        this.kaks = kaks;
    }

    public String getGabari() {
        return gabari;
    }

    public void setGabari(String gabari) {
        this.gabari = gabari;
    }

    public String getTapu() {
        return tapu;
    }

    public void setTapu(String tapu) {
        this.tapu = tapu;
    }

    public String getImar() {
        return imar;
    }

    public void setImar(String imar) {
        this.imar = imar;
    }

    public String getTakas() {
        return takas;
    }

    public void setTakas(String takas) {
        this.takas = takas;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    public String getNumberOfHouseInAFloor() {
        return numberOfHouseInAFloor;
    }

    public void setNumberOfHouseInAFloor(String numberOfHouseInAFloor) {
        this.numberOfHouseInAFloor = numberOfHouseInAFloor;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getDonem() {
        return donem;
    }

    public void setDonem(String donem) {
        this.donem = donem;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }
}
