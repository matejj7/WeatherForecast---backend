package com.example.pogoda.pogoda;
import com.example.pogoda.pogoda.DailyWeather;
import com.example.pogoda.pogoda.WeatherApiResponse;
import com.example.pogoda.pogoda.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public List<WeatherData> fetchWeather(double latitude, double longitude) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=weather_code,temperature_2m_max,temperature_2m_min,sunshine_duration";
        RestTemplate restTemplate = new RestTemplate();
        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
        logger.info("Response from Open-Meteo: {}", response);
        return mapResponseToWeatherData(response);
    }

    private List<WeatherData> mapResponseToWeatherData(WeatherApiResponse response) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        if (response != null && response.getDaily() != null) {
            DailyWeather daily = response.getDaily();
            List<String> times = daily.getTime();
            List<Integer> weatherCodes = daily.getWeatherCode();
            List<Double> maxTemps = daily.getTemperatureMax();
            List<Double> minTemps = daily.getTemperatureMin();
            List<Double> sunshineDurations = daily.getSunshineDuration();

            for (int i = 0; i < times.size(); i++) {
                WeatherData weatherData = new WeatherData();
                weatherData.setDate(times.get(i));
                weatherData.setWeatherCode((weatherCodes != null && weatherCodes.size() > i) ? weatherCodes.get(i) : null);
                weatherData.setTempMax((maxTemps != null && maxTemps.size() > i) ? maxTemps.get(i) : null);
                weatherData.setTempMin((minTemps != null && minTemps.size() > i) ? minTemps.get(i) : null);
                Double sunshineDuration = (sunshineDurations != null && sunshineDurations.size() > i) ? sunshineDurations.get(i) : null;
                weatherData.setEstimatedEnergy(calculateEstimatedEnergy(sunshineDuration));
                weatherDataList.add(weatherData);
                logger.debug("Mapped weatherData: {}", weatherData);
            }
        } else {
            logger.error("Response or daily weather data is null");
        }
        return weatherDataList;
    }

    private Double calculateEstimatedEnergy(Double sunshineDuration) {
        if (sunshineDuration == null) return null;
        double panelEfficiency = 0.2;
        double panelPowerKw = 2.5;
        return (sunshineDuration / 3600) * panelPowerKw * panelEfficiency;
    }
}
