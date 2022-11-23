package com.mmacedoaraujo.springbootspringsecurityjwt.controllers;

import com.mmacedoaraujo.springbootspringsecurityjwt.domain.AppUser;
import com.mmacedoaraujo.springbootspringsecurityjwt.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

}
