package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RestController
public class TestController {

    @Autowired
    ChurchFeeService churchFeeService;
    String county = "VÄRMDÖ";
    int fromYear = 2017;
    int toYear = 2023;


    /* TODO Kan skapa argument för år/kommun */
    @GetMapping("/")
    public String index() {

        double meanFee = churchFeeService.getMeanChurchFeeOfAllPaymentsInCountyForYears(fromYear, toYear, county);

        String output = "Average church fee in <" +
                county +
                "> was <" +
                meanFee +
                "> (some unit) Between <" +
                fromYear +
                "> and <" +
                toYear +
                ">";

        return output;
    }






}
