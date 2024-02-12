package com.example.demowebapp.controllers;

import com.example.demowebapp.entities.Student;
import com.example.demowebapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(path = "/student/add")
    public Student addNewStudent(@RequestBody Student student){
        return studentService.addNewStudent(student);
    }

    @GetMapping(path = "/student/get/all")
    public List<Student> getStudentList(){
        return studentService.getStudentList();
    }

    @GetMapping(path = "/student/get/{id}")
    public Student getStudentByID(@PathVariable Long id){
        return studentService.getStudentByID(id);
    }

    @PutMapping(path = "/student/update/{id}/workingstatus")
    public @ResponseBody Student updateIsWorkingByID(@PathVariable Long id, @RequestParam(required = true) Boolean isWorking){
        return studentService.updateIsWorkingByID(id, isWorking);
    }

    @PutMapping(path = "/student/update/{id}")
    public @ResponseBody Student updateStudentById(@PathVariable Long id, @RequestBody Student student){
        return studentService.updateStudentByID(id, student);
    }

    @DeleteMapping(path = "/student/delete")
    public ResponseEntity<?> deleteStudent(@RequestBody Student student){
        return studentService.deleteStudent(student);
    }

    @DeleteMapping(path = "/student/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        return studentService.deleteStudentById(id);
    }
}
