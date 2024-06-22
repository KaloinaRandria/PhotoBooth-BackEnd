package org.photobooth.restapi.http.data;

import java.util.List;

public class ServiceData {
    private String icon;
    private String label;

    private List<ValueRangeData> valueRange;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ValueRangeData> getValueRange() {
        return valueRange;
    }

    public void setValueRange(List<ValueRangeData> valueRange) {
        this.valueRange = valueRange;
    }
}
