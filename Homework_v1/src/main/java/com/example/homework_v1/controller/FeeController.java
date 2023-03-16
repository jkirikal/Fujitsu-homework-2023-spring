package com.example.homework_v1.controller;
import com.example.homework_v1.service.FeeCalculatorService;
import com.example.homework_v1.service.VehicleUsageError;
import com.example.homework_v1.service.WrongInputError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class FeeController {
    @Autowired
    FeeCalculatorService calcService;

    //takes get requests
    @RequestMapping(value="/", method=RequestMethod.GET)
    public double getFee(@RequestParam HashMap<String,String> allParams) {
        try{
            //creates an object from the request parameters and passes the object to "calculateFee" method.
            return calcService.calculateFee(hashMapToRequestInfo(allParams));
        }

        //if calculateFee throws VehicleUsageError or WrongInputError
        catch (VehicleUsageError e){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden", e);
        }
        catch (WrongInputError e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong input city or vehicle type", e);
        }


    }

    //method to check if the request has the right amount of parameters and puts the parameters to a new RequestInfo object.
    private RequestInfo hashMapToRequestInfo(HashMap<String,String> allParams){
        String[] data = new String[2];
        if(allParams.size()==2){
            allParams.forEach((key,value)->{
                        if(key.toLowerCase().contains("city")) data[0] = value;
                        else if(key.toLowerCase().contains("type")) data[1] = value;
                        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong parameter key: "+key+"." +
                                " Accepted keys are 'City' and 'Vehicle Type'.");
                    }
            );
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong number of parameters");
        RequestInfo reqInfo = new RequestInfo(data[0], data[1]);
        return reqInfo;
    }
}
