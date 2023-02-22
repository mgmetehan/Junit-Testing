package com.mgmetehan.JunitTesting.repository;

import com.mgmetehan.JunitTesting.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<String> nameCaptor;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("JPA - Test demos like native query")
    void should_test_like_native_query() {
        // Prepare test data
        var user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Smith");

        var user2 = new User();
        user2.setId(2L);
        user2.setName("John1");
        user2.setSurname("Doe");

        List<User> expectedUsers = Arrays.asList(user, user2);

        // Set up mock behavior
        when(userRepository.findByName(user.getName())).thenReturn(expectedUsers);

        // Call method under test
        var actualUsers = userRepository.findByName(user.getName());

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findByName(nameCaptor.capture());
        assertEquals(user.getName(), nameCaptor.getValue());
    }
}
