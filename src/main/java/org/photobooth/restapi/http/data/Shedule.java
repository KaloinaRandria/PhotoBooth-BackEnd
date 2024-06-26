package org.photobooth.restapi.http.data;

import org.entityframework.tools.Table;

import java.sql.Timestamp;

public class Shedule {
    private String title;
    private Timestamp start;
    private Timestamp end;
    private String classNames;
    private String backgroundColor;
    private String color;
    private String description;

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

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
