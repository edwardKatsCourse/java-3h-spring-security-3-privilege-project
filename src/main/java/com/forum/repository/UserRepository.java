package com.forum.repository;

import com.forum.dto.UserRequestDto;
import com.forum.entity.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface UserRepository {

    UserEntity create(String username);
    UserEntity findByUsername(String username);
    UserEntity promote(String id);
    UserEntity demote(String id);
    List<UserEntity> findAll();

}
