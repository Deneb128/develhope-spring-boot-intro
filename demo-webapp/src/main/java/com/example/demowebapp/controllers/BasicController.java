package com.example.demowebapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/")
public class BasicController {

    @GetMapping
    public String hello(){
        return "Hello";
    }
}
