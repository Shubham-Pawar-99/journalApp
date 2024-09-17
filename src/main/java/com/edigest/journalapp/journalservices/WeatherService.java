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

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    // Get weather information using redis

    public WeatherResponse getWeather(String city) {
        // Attempt to fetch the cached weather data from Redis
        // WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);

        // if (weatherResponse != null) {
        //     return weatherResponse; // Return cached data if available
        // } else {
            try {
                // Construct the API endpoint using the cache
                String finalapi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                        .replace(PlaceHolder.CITY, city)
                        .replace(PlaceHolder.API_KEY, apikey);

                // Call the external weather API
                ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalapi, HttpMethod.GET, null,
                        WeatherResponse.class);
                WeatherResponse body = res.getBody();

                // if (body != null) {
                //     // Cache the API response in Redis with a TTL of 3000 seconds
                //     redisService.set("Weather_of_" + city, body, 3000L);
                // }

                return body; // Return the weather data from the API
            } catch (Exception e) {
                // Handle any exceptions (e.g., API errors, network issues, etc.)
                // Log the exception and handle it appropriately
                System.err.println("Error fetching weather data: " + e.getMessage());
                e.printStackTrace();
                return null; // Return null or an appropriate fallback response in case of failure
            }
        // }
    }

}
