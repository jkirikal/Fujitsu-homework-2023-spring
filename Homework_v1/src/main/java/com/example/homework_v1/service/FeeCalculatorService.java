package com.example.homework_v1.service;
import com.example.homework_v1.controller.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.homework_v1.model.Weather;
import com.example.homework_v1.repository.WeatherRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class FeeCalculatorService {
    @Autowired
    WeatherRepository wRepository;

    //Calculates the base fee.
    public double calculateFee(RequestInfo reqInfo) throws VehicleUsageError, WrongInputError{
        double fee = 0;
        String city = reqInfo.getCity().toLowerCase();
        String transportation = reqInfo.getTransportation();
        //If the input parameters are faulty in some way, throws an error.
        if(!(city.equals("tallinn")||city.equals("tartu")||city.equals("pärnu"))) throw new WrongInputError();

        List<Weather> latestWeather = wRepository.getCityWeatherConditions();

        //adds to the base fee from weather conditions
        fee += feeFromWeather(city, transportation, latestWeather);

        //adds to the base fee according to the city and method of transportation
        fee += feeFromCityVehicle(city, transportation);

        return fee;
    }

    private double feeFromCityVehicle(String city, String transportation) throws WrongInputError{
        double sum = 0;
        HashMap<String, double[]> RBF = new HashMap<>();
        RBF.put("tallinn", new double[]{4, 3.5, 3});
        RBF.put("tartu", new double[]{3.5, 3, 2.5});
        RBF.put("pärnu", new double[]{3, 2.5, 2});
        if (transportation.equalsIgnoreCase("car")) sum = RBF.get(city)[0];
        else if (transportation.equalsIgnoreCase("scooter")) sum = RBF.get(city)[1];
        else if (transportation.equalsIgnoreCase("bike")) sum = RBF.get(city)[2];
        else throw new WrongInputError();
        return sum;
    }

    private double feeFromWeather(String city, String transportation, List<Weather> latestWeather) throws VehicleUsageError{
        double sum = 0;
        HashMap<String, String> stationMap = new HashMap<>();
        stationMap.put("tallinn", "Tallinn-Harku");
        stationMap.put("tartu", "Tartu-Tõravere");
        stationMap.put("pärnu", "Pärnu");
        double atef = 0;
        double wsef = 0;
        double wpef = 0;
        for (Weather weather : latestWeather) {
            if(stationMap.get(city).equals(weather.getName())){
                if(transportation.equalsIgnoreCase("bike")||transportation.equalsIgnoreCase("scooter")){
                    double airTemp = weather.getAir_temp();
                    if(airTemp<-10) atef = 1;
                    else if(airTemp>=-10&&airTemp<=0) atef = 0.5;

                    String phenomenon = weather.getPhenomenon().toLowerCase();
                    if(phenomenon.contains("snow")||
                            phenomenon.contains("sleet")) wpef = 1;
                    else if(phenomenon.contains("rain")) wpef = 0.5;
                    else if(phenomenon.contains("glaze")||
                            phenomenon.contains("hail")||
                            phenomenon.contains("thunder")) throw new VehicleUsageError();
                }
                double windSpeed = weather.getWind_speed();
                if(transportation.equalsIgnoreCase("bike")){
                    if(windSpeed>=10&&windSpeed<=20) wsef = 0.5;
                    else if(windSpeed>20) throw new VehicleUsageError();
                }
            }

        }
        sum = atef + wsef + wpef;
        return sum;
    }
}
