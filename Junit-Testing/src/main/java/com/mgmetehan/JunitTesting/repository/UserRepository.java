package com.mgmetehan.JunitTesting.repository;

import com.mgmetehan.JunitTesting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
