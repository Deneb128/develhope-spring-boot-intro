package com.example.demowebapp.controllers;

import com.example.demowebapp.entities.ProgrammingLanguage;
import com.example.demowebapp.services.ProgrammingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("")
public class ProgrammingLanguageController {
    @Autowired
    private ProgrammingService programmingService;

    @PostMapping(path = "/addproglang")
    public ProgrammingLanguage addNewProgrammingLanguage(@RequestBody ProgrammingLanguage programmingLanguage){
        return programmingService.addNewProgrammingLanguage(programmingLanguage);
    }

    @GetMapping(path = "/getproglang")
    public Page<ProgrammingLanguage> getProgrammingLanguageList(@RequestParam(required = false)Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size){
        return programmingService.getProgrammingLanguageList(page, size);
    }

    @PutMapping(path = "/changeinventor/{id}")
    public ProgrammingLanguage updateInventorById(@PathVariable Long id, @RequestParam(required = true) String inventor){
        return programmingService.updateInventorById(id, inventor);
    }
}
