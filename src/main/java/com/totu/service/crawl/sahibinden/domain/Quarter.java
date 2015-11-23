package com.totu.service.crawl.sahibinden.domain;

public class Quarter extends LocationDetail{

    Integer id;
    String name;
    Integer kmlId;

    @Override
    public String toString() {
        return String.format("id: %d, name: %s, ", id, name) + super.toString();
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

    public Integer getKmlId() {
        return kmlId;
    }

    public void setKmlId(Integer kmlId) {
        this.kmlId = kmlId;
    }
}
