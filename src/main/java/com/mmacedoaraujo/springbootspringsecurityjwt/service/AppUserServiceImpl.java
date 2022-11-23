package com.mmacedoaraujo.springbootspringsecurityjwt.service;

import com.mmacedoaraujo.springbootspringsecurityjwt.domain.AppUser;
import com.mmacedoaraujo.springbootspringsecurityjwt.domain.Role;
import com.mmacedoaraujo.springbootspringsecurityjwt.repository.AppUserRepository;
import com.mmacedoaraujo.springbootspringsecurityjwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getName());
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Role founRole = roleRepository.findByName(roleName);
        AppUser foundAppUser = getUser(username);
        foundAppUser.getRoles().add(founRole);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }
}
