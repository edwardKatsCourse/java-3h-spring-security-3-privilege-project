package com.forum;

import com.forum.entity.UserEntity;
import com.forum.entity.UserPrivilege;
import com.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    //Spring "main" method

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        UserEntity userEntity_1 = UserEntity.builder()
                .userPrivilege(UserPrivilege.ADMIN)
                .username("Vitaliia".toLowerCase())
                .build();

        userEntity_1 = userRepository.create(userEntity_1.getUsername());
        userRepository.promote(userEntity_1.getId());


        UserEntity userEntity_2 = UserEntity.builder()
                .userPrivilege(UserPrivilege.ADMIN)
                .username("Dmitrya".toLowerCase())
                .build();
        userEntity_2 = userRepository.create(userEntity_2.getUsername());
        userRepository.promote(userEntity_2.getId());

        UserEntity userEntity_3 = UserEntity.builder()
                .userPrivilege(UserPrivilege.REGULAR)
                .username("johndoe".toLowerCase())
                .build();

        userRepository.create(userEntity_3.getUsername());


    }
}
