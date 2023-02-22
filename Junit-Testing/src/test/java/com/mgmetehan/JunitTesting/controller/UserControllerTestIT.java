package com.mgmetehan.JunitTesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgmetehan.JunitTesting.dto.request.UserRequestDto;
import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.model.User;
import com.mgmetehan.JunitTesting.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("IT - Should return CREATED and a demo json")
    public void create_demo_should_return_CREATED() throws Exception {
        // Prepare test data
        var userRequestDto = new UserRequestDto();
        userRequestDto.setName("John");
        userRequestDto.setSurname("Smith");

        var expectedUserResponseDto = new UserResponseDto(1L, "John", "Smith");

        // Set up mock behavior
        when(userService.createUser(userRequestDto)).thenReturn(expectedUserResponseDto);

        // Call method under test
        ResponseEntity<UserResponseDto> responseEntity = new UserController(userService).createUser(userRequestDto);

        // Verify results
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedUserResponseDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("IT - Should return CREATED and a demo json")
    public void create_demo_should_return_CREATED_and_a_demo_json() throws Exception {
        // Prepare test data
        var userRequestDto = new UserRequestDto();
        userRequestDto.setName("John");
        userRequestDto.setSurname("Smith");

        var expectedUserResponseDto = new UserResponseDto(1L, "John", "Smith");

        // Set up mock behavior
        when(userService.createUser(userRequestDto)).thenReturn(expectedUserResponseDto);

        // Call method under test
        mockMvc.perform(post("/api/v1/user").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(userRequestDto))).andDo(print()).
                andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$.name").value("John")).
                andExpect(jsonPath("$.surname").value("Smith"));

        // Verify results
        verify(userService).createUser(userRequestDto);
    }

    @Test
    @DisplayName("IT - Should return OK and a demo json")
    public void get_demo_by_id_should_return_OK_and_a_demo_json() throws Exception {
        // Prepare test data
        var expectedUserResponseDto = new UserResponseDto(1L, "John", "Smith");

        // Set up mock behavior
        when(userService.getUserById(1L)).thenReturn(expectedUserResponseDto);

        // Call method under test
        mockMvc.perform(get("/api/v1/user/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedUserResponseDto))).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$.name").value("John")).
                andExpect(jsonPath("$.surname").value("Smith"));

        // Verify results
        verify(userService).getUserById(1L);
    }

    @Test
    @DisplayName("IT - Should return OK and a list of demo json")
    public void get_demo_by_id_should_return_OK_and_a_list_of_demo_json() throws Exception {
        // Prepare test data
        var user1 = new User();
        user1.setId(1L);
        user1.setName("John");
        user1.setSurname("Doe");

        var user2 = new User();
        user2.setId(2L);
        user2.setName("Johnny");
        user2.setSurname("Bravo");

        var expectedList = List.of(user1, user2);

        // Set up mock behavior
        when(userService.getUserByName(user1.getName())).thenReturn(expectedList);

        // Call method under test
        mockMvc.perform(
                        get("/api/v1/user/query?name=" + user1.getName())).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$[0].id").value(user1.getId())).
                andExpect(jsonPath("$[0].name").value(user1.getName())).
                andExpect(jsonPath("$[1].id").value(user2.getId())).
                andExpect(jsonPath("$[1].name").value(user2.getName()));
    }
}