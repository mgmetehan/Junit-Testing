package com.mgmetehan.JunitTesting.service.impl;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.exception.UserException;
import com.mgmetehan.JunitTesting.model.User;
import com.mgmetehan.JunitTesting.repository.UserRepository;
import com.mgmetehan.JunitTesting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserResponseDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        return UserResponseDto.of(user);
    }

    @Override
    public List<UserResponseDto> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<UserResponseDto> getUserByNameBySpringData(String name) {
        return userRepository.findByNameLikeIgnoreCase(name);
    }
}
