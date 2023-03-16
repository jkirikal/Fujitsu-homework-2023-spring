package com.example.homework_v1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.homework_v1.model.Weather;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long>{
    //returns the latest weather data from our database for Tallin, Tartu and Pärnu
        @Query(value = "select * from weather_data where timestamp = (select max(timestamp) from weather_data)", nativeQuery = true)
    List<Weather> getCityWeatherConditions();
}
