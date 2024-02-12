package com.example.demowebapp;

import com.example.demowebapp.controllers.UserController;
import com.example.demowebapp.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
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
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User createAUser() throws Exception {
        return createAUser(true);
    }

    private User createAUser(Boolean withId) throws Exception {
        User user = new User();
        user.setNickname("TestNick");
        user.setPassword("TestPassword");
        user.setEmail("test@test.com");

        return createAUser(user, withId);
    }

    private User createAUser(User user, Boolean withId) throws Exception {
        if (withId == false) {
            return user;
        }
        MvcResult result = createAUserRequest(user);
        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        return userResponse;
    }

    private MvcResult createAUserRequest() throws Exception {
        User user = new User();
        user.setNickname("TestNick");
        user.setPassword("TestPassword");
        user.setEmail("test@test.com");

        return createAUserRequest(user);
    }

    private MvcResult createAUserRequest(User user) throws Exception {
        if (user == null) return null;
        String userJSON = objectMapper.writeValueAsString(user);

        return this.mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private User getUserFromId(Long id) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/get/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        try{
            String userJSON = result.getResponse().getContentAsString();
            return objectMapper.readValue(userJSON, User.class);
        }catch(Exception e){
            return null;
        }
    }

    private User getUserFromMvcResult(MvcResult result) throws Exception {
        String userJSON = result.getResponse().getContentAsString();
        return objectMapper.readValue(userJSON, User.class);
    }

    @Test
    void userControllerLoad() {
        assertThat(userController).isNotNull();
    }

    @Test
    void createAUserTest() throws Exception {
        MvcResult result = createAUserRequest();
        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(userResponse.getId()).isNotNull();
    }

    @Test
    void getUserList() throws Exception {
        this.createAUserRequest();

        MvcResult result = this.mockMvc.perform(get("/user/get/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<User> userListResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        System.out.println("Users in database: " + userListResponse.size());
        assertThat(userListResponse.size()).isNotZero();
    }

    @Test
    void getUserById() throws Exception {
        User user = this.createAUser();
        assertThat(user.getId()).isNotNull();

        User userResponse = getUserFromId(user.getId());
        assertThat(userResponse.getId()).isEqualTo(user.getId());
    }

    @Test
    void updateUserById() throws Exception {
        User user = this.createAUser();
        assertThat(user.getId()).isNotNull();
        User userBody = this.createAUser(false);

        final String newNickname = "TestNicknameNew";
        userBody.setNickname(newNickname);

        String userJSON = objectMapper.writeValueAsString(userBody);

        MvcResult result = this.mockMvc.perform(put("/user/update/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(userResponse.getNickname()).isEqualTo(newNickname);

        User userResponseGet = getUserFromId(user.getId());
        assertThat(userResponseGet.getId()).isEqualTo(user.getId());
    }

    @Test
    void updateIsBannedTrueByIDThatExists() throws Exception {
        User user = this.createAUser();
        assertThat(user.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(put("/user/update/" + user.getId() + "/banstatus?isBanned=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getIsBanned()).isEqualTo(true);

        User userResponseGet = getUserFromId(user.getId());
        assertThat(userResponseGet.getId()).isEqualTo(user.getId());
        assertThat(userResponseGet.getIsBanned()).isEqualTo(true);
    }

    @Test
    void updateIsBannedFalseByIDThatExists() throws Exception {
        User user = this.createAUser(false);
        user.setIsBanned(true);
        MvcResult originalResult = this.createAUserRequest(user);

        User originalUserResponseGet = getUserFromMvcResult(originalResult);
        assertThat(originalUserResponseGet.getId()).isNotNull();
        assertThat(originalUserResponseGet.getIsBanned()).isTrue();

        MvcResult result = this.mockMvc.perform(put("/user/update/" + originalUserResponseGet.getId() + "/banstatus?isBanned=false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getIsBanned()).isEqualTo(false);


        User userResponseGet = getUserFromId(originalUserResponseGet.getId());
        assertThat(userResponseGet.getId()).isEqualTo(originalUserResponseGet.getId());
        assertThat(userResponseGet.getIsBanned()).isEqualTo(false);
    }

    @Test
    void updateIsBannedByWrongID() throws Exception {
        User user = this.createAUser();
        assertThat(user.getId()).isNotNull();

        Long wrongId = user.getId()+1;
        MvcResult result = this.mockMvc.perform(put("/user/update/" + wrongId + "/banstatus?isBanned=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponseGet = getUserFromId(wrongId);
        assertThat(userResponseGet).isNull();
    }

    @Test
    void deleteUserById() throws Exception {
        User user = this.createAUser();
        String userJSON = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(delete("/user/delete/" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponseGet = getUserFromId(user.getId());
        assertThat(userResponseGet).isNull();
    }

    @Test
    void deleteUser() throws Exception {
        User user = this.createAUser();
        String userJSON = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(delete("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON)).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User userResponseGet = getUserFromId(user.getId());
        assertThat(userResponseGet).isNull();
    }
}
