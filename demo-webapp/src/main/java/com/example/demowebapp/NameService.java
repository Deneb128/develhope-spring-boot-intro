package com.example.demowebapp;

import org.springframework.stereotype.Service;

@Service
public class NameService {

    public String getName(String name){
        return name;
    }

    public String revertName(String name){
        return new StringBuilder(name).reverse().toString();
    }
}
