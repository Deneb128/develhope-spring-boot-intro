package com.example.demowebapp;

import org.springframework.web.bind.annotation.*;

@RestController
public class NameController {

    @GetMapping(path = "/{name}")
    public String getName(@PathVariable String name){
        return name;
    }

    @PostMapping(path = "/{name}")
    public String revertName(@PathVariable String name){
        return new StringBuilder(name).reverse().toString();
    }
}
