package com.totu.service.crawl.sahibinden.domain;

public class District{

    Integer id;
    String name;

    @Override
    public String toString() {
        return String.format("id: %d, name: %s", id, name) + super.toString();
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



}
