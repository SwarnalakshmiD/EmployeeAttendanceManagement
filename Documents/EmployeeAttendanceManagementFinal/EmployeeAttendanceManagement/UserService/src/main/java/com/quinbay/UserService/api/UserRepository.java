package com.quinbay.UserService.api;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quinbay.UserService.model.entity.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();




    List<User> findBymanagerId(int managerId);







}
