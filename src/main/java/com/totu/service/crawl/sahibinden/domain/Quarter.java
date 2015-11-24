package com.totu.service.crawl.sahibinden.domain;

public class Quarter extends LocationDetail{

    Long id;
    String name;
    Long kmlId;

    @Override
    public String toString() {
        return String.format("id: %d, name: %s, ", id, name) + super.toString();
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

    public Long getKmlId() {
        return kmlId;
    }

    public void setKmlId(Long kmlId) {
        this.kmlId = kmlId;
    }

}
