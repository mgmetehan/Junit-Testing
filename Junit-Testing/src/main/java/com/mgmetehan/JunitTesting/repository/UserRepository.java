package com.mgmetehan.JunitTesting.repository;

import com.mgmetehan.JunitTesting.dto.response.UserResponseDto;
import com.mgmetehan.JunitTesting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM TBL_USER WHERE name LIKE (%:name%)")
    List<User> findByName(@Param("name") String name);

    List<UserResponseDto> findByNameLikeIgnoreCase(String name);
}
