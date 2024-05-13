package com.example.pogoda.pogoda;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public class DailyWeather {
    @JsonProperty("time")
    private List<String> time;

    @JsonProperty("weather_code")
    private List<Integer> weatherCode;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperatureMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperatureMin;

    @JsonProperty("sunshine_duration")
    private List<Double> sunshineDuration;

}
