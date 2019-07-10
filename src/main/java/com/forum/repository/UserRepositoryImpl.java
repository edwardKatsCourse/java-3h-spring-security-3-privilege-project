package com.forum.repository;

import com.forum.dto.UserRequestDto;
import com.forum.entity.UserEntity;
import com.forum.entity.UserPrivilege;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final List<UserEntity> users = new ArrayList<>();

    @Override
    public UserEntity create(String username) {

        if (this.findByUsername(username) != null) {
            String errorMessage = "User with name [" + username + "] already exists";
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }

        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .userPrivilege(UserPrivilege.REGULAR)
                .build();

        if (users.isEmpty()) {
            userEntity.setUserPrivilege(UserPrivilege.ADMIN);
        }

        users.add(userEntity);

        return userEntity;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return users.stream()
                .filter(x -> x.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserEntity promote(String id) {
        UserEntity userEntity = users.stream()
                .filter(x -> x.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such ID does not exist");
        }

        UserPrivilege privilege;
        if (userEntity.getUserPrivilege() == UserPrivilege.BLOCKED) {
            privilege = UserPrivilege.REGULAR;
        } else if (userEntity.getUserPrivilege() == UserPrivilege.REGULAR) {
            privilege = UserPrivilege.ADMIN;
        } else {
            privilege = UserPrivilege.ADMIN;
        }

        userEntity.setUserPrivilege(privilege);
        return userEntity;
    }

    @Override
    public UserEntity demote(String id) {

        UserEntity userEntity = users.stream()
                .filter(x -> x.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such ID does not exist");
        }
        UserPrivilege privilege;

        if (userEntity.getUserPrivilege() == UserPrivilege.ADMIN) {
            privilege = UserPrivilege.REGULAR;
        } else if (userEntity.getUserPrivilege() == UserPrivilege.REGULAR) {
            privilege = UserPrivilege.BLOCKED;
        } else {
            privilege = UserPrivilege.BLOCKED;
        }

        userEntity.setUserPrivilege(privilege);
        return userEntity;
    }

    @Override
    public List<UserEntity> findAll() {
        return users;
    }
}
