package com.example.demowebapp;

import com.example.demowebapp.entities.Student;
import com.example.demowebapp.repositories.StudentRepository;
import com.example.demowebapp.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value = "test")
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private Student createStudent(){
        Student student = new Student();
        student.setName("TestName");
        student.setSurname("TestSurname");
        return student;
    }

    private Student createStudentFromDB(){
        return createStudentFromDB(false);
    }

    private Student createStudentFromDB(Boolean isWorking){
        Student student = createStudent();
        student.setIsWorking(isWorking);
        return studentRepository.saveAndFlush(student);
    }

    @Test
    void addNewStudentTest()
    {
        Student student = createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
    }

    @Test
    void getStudentListServiceTest()
    {
        this.createStudentFromDB();
        List<Student> studentList = studentService.getStudentList();
        assertThat(studentList.isEmpty()).isEqualTo(false);
    }

    @Test
    void getStudentByIDTest()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();
    }

    @Test
    void getStudentByWrongIDTest()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId()+1);
        assertThat(studentFromService).isNull();
    }

    @Test
    void updateStudentByIDTest()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();

        Student studentBodyRequest = this.createStudent();
        studentBodyRequest.setId(studentFromService.getId());
        String newName = "TestNameNew";
        studentBodyRequest.setName(newName);

        Student studentFromUpdate = studentService.updateStudentByID(studentFromService.getId(), studentBodyRequest);
        assertThat(studentFromUpdate).isNotNull();
        assertThat(studentFromUpdate.getId()).isNotNull();
        assertThat(studentFromUpdate.getId()).isEqualTo(studentBodyRequest.getId());
        assertThat(studentFromUpdate.getName()).isEqualTo(studentBodyRequest.getName());
        assertThat(studentFromUpdate.getSurname()).isEqualTo(studentBodyRequest.getSurname());
        assertThat(studentFromUpdate.getIsWorking()).isEqualTo(studentBodyRequest.getIsWorking());
        assertThat(studentFromUpdate.getName()).isEqualTo(newName);
    }

    @Test
    void updateStudentByWrongIDTest()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();

        Student studentBodyRequest = this.createStudent();
        studentBodyRequest.setId(studentFromService.getId());
        String newName = "TestNameNew";
        studentBodyRequest.setName(newName);

        Student studentFromUpdate = studentService.updateStudentByID(studentFromService.getId()+1, studentBodyRequest);
        assertThat(studentFromUpdate).isNull();
    }

    @Test
    void updateIsWorkingTrueByID()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        assertThat(student.getIsWorking()).isFalse();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();
        assertThat(studentFromService.getIsWorking()).isFalse();



        Student studentFromUpdate = studentService.updateIsWorkingByID(studentFromService.getId(), true);
        assertThat(studentFromUpdate).isNotNull();
        assertThat(studentFromUpdate.getId()).isNotNull();
        assertThat(studentFromUpdate.getIsWorking()).isTrue();

        Student studentFromServiceNew = studentService.getStudentByID(studentFromService.getId());
        assertThat(studentFromServiceNew).isNotNull();
        assertThat(studentFromServiceNew.getId()).isEqualTo(studentFromUpdate.getId());
        assertThat(studentFromServiceNew.getName()).isEqualTo(studentFromUpdate.getName());
        assertThat(studentFromServiceNew.getSurname()).isEqualTo(studentFromUpdate.getSurname());
        assertThat(studentFromServiceNew.getIsWorking()).isEqualTo(studentFromUpdate.getIsWorking());
        assertThat(studentFromServiceNew.getIsWorking()).isTrue();
    }

    @Test
    void updateIsWorkingFalseByID()
    {
        Student student = this.createStudentFromDB(true);
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        assertThat(student.getIsWorking()).isTrue();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();
        assertThat(studentFromService.getIsWorking()).isTrue();



        Student studentFromUpdate = studentService.updateIsWorkingByID(studentFromService.getId(), false);
        assertThat(studentFromUpdate).isNotNull();
        assertThat(studentFromUpdate.getId()).isNotNull();
        assertThat(studentFromUpdate.getIsWorking()).isFalse();

        Student studentFromServiceNew = studentService.getStudentByID(studentFromService.getId());
        assertThat(studentFromServiceNew).isNotNull();
        assertThat(studentFromServiceNew.getId()).isEqualTo(studentFromUpdate.getId());
        assertThat(studentFromServiceNew.getName()).isEqualTo(studentFromUpdate.getName());
        assertThat(studentFromServiceNew.getSurname()).isEqualTo(studentFromUpdate.getSurname());
        assertThat(studentFromServiceNew.getIsWorking()).isEqualTo(studentFromUpdate.getIsWorking());
        assertThat(studentFromServiceNew.getIsWorking()).isFalse();
    }

    @Test
    void updateIsWorkingByWrongID()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        assertThat(student.getIsWorking()).isFalse();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();
        assertThat(studentFromService.getIsWorking()).isFalse();


        Long wrongId = studentFromService.getId() + 1;
        Student studentFromUpdate = studentService.updateIsWorkingByID(wrongId, true);
        assertThat(studentFromUpdate).isNull();
    }

    @Test
    void deleteStudentById()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();

        studentService.deleteStudentById(studentFromService.getId());

        Student studentAfterDelete = studentService.getStudentByID(student.getId());
        assertThat(studentAfterDelete).isNull();
    }

    @Test
    void deleteStudent()
    {
        Student student = this.createStudentFromDB();
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
        Student studentFromService = studentService.getStudentByID(student.getId());
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();

        studentService.deleteStudent(student);

        Student studentAfterDelete = studentService.getStudentByID(student.getId());
        assertThat(studentAfterDelete).isNull();
    }

}
