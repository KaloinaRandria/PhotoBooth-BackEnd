package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;

@Table(name = "value_ranges")
public class ValueRange {
    @Primary(auto = true)
    private int id;
    private String range_label;
    private int min_value;
    private int max_value;

    public ValueRange() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRange_label() {
        return range_label;
    }

    public void setRange_label(String range_label) {
        this.range_label = range_label;
    }

    public int getMin_value() {
        return min_value;
    }

    public void setMin_value(int min_value) {
        this.min_value = min_value;
    }

    public int getMax_value() {
        return max_value;
    }

    public void setMax_value(int max_value) {
        this.max_value = max_value;
    }
}
