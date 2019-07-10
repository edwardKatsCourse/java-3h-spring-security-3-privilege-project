package com.forum.controller;

import com.forum.dto.UserRequestDto;
import com.forum.dto.UserResponseDto;
import com.forum.entity.UserEntity;
import com.forum.entity.UserPrivilege;
import com.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/create")
    public UserResponseDto create(@RequestBody UserRequestDto userRequestDto) {
        UserEntity userEntity = userRepository.create(userRequestDto.getUsername());

        System.out.printf("User created. ID: [%s], username: [%s], privilege: [%s]\n",
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getUserPrivilege());

        return UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .isBlocked(userEntity.getUserPrivilege() == UserPrivilege.BLOCKED)
                .build();
    }

    @GetMapping("/users/all")
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(x -> UserResponseDto.builder()
                        .username(x.getUsername())
                        .id(x.getId())
                        .isBlocked(x.getUserPrivilege() == UserPrivilege.BLOCKED)
                        .build()
                )
                .collect(Collectors.toList());
    }

    @PutMapping("/users/{userId}/promote")
    public void promote(@PathVariable("userId") String userId) {
        UserEntity userEntity = userRepository.promote(userId);
        System.out.printf("User with id: [%s] and username: [%s] was promoted to [%s]\n",
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getUserPrivilege());
    }


    @PutMapping("/users/{userId}/demote")
    public void demote(@PathVariable("userId") String userId) {
        UserEntity userEntity = userRepository.demote(userId);
        System.out.printf("User with id: [%s] and username: [%s] was demoted to [%s]\n",
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getUserPrivilege());
    }

}
