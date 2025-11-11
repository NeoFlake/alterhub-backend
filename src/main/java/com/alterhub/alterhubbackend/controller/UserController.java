package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Users.ROOT)
public class UserController {

    private final UserService userService;

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping(ApiRoutes.Users.AUTHENTICATION)
    public ResponseEntity<UserDTO> authentication(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        return ResponseEntity.ok(userService.authentication(userAuthenticationDTO));
    }

    @PostMapping(ApiRoutes.Users.ACCESS_GRANTED)
    public ResponseEntity<Boolean> accessGranted(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.accessGranted(userRequestDTO));
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.addUser(userRequestDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<UserDTO> updateUserById(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUserById(id, userRequestDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable UUID id, @RequestParam UserAuthenticationDTO userAuthenticationDTO) {
        userService.deleteUserById(id, userAuthenticationDTO);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
