package com.example.springboot;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChurchFeeService {

    public double getMeanChurchFeeOfAllPaymentsInCountyForYears(int startYear, int toYear, String county) throws RequestException {
        var paymentList = getPaymentData(startYear, toYear, county);
        return getMeanChurchFee(paymentList);
    }

    private ArrayList<SkvApiResponse.YearlyPayment> getPaymentData(int startYear, int toYear, String county) throws RequestException {
        final String apiUri = "https://skatteverket.entryscape.net/rowstore/dataset/c67b320b-ffee-4876-b073-dd9236cd2a99";
        String url = UriComponentsBuilder.fromUriString(apiUri)
                .queryParam("kommun", county.toUpperCase())
                .queryParam("år", getYearRangeRegex(startYear, toYear))
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        SkvApiResponse result = restTemplate.getForObject(url, SkvApiResponse.class);
        if (result == null){
            throw new RequestException("No response from API");
        }
        var payments = result.getPayments();

        /* TODO Bör hantera detta annorlunda (status 500 internal error känns ej rätt)
        *   men vill även säkerställa att den inte börjar beräkna medelvärde av 0 betalningar...*/
        if (payments.size() == 0){
            throw new RequestException("No data found in county for this time span");
        }
        return payments;
    }

    private double getMeanChurchFee(List<SkvApiResponse.YearlyPayment> paymentList){
        double total = 0;
        int nPayments = 0;
        for (SkvApiResponse.YearlyPayment payment : paymentList){
            total += payment.getChurchFee();
            nPayments++;
        }
        if (nPayments == 0){
            return 0;
        }
        return total / nPayments;
    }

    private static String getYearRangeRegex(int fromYear, int toYear) {
        StringBuilder regex = new StringBuilder("\b(");

        for (int i = fromYear; i <= toYear; i++) {
            if (i != fromYear) {
                regex.append("|");
            }
            regex.append(i);
        }

        regex.append(")\b");
        return regex.toString();
    }
}
