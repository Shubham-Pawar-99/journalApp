package com.edigest.journalapp.journalservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.edigest.journalapp.api.response.WeatherResponse;

@Component
public class WeatherService {

    private static final String apikey = "c350d1c54d7a0a5bef4f45fe69d6d3b6";

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=city";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {

        String finalapi = API.replace("city", city).replace("API_KEY", apikey);
        ResponseEntity<WeatherResponse> res=restTemplate.exchange(finalapi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = res.getBody();
        return body;
    }

}
