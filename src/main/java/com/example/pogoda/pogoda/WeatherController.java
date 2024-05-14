package com.example.pogoda.pogoda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @CrossOrigin(origins = "https://weatherforecast-frontend.onrender.com")
    @GetMapping("")
    public ResponseEntity<List<WeatherData>> getWeather(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        try {
            List<WeatherData> weatherDataList = weatherService.fetchWeather(latitude, longitude);
            return ResponseEntity.ok(weatherDataList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
