package com.forum.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserEntity {

    private String id;
    private String username;
    private UserPrivilege userPrivilege;

}
