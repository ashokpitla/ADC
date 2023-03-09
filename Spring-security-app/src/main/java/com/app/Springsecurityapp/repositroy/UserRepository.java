package com.app.Springsecurityapp.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Springsecurityapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{



}
