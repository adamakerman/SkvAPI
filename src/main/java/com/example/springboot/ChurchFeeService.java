package com.example.springboot;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChurchFeeService {

    public double getMeanChurchFeeOfAllPaymentsInCountyForYears(int startYear, int endYear, String county){
        var paymentList = getPaymentData(startYear, endYear, county);

        return getMeanChurchFee(paymentList);
    }


    private ArrayList<SkvApiResponse.YearlyPayment> getPaymentData(int startYear, int endYear, String county){

        final String apiUri = "https://skatteverket.entryscape.net/rowstore/dataset/c67b320b-ffee-4876-b073-dd9236cd2a99";

        String url = UriComponentsBuilder.fromUriString(apiUri)
                .queryParam("kommun", county)
                /* TODO önskade att göra typ ".queryParam("år", URLencode(yearRangeToRegex(startyear, endyear)) */
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        SkvApiResponse result = restTemplate.getForObject(url, SkvApiResponse.class);
        /* TODO Skapa exception handlers */
        assert result != null;
        var payments = result.getPayments();

        /* TODO Filtrerar ut felaktiga årtal (pga dålig request query) */
        payments.removeIf(p -> isIllegalYear(p.getYear(), startYear, endYear));

        return payments;
    }

    private double getMeanChurchFee(List<SkvApiResponse.YearlyPayment> paymentList){
        double total = 0;
        int nPayments = 0;
        for (SkvApiResponse.YearlyPayment payment : paymentList){
            total += payment.getChurchFee();
            nPayments++;
        }

        return total / nPayments;
    }

    private boolean isIllegalYear(int inputYear, int startYear, int endYear){

        return inputYear < startYear | inputYear > endYear;
    }

    /* Får tyvärr inte regex att funka i requesten... Även om den är korrekt URL-encodad :( */
    private static String getYearRangeRegex(int fromYear, int toYear) {
        StringBuilder regex = new StringBuilder("\\b(");

        for (int i = fromYear; i <= toYear; i++) {
            if (i != fromYear) {
                regex.append("|");
            }
            regex.append(i);
        }

        regex.append(")\\b");
        return regex.toString();
    }
}
