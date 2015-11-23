package com.totu.domain.market;


import java.util.Map;

public class Estate extends AbstractItem {

    private String publishDateStr;
    private String title;
    private Long price;
    private String currency;
    private String rooms;
    private String m2;
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

    // location
    private String city;
    private String district; // ilçe
    private String neighborhood;

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

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}
