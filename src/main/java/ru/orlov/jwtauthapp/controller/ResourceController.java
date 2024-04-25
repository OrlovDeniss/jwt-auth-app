package ru.orlov.jwtauthapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resources")
@RequiredArgsConstructor
public class ResourceController {

    @Operation(summary = "Доступ к защищенным ресурсам")
    @GetMapping("protected")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> getProtectedResource() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Доступ к приватным ресурсам")
    @GetMapping("private")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> getPrivateResource() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
