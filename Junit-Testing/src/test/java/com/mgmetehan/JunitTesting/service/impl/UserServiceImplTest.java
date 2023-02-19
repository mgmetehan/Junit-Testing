package com.mgmetehan.JunitTesting.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.exception.UserException;
import com.mgmetehan.JunitTesting.model.User;
import com.mgmetehan.JunitTesting.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Test createUser called repository")
    void should_create_user() {
        // given
        var userRequestDto = new UserRequestDto();
        userRequestDto.setName("John");
        userRequestDto.setSurname("Doe");

        var savedUser = new User();
        savedUser.setName("John");
        savedUser.setSurname("Doe");
        savedUser.setId(1L);

        // when
        when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        var result = userService.createUser(userRequestDto);

        // then
        verify(userRepository).save(any(User.class));
        assertEquals(savedUser.getName(), result.getName());
        assertEquals(savedUser.getSurname(), result.getSurname());
        assertEquals(savedUser.getId(), result.getId());
    }

    @Test
    @DisplayName("Test getUserById called repository")
    void should_get_user_by_id() {
        // given
        var user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setId(1L);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var result = userService.getUserById(1L);

        // then
        verify(userRepository).findById(1L);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurname(), result.getSurname());
        assertEquals(user.getId(), result.getId());
    }

    @Test
    @DisplayName("Test getUserById throws an exception")
    void should_throw_exception_get_user_by_id() {
        // given
        var user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setId(1L);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        var ex = assertThrows(UserException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    @DisplayName("Test getUserByName called repository")
    void should_get_user_by_name() {
        // given
        var user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setId(1L);

        // when
        when(userRepository.findByName("John")).thenReturn(List.of(user));

        var result = userService.getUserByName("John");

        // then
        verify(userRepository).findByName("John");
        assertEquals(user.getName(), result.get(0).getName());
        assertEquals(user.getSurname(), result.get(0).getSurname());
        assertEquals(user.getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Test getUserByName return as List")
    public void should_return_list_get_user_by_name() {
        var name = "John";

        var user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setSurname("Doe");

        var user2 = new User();
        user2.setId(2L);
        user2.setName("Johnny Bravo");
        user2.setSurname("Bravo");

        var userList = Arrays.asList(user1, user2);

        when(userRepository.findByName(anyString())).thenReturn(userList);

        var userService = new UserServiceImpl(userRepository);

        var returnedUserList = userService.getUserByName(name);

        assertEquals(userList.size(), returnedUserList.size());

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            assertEquals(user.getId(), returnedUserList.get(i).getId());
            assertEquals(user.getName(), returnedUserList.get(i).getName());
            assertEquals(user.getSurname(), returnedUserList.get(i).getSurname());
        }
    }

    @Test
    @DisplayName("Test getUserByNameBySpringData return as List")
    public void should_get_user_by_name_by_spring_data() {
        var name = "John";

        var user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setSurname("Doe");

        var user2 = new User();
        user2.setId(2L);
        user2.setName("Johnny Bravo");
        user2.setSurname("Bravo");

        var userList = Arrays.asList(UserResponseDto.of(user1), UserResponseDto.of(user2));

        when(userRepository.findByNameLikeIgnoreCase(name)).thenReturn(userList);

        var userService = new UserServiceImpl(userRepository);

        var userResponseDtoList = userService.getUserByNameBySpringData(name);

        assertEquals(userList.size(), userResponseDtoList.size());

        for (int i = 0; i < userList.size(); i++) {
            UserResponseDto userResponseDto = userList.get(i);
            assertEquals(userResponseDto.getId(), userResponseDtoList.get(i).getId());
            assertEquals(userResponseDto.getName(), userResponseDtoList.get(i).getName());
            assertEquals(userResponseDto.getSurname(), userResponseDtoList.get(i).getSurname());
        }
    }
}
