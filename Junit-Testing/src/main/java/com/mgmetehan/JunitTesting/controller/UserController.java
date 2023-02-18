package com.mgmetehan.JunitTesting.controller;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.model.User;
import com.mgmetehan.JunitTesting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(
                userService.createUser(userRequestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(
                userService.getUserById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/query")
    public ResponseEntity<List<User>> getUserByName(@RequestParam String name) {
        return new ResponseEntity<>(
                userService.getUserByName(name),
                HttpStatus.OK
        );
    }

    @GetMapping("/spring-data")
    public ResponseEntity<List<UserResponseDto>> getUserByNameBySpringData(@RequestParam String name) {
        return new ResponseEntity<>(
                userService.getUserByNameBySpringData(name),
                HttpStatus.OK
        );
    }
}
