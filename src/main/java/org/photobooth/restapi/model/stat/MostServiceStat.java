package org.photobooth.restapi.model.stat;

import org.photobooth.restapi.model.ServComp;

import java.sql.Date;

public class MostServiceStat {
    private Date start;
    private Date end;
    private ServComp service;
    private int use_count;

    public MostServiceStat() {}

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ServComp getService() {
        return service;
    }

    public void setService(ServComp service) {
        this.service = service;
    }

    public int getUse_count() {
        return use_count;
    }

    public void setUse_count(int use_count) {
        this.use_count = use_count;
    }
}
