package com.totu.service.crawl.sahibinden.domain;

public class Town extends LocationDetail{

    Long id;
    String name;

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



}
