package com.totu.domain.market;

import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;


public abstract class AbstractItem {
    // mongodb'de internal kullanilan ID
    @Id
    private String id;
    private Site resourceSite;
    private String name;
    private String desc;
    private String url;
    private String remoteId;
    private ZonedDateTime createdDate;
    private String processLog;

    @Override
    public String toString() {
        return String.format(
            "AbstractItem[id=%s, resourceSite=%s, name=%s, desc='%s', url='%s', remoteId='%s', indexDate='%s', processLog='%s']",
            id, resourceSite, name, desc, url, remoteId, createdDate, processLog);
    }

    public Site getResourceSite() {
        return resourceSite;
    }

    public void setResourceSite(Site resourceSite) {
        this.resourceSite = resourceSite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getProcessLog() {
        return processLog;
    }

    public void setProcessLog(String processLog) {
        this.processLog = processLog;
    }
}
