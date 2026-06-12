package com.devstack.taskflow.service;

import com.devstack.taskflow.dto.UserRequestDto;
import com.devstack.taskflow.dto.UserResponseDto;
import com.devstack.taskflow.exception.userexceptions.UserNotFoundException;
import com.devstack.taskflow.exception.userexceptions.UserAlreadyExistsException;
import com.devstack.taskflow.model.User;
import com.devstack.taskflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUser(Long id){

        return entityToDto(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Nenhuma conta foi encontrada.", HttpStatus.NOT_FOUND)
        ));
    }

    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userList = new ArrayList<>();

        userRepository.findAll().forEach(u -> {
                    userList.add(new UserResponseDto(u.getName(), u.getEmail()));
                });

        return userList;
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Este usuário já existe.", HttpStatus.BAD_REQUEST);
        }

        User created = userRepository.save(dtoToEntity(dto));

        return entityToDto(created);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Nenhuma conta foi encontrada.", HttpStatus.NOT_FOUND)
        );
        userRepository.delete(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto){
        User found = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Nenhuma conta foi encontrada.", HttpStatus.NOT_FOUND)
        );

        found.setName(dto.getName());
        found.setEmail(dto.getEmail());
        found.setPassword(dto.getPassword());

        User updated = userRepository.save(found);

        return entityToDto(updated);
    }

    private UserResponseDto entityToDto(User user) {

        return new UserResponseDto(
                user.getName(),
                user.getEmail()
        );
    }

    private User dtoToEntity(UserRequestDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if(dto.getPassword() != null && dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }

        return user;
    }
}
