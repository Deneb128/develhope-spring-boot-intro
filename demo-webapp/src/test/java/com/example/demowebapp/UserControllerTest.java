package com.example.demowebapp;

import com.example.demowebapp.controllers.BasicController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UserControllerTest {

    @Autowired
    BasicController basicController;


    @Test
    void basicControllerLoad() {
        assertThat(basicController).isNotNull();
    }

}
