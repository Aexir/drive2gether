package pl.aexir.ride2gether.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aexir.ride2gether.data.User;
import pl.aexir.ride2gether.data.requests.LoginRequest;
import pl.aexir.ride2gether.services.interfaces.IAuthService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid User user){
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginRequest loginRequest){
        return authService.loginUser(loginRequest);
    }
}
