package com.example.demowebapp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2")
public class MyControllerV2 {

    @GetMapping(path = "/ciao/{provincia}")
    public User ciao(@PathVariable String provincia, @RequestParam(required = true)String nome){
        return new User(nome, provincia);
    }
}
