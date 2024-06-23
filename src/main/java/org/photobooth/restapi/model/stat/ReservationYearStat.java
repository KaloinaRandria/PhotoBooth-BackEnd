package org.photobooth.restapi.model.stat;

import java.util.List;

public class ReservationYearStat {
    public List<Integer> data;
    public int total;
    public int year;

    public ReservationYearStat() {}

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
