package com.mgmetehan.JunitTesting.dto.response;

import com.mgmetehan.JunitTesting.model.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;

    public UserResponseDto(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getSurname());
    }
}
