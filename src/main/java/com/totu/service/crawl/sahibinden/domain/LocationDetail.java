package com.totu.service.crawl.sahibinden.domain;


public abstract class LocationDetail {

    Integer locationId;
    Double lat;
    Double lon;
    Integer zoom;

    @Override
    public String toString() {
        return String.format("locationId:%d, lat:%f, lon:%f, zoom:%d",
            locationId, lat, lon, zoom);
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }


}
