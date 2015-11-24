package com.totu.service.crawl.sahibinden.domain;

import java.util.List;

public class District{

    Long id;
    String name;
    List<Quarter> quarterList;

    @Override
    public String toString() {
        return String.format("id: %d, name: %s", id, name) + super.toString();
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

    public List<Quarter> getQuarterList() {
        return quarterList;
    }

    public void setQuarterList(List<Quarter> quarterList) {
        this.quarterList = quarterList;
    }
}
