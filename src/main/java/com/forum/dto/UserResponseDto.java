package com.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserResponseDto {

    private String id;
    private String username;
    private boolean isBlocked;
}
