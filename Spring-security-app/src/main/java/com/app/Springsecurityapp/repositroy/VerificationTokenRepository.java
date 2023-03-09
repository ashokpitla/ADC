package com.app.Springsecurityapp.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Springsecurityapp.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);

}
