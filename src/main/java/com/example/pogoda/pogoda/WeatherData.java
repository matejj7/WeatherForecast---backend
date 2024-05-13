package com.example.pogoda.pogoda;
import lombok.Data;
@Data
public class WeatherData {
    private String date;
    private Integer weatherCode;
    private Double tempMin;
    private Double tempMax;
    private Double estimatedEnergy;
}
