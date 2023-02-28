package com.condigence.service;

import com.condigence.model.User;
import com.condigence.repository.UserRepository;
import com.condigence.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    List<User> users =  new ArrayList<>();


    @Test @Order(1)
    public void test_getAll(){
        users =  new ArrayList<>();
        users.add(new User("1", "vishnu@gmail.com", "9742503868", "vish", "1234", "1234", "29-04-2023", true, true, true , true));
        users.add(new User("2", "aryan@gmail.com", "11111111", "aryan", "1234", "1234", "29-04-2023", true, true, true , true));
        when(userRepository.findAll()).thenReturn(users); // Mocking
        assertEquals(2,userService.getAll().size());
        assertNotEquals(3,userService.getAll().size());
    }
    @Test
    @Order(2)
    @Disabled
    public void test_getById(){
        User user = new User("1", "vishnu@gmail.com", "9742503868", "vish", "1234", "1234", "29-04-2023", true, true, true , true);
        String userId = "1";
        when(userRepository.findById(userId).get()).thenReturn(user); // Mocking
        assertEquals(true,userService.getUserByUserId(userId).get().getId().equalsIgnoreCase(userId));
    }

    @Test
    @Order(3)
    public void addUser(){
        User user = new User("1", "vishnu@gmail.com", "9742503868", "vish", "1234", "1234", "29-04-2023", true, true, true , true);
        when(userRepository.save(user)).thenReturn(user); // Mocking
       // assertEquals(user,userService.saveUser(user));  // userService Needs userBean
    }

    @Test
    @Order(4)
    @Disabled
    public void updateUser(){
        User user = new User("1", "vishnu@gmail.com", "9742503868", "vish", "1234", "1234", "29-04-2023", true, true, true , true);
        when(userRepository.save(user)).thenReturn(user); // Mocking
        // assertEquals(user,userService.updateUser(user));  // userService Needs userBean
    }

    @Test
    @Order(5)
    public void deleteUser(){
        User user = new User("1", "vishnu@gmail.com", "9742503868", "vish", "1234", "1234", "29-04-2023", true, true, true , true);
        userService.deleteById(user.getId());// Can not Mock delete
        verify(userRepository,times(1)).deleteById(user.getId());
    }


}
