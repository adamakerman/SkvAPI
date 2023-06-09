package com.example.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChurchController {
    @Autowired
    ChurchFeeService churchFeeService;

    @GetMapping("/api/meanChurchFee")
    public String meanChurchFee(@RequestParam String county,
                @RequestParam int fromYear,
                @RequestParam int toYear) throws RequestException, JsonProcessingException {

        double meanFee = churchFeeService.getMeanChurchFeeOfAllPaymentsInCountyForYears(fromYear, toYear, county.toUpperCase());
        var response = new ResponseClass(fromYear, toYear, county, meanFee);

        var om = new ObjectMapper();
        return om.writeValueAsString(response);
    }
}
