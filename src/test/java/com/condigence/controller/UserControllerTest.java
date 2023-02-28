package com.condigence.controller;


import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes={UserControllerTest.class})
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    List<UserDTO> users =  new ArrayList<>();

    ProfileDTO profiledto;

    UserDTO user;

    @Test
    public void test_getAllUsers(){
        users =  new ArrayList<>();
        users.add(new UserDTO("1", "vishnu@gmail.com", "9742503868", "vish",  profiledto,"1234", "1234",   true, true , true));
        users.add(new UserDTO("2", "aryan@gmail.com", "9742503867", "Aryan",  profiledto,"1234", "1234",   true, true , true));
        when(userService.getAll()).thenReturn(users); // Mocking
        ResponseEntity<List<UserDTO>> resp = userController.getAll();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(2,resp.getBody().size());
    }

    @Test
    public void test_getUserById(){
        user =new UserDTO("1", "vishnu@gmail.com", "9742503868", "vish",  profiledto,"1234", "1234",   true, true , true);
        when(userService.getUserById("1")).thenReturn(user); // Mocking
        UserDTO expected = userController.getUserById("1");
        assertEquals(expected, userController.getUserById("1"));
    }
}
