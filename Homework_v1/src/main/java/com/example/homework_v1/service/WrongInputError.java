package com.example.homework_v1.service;

public class WrongInputError extends RuntimeException{
    public WrongInputError(){
        super("Wrong input parameters");
    }
}
