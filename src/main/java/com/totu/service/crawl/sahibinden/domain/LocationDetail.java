package com.totu.service.crawl.sahibinden.domain;


public abstract class LocationDetail {

    Long locationId;
    Double lat;
    Double lon;
    Long zoom;

    @Override
    public String toString() {
        return String.format("locationId:%d, lat:%f, lon:%f, zoom:%d",
            locationId, lat, lon, zoom);
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
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

    public Long getZoom() {
        return zoom;
    }

    public void setZoom(Long zoom) {
        this.zoom = zoom;
    }


}
