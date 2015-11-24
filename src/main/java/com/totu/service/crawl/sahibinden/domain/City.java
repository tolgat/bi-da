package com.totu.service.crawl.sahibinden.domain;


public class City extends LocationDetail {

    //{"id":34,"name":"İstanbul (Tümü)","tag":"istanbul",
    // "country":{"id":1,"name":"Türkiye","abbreviation":"TR","language":"tr","displayOrder":1,"sortOrder":1,"phoneCode":"+90","status":"ACTIVE","detail":null,"active":true},
    // "displayOrder":1,"sortOrder":1,"status":"ACTIVE","detail":{"location_id":534,"lat":41.00527,"lon":28.97696,"zoom":4,"cityId":34},"active":true}
    Long id;
    String name;
    String tag;
    Integer displayOrder;
    Integer sortOrder;

    Country country;

    @Override
    public String toString() {
        return String.format("id: %d, name: %s, tag: %s, displayOrder:%d, sortOrder:%d, country:{%s}, ",
            id, name, tag, displayOrder, sortOrder, country) + super.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
