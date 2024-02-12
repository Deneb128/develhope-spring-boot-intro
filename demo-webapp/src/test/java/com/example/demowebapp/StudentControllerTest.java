package com.example.demowebapp;

import com.example.demowebapp.controllers.StudentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demowebapp.entities.Student;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Student createAStudent() throws Exception {
        return createAStudent(true);
    }

    private Student createAStudent(Boolean withId) throws Exception {
        Student student = new Student();
        student.setName("TestName");
        student.setSurname("TestSurname");

        return createAStudent(student, withId);
    }

    private Student createAStudent(Student student, Boolean withId) throws Exception {
        if (withId == false) {
            return student;
        }
        MvcResult result = createAStudentRequest(student);
        Student studentResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        return studentResponse;
    }

    private MvcResult createAStudentRequest() throws Exception {
        Student student = new Student();
        student.setName("TestName");
        student.setSurname("TestSurname");

        return createAStudentRequest(student);
    }

    private MvcResult createAStudentRequest(Student student) throws Exception {
        if (student == null) return null;
        String studentJSON = objectMapper.writeValueAsString(student);

        return this.mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private Student getStudentFromId(Long id) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student/get/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        try{
            String studentJSON = result.getResponse().getContentAsString();
            return objectMapper.readValue(studentJSON, Student.class);
        }catch(Exception e){
            return null;
        }
    }

    private Student getStudentFromMvcResult(MvcResult result) throws Exception {
        String studentJSON = result.getResponse().getContentAsString();
        return objectMapper.readValue(studentJSON, Student.class);
    }

    @Test
    void studentControllerLoad() {
        assertThat(studentController).isNotNull();
    }

    @Test
    void createAStudentTest() throws Exception {
        MvcResult result = createAStudentRequest();
        Student studentResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(studentResponse.getId()).isNotNull();
    }

    @Test
    void getStudentList() throws Exception {
        this.createAStudentRequest();

        MvcResult result = this.mockMvc.perform(get("/student/get/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Student> studentListResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        System.out.println("Students in database: " + studentListResponse.size());
        assertThat(studentListResponse.size()).isNotZero();
    }

    @Test
    void getStudentById() throws Exception {
        Student student = this.createAStudent();
        assertThat(student.getId()).isNotNull();

        Student studentResponse = getStudentFromId(student.getId());
        assertThat(studentResponse.getId()).isEqualTo(student.getId());
    }

    @Test
    void updateStudentById() throws Exception {
        Student student = this.createAStudent();
        assertThat(student.getId()).isNotNull();
        Student studentBody = this.createAStudent(false);

        final String newName = "TestNameChanged";
        studentBody.setName(newName);

        String studentJSON = objectMapper.writeValueAsString(studentBody);

        MvcResult result = this.mockMvc.perform(put("/student/update/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(studentResponse.getName()).isEqualTo(newName);

        Student studentResponseGet = getStudentFromId(student.getId());
        assertThat(studentResponseGet.getId()).isEqualTo(student.getId());
    }

    @Test
    void updateIsWorkingTrueByIDThatExists() throws Exception {
        Student student = this.createAStudent();
        assertThat(student.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(put("/student/update/" + student.getId() + "/workingstatus?isWorking=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(studentResponse).isNotNull();
        assertThat(studentResponse.getIsWorking()).isEqualTo(true);

        Student studentResponseGet = getStudentFromId(student.getId());
        assertThat(studentResponseGet.getId()).isEqualTo(student.getId());
        assertThat(studentResponseGet.getIsWorking()).isEqualTo(true);
    }

    @Test
    void updateIsWorkingFalseByIDThatExists() throws Exception {
        Student student = this.createAStudent(false);
        student.setIsWorking(true);
        MvcResult originalResult = this.createAStudentRequest(student);

        Student originalStudentResponseGet = getStudentFromMvcResult(originalResult);
        assertThat(originalStudentResponseGet.getId()).isNotNull();
        assertThat(originalStudentResponseGet.getIsWorking()).isTrue();

        MvcResult result = this.mockMvc.perform(put("/student/update/" + originalStudentResponseGet.getId() + "/workingstatus?isWorking=false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(studentResponse).isNotNull();
        assertThat(studentResponse.getIsWorking()).isEqualTo(false);


        Student studentResponseGet = getStudentFromId(originalStudentResponseGet.getId());
        assertThat(studentResponseGet.getId()).isEqualTo(originalStudentResponseGet.getId());
        assertThat(studentResponseGet.getIsWorking()).isEqualTo(false);
    }

    @Test
    void updateIsWorkingByWrongID() throws Exception {
        Student student = this.createAStudent();
        assertThat(student.getId()).isNotNull();

        Long wrongId = student.getId()+1;
        MvcResult result = this.mockMvc.perform(put("/student/update/" + wrongId + "/workingstatus?isWorking=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponseGet = getStudentFromId(wrongId);
        assertThat(studentResponseGet).isNull();
    }

    @Test
    void deleteStudentById() throws Exception {
        Student student = this.createAStudent();
        String studentJSON = objectMapper.writeValueAsString(student);

        this.mockMvc.perform(delete("/student/delete/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponseGet = getStudentFromId(student.getId());
        assertThat(studentResponseGet).isNull();
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = this.createAStudent();
        String studentJSON = objectMapper.writeValueAsString(student);

        this.mockMvc.perform(delete("/student/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON)).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentResponseGet = getStudentFromId(student.getId());
        assertThat(studentResponseGet).isNull();
    }
}
