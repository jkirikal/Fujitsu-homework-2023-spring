package com.example.homework_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.stream.Collectors;
import com.example.homework_v1.model.Weather;
import com.example.homework_v1.repository.WeatherRepository;


@Component
public class GetWeatherData {
    @Autowired
    WeatherRepository wRepository;

    //Takes the xml data from given webpage, creates Station objects from the data and saves them in our H2 database
    public void getRequest() throws Exception {
        //takes the webpage data and returns it as a string
        String msg = readDataFromUrl();
        String[] rows = msg.split("\t");

        //calculates the time of observation made by Ilmateenistus
        java.sql.Timestamp sqlDate = getObservationTime();

        //parses the string data into fields, that are important to us and saves the data to our in-memory H2 database.
        stationObjectsFromXmlString(sqlDate, rows);

    }

    private String readDataFromUrl() throws Exception{
        URL url = new URL("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        return in.lines().collect(Collectors.joining());
    }

    //sets the timestamp of the latest observation to the correct format, according to the computer's local time.
    private java.sql.Timestamp getObservationTime(){
        Date timestamp = new Date();
        timestamp.setMinutes(0);
        timestamp.setSeconds(0);
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(timestamp.getTime());
        sqlDate.setNanos(0);
        return sqlDate;
    }


    //parses the xml string into a Weather object.
    //the code is a little unnecessary, since there are xml parsers available, but I had a lot of version problems
    //when trying to add and import the right dependencies. Some of the xml parsers are also deprecated in this Java/Spring version.
    private void stationObjectsFromXmlString(java.sql.Timestamp sqlDate, String[] rows){
        Weather station = new Weather();
        int count = -1;
        for (String row : rows) {
            if (!row.equals("")) {
                if(row.contains("timestamp")){
                    int indx = row.indexOf("timestamp=");
                    row = row.replaceFirst(">","");
                    int indxx = row.indexOf(">");
                    continue;
                }
                //only takes the data for some specific cities.
                if ((row.contains("Tallinn") || row.contains("Tartu") || row.contains("Pärnu"))&&!row.contains("Pärnu-Sauga")&&!row.contains("Tartu-Kvissental")) {
                    count = 0;
                }
                if(count>=0){
                    row = row.replaceFirst("<","");
                    int indx1 = row.indexOf(">");
                    int indx2 = row.indexOf("<");
                    row = row.substring(indx1+1, indx2);
                    if (count==0){
                        station = new Weather();
                        station.setName(row);
                        station.setTimestamp(sqlDate);
                    }
                    else if (count == 1){
                        station.setWmo_code(Integer.parseInt(row));
                    }
                    else if (count == 4){
                        station.setPhenomenon(row);
                    }
                    else if (count == 9){
                        station.setAir_temp(Double.parseDouble(row));
                    }
                    else if (count == 11) {
                        station.setWind_speed(Double.parseDouble(row));
                        //saves the Weather object to our database
                        wRepository.save(station);
                        count = -1;
                    }
                    if(count!=-1) count++;
                }


            }
        }
    }
}
