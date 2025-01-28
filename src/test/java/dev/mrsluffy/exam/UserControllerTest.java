package dev.mrsluffy.exam;

/**
 * product
 *
 * @author John Andrew Camu <werdna.jac@gmail.com>
 * @version 1.0
 * @since 10/12/2024
 **/

import dev.mrsluffy.exam.helper.common.response.PagedResponse;
import dev.mrsluffy.exam.helper.common.response.UnauthorizedException;
import dev.mrsluffy.exam.user.controller.UserDataController;
import dev.mrsluffy.exam.user.data.entities.UserData;
import dev.mrsluffy.exam.user.data.entities.UserGender;
import dev.mrsluffy.exam.user.data.entities.UserRole;
import dev.mrsluffy.exam.user.data.model.UserModel;
import dev.mrsluffy.exam.user.data.model.UserRequestModel;
import dev.mrsluffy.exam.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDataController userDataController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userDataController).build();
    }

    @Test
    public void testAddUser_Success() {
        UserRequestModel userModel = new UserRequestModel();
        userModel.setName("John Andre");
        userModel.setEmail("andrew@email.com");

        UserModel expectedUser = UserModel.builder()
                .name("John Andre")
                .email("andrew@email.com")
                .role(UserRole.valueOf("GENERAL"))
                .gender(UserGender.valueOf("MALE"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .age(34)
                .build();

        when(userService.addUser(any(UserRequestModel.class))).thenReturn(expectedUser);

        ResponseEntity<?> response = userDataController.CreateUser(userModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    public void testGetUsers_Success() {

        PagedResponse<UserData> pagedResponse = new PagedResponse<>(Collections.singletonList(new UserData()), 1);
        when(userService.getUsers(anyString(), any(Pageable.class))).thenReturn(pagedResponse);

        ResponseEntity<?> response = userDataController.GetUsers("andrew@email.com", 1, 10, "email", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagedResponse, response.getBody());
    }

    @Test
    public void testUpdateUser_Success() {
        Integer userId = 1;
        UserRequestModel userModel = new UserRequestModel();
        userModel.setName("John Andrew");
        userModel.setGender(UserGender.valueOf("MALE"));
        userModel.setBirthDate(LocalDate.parse("1990-01-01"));

        UserModel expectedUser = UserModel.builder()
                .name("John Andrew")
                .email("andrew@email.com")
                .role(UserRole.valueOf("GENERAL"))
                .gender(UserGender.valueOf("MALE"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .age(34)
                .build();

        when(userService.updateUser(eq(userId), any(UserRequestModel.class))).thenReturn(expectedUser);

        ResponseEntity<?> response = userDataController.UpdateUser(userId, userModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userService).deleteUser(anyInt());

        ResponseEntity<?> response = userDataController.DeleteProduct( 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}