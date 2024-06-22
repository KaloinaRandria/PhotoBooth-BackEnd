package org.photobooth.restapi.service;

import org.photobooth.restapi.model.ValueRange;

import java.util.List;

public class ValueRangeService extends Service {
    public ValueRangeService() {
        super();
    }

    public List<ValueRange> getAll() throws Exception {
        return this.getAll(ValueRange.class);
    }
}
