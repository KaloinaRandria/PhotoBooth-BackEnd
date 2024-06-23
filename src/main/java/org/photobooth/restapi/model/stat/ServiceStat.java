package org.photobooth.restapi.model.stat;

import java.util.List;

public class ServiceStat {
    List<String> categories;
    List<Object> data;
    List<String> colors;

    public ServiceStat() {}

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
