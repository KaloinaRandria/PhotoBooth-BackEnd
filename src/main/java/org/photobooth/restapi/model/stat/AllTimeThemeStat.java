package org.photobooth.restapi.model.stat;

import java.util.List;

public class AllTimeThemeStat {
    private List<String> labels;
    private List<Object> data;
    private List<String> colors;
    private Object gain;
    private Object usedCount;

    public AllTimeThemeStat() {}

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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

    public Object getGain() {
        return gain;
    }

    public void setGain(Object gain) {
        this.gain = gain;
    }

    public Object getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Object usedCount) {
        this.usedCount = usedCount;
    }
}
