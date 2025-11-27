package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.service.interfaces.AuthService;
import com.alterhub.alterhubbackend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Users.ROOT)
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping(ApiRoutes.Users.AUTHENTICATION)
    public ResponseEntity<AuthPayloadDTO> authentication(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        AuthResponseDTO response = userService.authentication(userAuthenticationDTO);
        return ResponseEntity.ok()
                .header("Set-Cookie", response.getRefreshTokenCookie().toString())
                .body(response.getAuthentificationInfos());
    }

    @PostMapping(ApiRoutes.Users.ACCESS_GRANTED)
    public ResponseEntity<Boolean> accessGranted(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.accessGranted(userDTO));
    }

    @PostMapping(ApiRoutes.Users.REFRESH_TOKEN)
    public ResponseEntity<AuthPayloadDTO> refreshToken(@RequestBody String refreshToken){
        AuthResponseDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok()
                .header("Set-Cookie", response.getRefreshTokenCookie().toString())
                .body(response.getAuthentificationInfos());
    }

    @PostMapping(ApiRoutes.Users.LOGOUT)
    public ResponseEntity<Void> logout(
            @CookieValue(name = ReturnMessages.REFRESH_TOKEN_COOKIE_NAME) String refreshToken
    ) {
        ResponseCookie deleteCookie = authService.logout(refreshToken);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
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
