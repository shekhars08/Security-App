package com.spring.securityapp;

import com.spring.securityapp.entities.UserEntity;
import com.spring.securityapp.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		UserEntity user = new UserEntity(
				4L,
				"Shekhar",
				"shekhar@gmail.com",
				"1234"
		);

		String token = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);

	}

}
