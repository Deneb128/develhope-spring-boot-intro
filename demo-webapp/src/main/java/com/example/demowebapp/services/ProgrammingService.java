package com.example.demowebapp.services;

import com.example.demowebapp.entities.ProgrammingLanguage;
import com.example.demowebapp.repositories.ProgrammingLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgrammingService {
    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    public ProgrammingLanguage addNewProgrammingLanguage(ProgrammingLanguage programmingLanguage){
        return programmingLanguageRepository.saveAndFlush(programmingLanguage);
    }

    public Page<ProgrammingLanguage> getProgrammingLanguageList(Optional<Integer> page, Optional<Integer> size){
        if(page.isPresent() && size.isPresent()){
            Pageable pageable = PageRequest.of(page.get(), size.get());
            Page<ProgrammingLanguage> programmingLanguages = programmingLanguageRepository.findAll(pageable);
            return programmingLanguages;
        } else {
            Page<ProgrammingLanguage> pageProgrammingLanguage = Page.empty();
            return pageProgrammingLanguage;
        }
    }

    public ProgrammingLanguage updateInventorById(Long id, String inventor){
        if (programmingLanguageRepository.existsById(id)){
            ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id).get();
            programmingLanguage.setInventor(inventor);
            return programmingLanguageRepository.saveAndFlush(programmingLanguage);
        } else {
            return new ProgrammingLanguage();
        }
    }

    public void deleteAllProgrammingLanguages(){
        programmingLanguageRepository.deleteAll();
    }
}
