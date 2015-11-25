package com.totu.domain.market;

import com.mongodb.BasicDBList;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;


public abstract class AbstractItem {
    // mongodb'de internal kullanilan ID
    @Id
    private String id;
    private Site resourceSite;
    private String url;
    private String remoteId;
    private String publishDateAsStr;
    private ZonedDateTime createDate;
    private ZonedDateTime lastSeenDate;
    private String processLog;
    private BasicDBList properties;
    private String title;
    private Long price;
    private String currency;
    // location
    private String il;
    private String ilce;
    private String mahalle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Site getResourceSite() {
        return resourceSite;
    }

    public void setResourceSite(Site resourceSite) {
        this.resourceSite = resourceSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getPublishDateAsStr() {
        return publishDateAsStr;
    }

    public void setPublishDateAsStr(String publishDateAsStr) {
        this.publishDateAsStr = publishDateAsStr;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLastSeenDate() {
        return lastSeenDate;
    }

    public void setLastSeenDate(ZonedDateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public String getProcessLog() {
        return processLog;
    }

    public void setProcessLog(String processLog) {
        this.processLog = processLog;
    }

    public BasicDBList getProperties() {
        return properties;
    }

    public void setProperties(BasicDBList properties) {
        this.properties = properties;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIl() {
        return il;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public String getIlce() {
        return ilce;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    public String getMahalle() {
        return mahalle;
    }

    public void setMahalle(String mahalle) {
        this.mahalle = mahalle;
    }
}
