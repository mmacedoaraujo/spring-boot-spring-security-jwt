package com.mmacedoaraujo.springbootspringsecurityjwt;

import com.mmacedoaraujo.springbootspringsecurityjwt.domain.AppUser;
import com.mmacedoaraujo.springbootspringsecurityjwt.domain.Role;
import com.mmacedoaraujo.springbootspringsecurityjwt.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SpringbootSpringsecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSpringsecurityJwtApplication.class, args);
	}
	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(null, "ROLE_USER"));
			appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			appUserService.saveUser(new AppUser(null, "John Travolta", "john", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Will Smith", "will", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Jim Carrey", "jim", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Arnold Schwarzernegger", "arnold", "1234", new ArrayList<>()));

			appUserService.addRoleToUser("john", "ROLE_USER");
			appUserService.addRoleToUser("john", "ROLE_MANAGER");
			appUserService.addRoleToUser("will", "ROLE_MANAGER");
			appUserService.addRoleToUser("jim", "ROLE_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_USER");
		};
	}
}
