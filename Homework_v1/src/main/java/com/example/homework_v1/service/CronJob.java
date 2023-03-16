package com.example.homework_v1.service;

import com.example.homework_v1.controller.GetWeatherData;
import com.example.homework_v1.controller.RequestInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class CronJob {
    @Autowired
    GetWeatherData getWeather;

    //Gets weather data from Ilmateenistus at the startup of the program
    @PostConstruct
    public void onStartup() throws Exception{
        getWeather.getRequest();
    }

    //Gets weather data according to our setup, by default 15 minutes past full hour.
    //@Scheduled(cron = "0 15 * * * ?")
    @Scheduled(cron = "${cron.expression}")
    public void scheduleFixedDelayTask() throws Exception{
        getWeather.getRequest();
    }
}
