package com.example.springboot;

public class ResponseClass {
    int startYear;
    int toYear;
    String county;
    double meanChurchFee;

    public ResponseClass(int startYear, int toYear, String county, double meanChurchFee) {
        this.startYear = startYear;
        this.toYear = toYear;
        this.county = county;
        this.meanChurchFee = meanChurchFee;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getToYear() {
        return toYear;
    }

    public String getCounty() {
        return county;
    }

    public double getMeanChurchFee() {
        return meanChurchFee;
    }
}
