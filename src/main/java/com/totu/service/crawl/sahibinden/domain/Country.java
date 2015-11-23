package com.totu.service.crawl.sahibinden.domain;


public class Country {

    //"country":{"id":1,"name":"Türkiye","abbreviation":"TR","language":"tr","displayOrder":1,"sortOrder":1,"phoneCode":"+90","status":"ACTIVE","detail":null,"active":true},
    Integer id;
    String name;
    String abbreviation;
    String language;
    Integer displayOrder;
    Integer sortOrder;


    @Override
    public String toString() {
        return String.format("id: %d, name: %s, abbreviation: %s, language:%s, displayOrder:%d, sortOrder:%d",
            id, name, abbreviation, language, displayOrder, sortOrder);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}
