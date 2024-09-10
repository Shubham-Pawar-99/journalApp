package com.edigest.journalapp.journalservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edigest.journalapp.api.response.WeatherResponse;
import com.edigest.journalapp.cache.AppCache;
import com.edigest.journalapp.constants.PlaceHolder;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;
    // private static final String API =
    // "https://api.weatherstack.com/current?access_key=API_KEY&query=city";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalapi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolder.CITY, city)
                .replace(PlaceHolder.API_KEY, apikey);
        ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalapi, HttpMethod.GET, null,
                WeatherResponse.class);
        WeatherResponse body = res.getBody();
        return body;
    }
    
}
