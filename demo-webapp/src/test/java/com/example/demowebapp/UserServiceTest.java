package com.example.demowebapp;

import com.example.demowebapp.entities.User;
import com.example.demowebapp.repositories.UserRepository;
import com.example.demowebapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value = "test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User createUser(){
        User user = new User();
        user.setNickname("TestNick");
        user.setPassword("TestPassword");
        user.setEmail("test@test.com");
        return user;
    }

    private User createUserFromDB(){
        return createUserFromDB(false);
    }

    private User createUserFromDB(Boolean isBanned){
        User user = createUser();
        user.setIsBanned(isBanned);
        return userRepository.saveAndFlush(user);
    }

    @Test
    void addNewUserTest()
    {
        User user = createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void getUserListServiceTest()
    {
        this.createUserFromDB();
        List<User> userList = userService.getUserList();
        assertThat(userList.isEmpty()).isEqualTo(false);
    }

    @Test
    void getUserByIDTest()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();
    }

    @Test
    void getUserByWrongIDTest()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId()+1);
        assertThat(userFromService).isNull();
    }

    @Test
    void updateUserByIDTest()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();

        User userBodyRequest = this.createUser();
        userBodyRequest.setId(userFromService.getId());
        String newNickname = "TestNicknameNew";
        userBodyRequest.setNickname(newNickname);

        User userFromUpdate = userService.updateUserByID(userFromService.getId(), userBodyRequest);
        assertThat(userFromUpdate).isNotNull();
        assertThat(userFromUpdate.getId()).isNotNull();
        assertThat(userFromUpdate.getId()).isEqualTo(userBodyRequest.getId());
        assertThat(userFromUpdate.getNickname()).isEqualTo(userBodyRequest.getNickname());
        assertThat(userFromUpdate.getPassword()).isEqualTo(userBodyRequest.getPassword());
        assertThat(userFromUpdate.getEmail()).isEqualTo(userBodyRequest.getEmail());
        assertThat(userFromUpdate.getIsBanned()).isEqualTo(userBodyRequest.getIsBanned());
        assertThat(userFromUpdate.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void updateUserByWrongIDTest()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();

        User userBodyRequest = this.createUser();
        userBodyRequest.setId(userFromService.getId());
        String newNickname = "TestNicknameNew";
        userBodyRequest.setNickname(newNickname);

        User userFromUpdate = userService.updateUserByID(userFromService.getId()+1, userBodyRequest);
        assertThat(userFromUpdate).isNull();
    }

    @Test
    void updateIsBannedTrueByID()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getIsBanned()).isFalse();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();
        assertThat(userFromService.getIsBanned()).isFalse();



        User userFromUpdate = userService.updateIsBannedByID(userFromService.getId(), true);
        assertThat(userFromUpdate).isNotNull();
        assertThat(userFromUpdate.getId()).isNotNull();
        assertThat(userFromUpdate.getIsBanned()).isTrue();

        User userFromServiceNew = userService.getUserByID(userFromService.getId());
        assertThat(userFromServiceNew).isNotNull();
        assertThat(userFromServiceNew.getId()).isEqualTo(userFromUpdate.getId());
        assertThat(userFromServiceNew.getNickname()).isEqualTo(userFromUpdate.getNickname());
        assertThat(userFromServiceNew.getPassword()).isEqualTo(userFromUpdate.getPassword());
        assertThat(userFromServiceNew.getEmail()).isEqualTo(userFromUpdate.getEmail());
        assertThat(userFromServiceNew.getIsBanned()).isEqualTo(userFromUpdate.getIsBanned());
        assertThat(userFromServiceNew.getIsBanned()).isTrue();
    }

    @Test
    void updateIsBannedFalseByID()
    {
        User user = this.createUserFromDB(true);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getIsBanned()).isTrue();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();
        assertThat(userFromService.getIsBanned()).isTrue();



        User userFromUpdate = userService.updateIsBannedByID(userFromService.getId(), false);
        assertThat(userFromUpdate).isNotNull();
        assertThat(userFromUpdate.getId()).isNotNull();
        assertThat(userFromUpdate.getIsBanned()).isFalse();

        User userFromServiceNew = userService.getUserByID(userFromService.getId());
        assertThat(userFromServiceNew).isNotNull();
        assertThat(userFromServiceNew.getId()).isEqualTo(userFromUpdate.getId());
        assertThat(userFromServiceNew.getNickname()).isEqualTo(userFromUpdate.getNickname());
        assertThat(userFromServiceNew.getPassword()).isEqualTo(userFromUpdate.getPassword());
        assertThat(userFromServiceNew.getEmail()).isEqualTo(userFromUpdate.getEmail());
        assertThat(userFromServiceNew.getIsBanned()).isEqualTo(userFromUpdate.getIsBanned());
        assertThat(userFromServiceNew.getIsBanned()).isFalse();
    }

    @Test
    void updateIsBannedByWrongID()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getIsBanned()).isFalse();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();
        assertThat(userFromService.getIsBanned()).isFalse();


        Long wrongId = userFromService.getId() + 1;
        User userFromUpdate = userService.updateIsBannedByID(wrongId, true);
        assertThat(userFromUpdate).isNull();
    }

    @Test
    void deleteUserById()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();

        userService.deleteUserByID(userFromService.getId());

        User userAfterDelete = userService.getUserByID(user.getId());
        assertThat(userAfterDelete).isNull();
    }

    @Test
    void deleteUser()
    {
        User user = this.createUserFromDB();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        User userFromService = userService.getUserByID(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();

        userService.deleteUser(user);

        User userAfterDelete = userService.getUserByID(user.getId());
        assertThat(userAfterDelete).isNull();
    }

}
