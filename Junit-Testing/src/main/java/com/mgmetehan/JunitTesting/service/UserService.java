package com.mgmetehan.JunitTesting.service;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
