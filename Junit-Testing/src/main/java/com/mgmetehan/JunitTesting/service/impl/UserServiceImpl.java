package com.mgmetehan.JunitTesting.service.impl;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.model.User;
import com.mgmetehan.JunitTesting.repository.UserRepository;
import com.mgmetehan.JunitTesting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        var user = new User();
        user.setName(userRequestDto.getName());
        user.setSurname(userRequestDto.getSurname());

        var result = userRepository.save(user);
        return UserResponseDto.of(result);
    }
}
