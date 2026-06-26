package com.devstack.taskflow.service;

import com.devstack.taskflow.dto.userdto.UserRequestDto;
import com.devstack.taskflow.dto.userdto.UserResponseDto;
import com.devstack.taskflow.exception.userexceptions.UserAlreadyExistsException;
import com.devstack.taskflow.exception.userexceptions.UserNotFoundException;
import com.devstack.taskflow.model.User;
import com.devstack.taskflow.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should get a user successfully")
    void getUserSuccessfully() {

        User userSaved = new User("Teste", "teste@email.com", "teste123");
        ReflectionTestUtils.setField(userSaved, "id", 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userSaved));

        UserResponseDto dtoFound = userService.getUser(1L);

        assertEquals(dtoFound.name(), userSaved.getName());
        assertEquals(dtoFound.email(), userSaved.getEmail());
    }

    @Test
    @DisplayName("Should throws a UserNotFoundException when user not exists")
    void getUserThrowingUserNotFoundException() {

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(1L);
        });

        assertEquals("Nenhuma conta foi encontrada.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpCode());

    }

    @Test
    @DisplayName("Should create a new user successfully when the user not exists")
    void createUserSuccessfully() {

        User userSaved = new User("Teste", "teste@email.com", "teste123");
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("Teste");
        userRequestDto.setEmail("teste@email.com");
        userRequestDto.setPassword("teste123");

        when(userRepository.existsByEmail(any())).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        UserResponseDto userCreated = userService.createUser(userRequestDto);

        assertEquals(userCreated.name(), userSaved.getName());
        assertEquals(userCreated.email(), userSaved.getEmail());

    }

    @Test
    @DisplayName("Should throws a UserAlreadyExistsException")
    void createUserWhenUserAlreadyExists() {

        when(userRepository.existsByEmail(any())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(new UserRequestDto());
        });

        assertEquals("Este usuário já existe.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpCode());
    }

//  TODO:

    @Test
    @DisplayName("Should update a user when the user exists and the updated fields is ok")
    void updateUserWhenAllOk() {

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("TesteAlterado");
        requestDto.setEmail("teste-alterado@email.com");
        requestDto.setPassword("teste_alterado123");

        User user = new User("Teste", "teste@email.com", "teste123");
        ReflectionTestUtils.setField(user, "id", 1L);

        User userUpdated = new User("TesteAlterado", "teste-alterado@email.com","teste_alterado123");
        ReflectionTestUtils.setField(userUpdated, "id", 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenReturn(userUpdated);

        UserResponseDto responseDto = userService.updateUser(1L, requestDto);

        assertEquals(responseDto.name(), userUpdated.getName());
        assertEquals(responseDto.email(), userUpdated.getEmail());
    }

}