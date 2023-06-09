package com.example.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SkvApiResponse{
    public int resultCount;
    public int offset;
    public int limit;
    public int queryTime;

    @JsonProperty("results")
    public ArrayList<YearlyPayment> payments;

    public ArrayList<YearlyPayment> getPayments() {
        return payments;
    }


    public static class YearlyPayment{
        @JsonProperty("kyrkoavgift")
        public double churchFee;
        @JsonProperty("år")
        public int year;
        public double getChurchFee() {
            return churchFee;
        }
        public int getYear() {
            return year;
        }
    }
}
