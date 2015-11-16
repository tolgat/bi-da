package com.totu.domain.market;


import java.util.Map;

public class Estate extends AbstractItem {

    private Site resourceSite;
    private String externalId;
    private String publishDateStr;
    private String title;
    private Long price;
    private String rooms;
    private Integer m2;
    private String type; // Satılık Daire
    private String age;
    private Integer floor; // bulundugu kat
    private Integer totalFloors;
    private Integer bathrooms;
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
            super.toString(), resourceSite.name(), externalId, price, rooms);
    }

    public Site getResourceSite() {
        return resourceSite;
    }

    public void setResourceSite(Site resourceSite) {
        this.resourceSite = resourceSite;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public Integer getM2() {
        return m2;
    }

    public void setM2(Integer m2) {
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
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
