package org.photobooth.restapi.http.data;

import org.entityframework.tools.Table;

import java.sql.Timestamp;

public class Shedule {
    private String title;
    private Timestamp start;
    private Timestamp end;
    private String classNames;

    public Shedule() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassName(String className) {
        this.classNames = className;
    }
}
