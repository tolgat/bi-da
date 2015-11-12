package com.totu.domain.market;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;


public abstract class AbstractItem {
    // mongodb'de internal kullanilan ID
    @Id
    private String id;

    private String name;
    private String desc;
    private String siteName;
    private String url;
    private String remoteId;
    private ZonedDateTime createdDate;

    @Override
    public String toString() {
        return String.format(
            "AbstractItem[id=%s, name=%s, desc='%s', siteName='%s', url='%s', remoteId='%s', indexDate='%s']",
            id, name, desc, siteName, url, remoteId, createdDate);
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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
}
