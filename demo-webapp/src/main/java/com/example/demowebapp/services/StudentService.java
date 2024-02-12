package com.example.demowebapp.services;

import com.example.demowebapp.entities.Student;
import com.example.demowebapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student addNewStudent(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    public List getStudentList() {
        return studentRepository.findAll();
    }

    public Student getStudentByID(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return null;
        }
        return student.get();
    }

    public Student updateStudentByID(Long id, Student student) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            student.setId(optStudent.get().getId());
            return studentRepository.saveAndFlush(student);
        }
        return null;
    }

    public Student updateIsWorkingByID(Long id, Boolean isWorking) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();
            student.setIsWorking(isWorking);
            return studentRepository.saveAndFlush(student);
        } else {
            return null;
        }
    }

    public ResponseEntity<?> deleteStudentById(Long id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (studentRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        studentRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteStudent(Student student) {
        if (student == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (studentRepository.findById(student.getId()).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        try {
            studentRepository.delete(student);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
