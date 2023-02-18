package com.mgmetehan.JunitTesting.service;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.model.User;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long id);

    List<User> getUserByName(String name);

    List<UserResponseDto> getUserByNameBySpringData(String name);
}
