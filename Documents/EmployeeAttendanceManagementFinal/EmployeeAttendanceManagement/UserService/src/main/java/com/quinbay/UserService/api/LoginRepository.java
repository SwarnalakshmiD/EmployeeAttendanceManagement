package com.quinbay.UserService.api;

import com.quinbay.UserService.model.entity.Login;
import com.quinbay.UserService.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUserName(String username);
}
