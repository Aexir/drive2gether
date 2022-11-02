package pl.aexir.ride2gether.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.aexir.ride2gether.data.User;
import pl.aexir.ride2gether.data.requests.LoginRequest;
import pl.aexir.ride2gether.data.responses.ErrorResponse;
import pl.aexir.ride2gether.repositories.UserRepository;
import pl.aexir.ride2gether.services.interfaces.IAuthService;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,40}$";

    @Override
    public ResponseEntity<?> registerUser(User user) {
        if (user.getLogin().isBlank() || user.getLogin() == null){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Username cannot be null!"));
        }
        if (userRepository.existsByLogin(user.getLogin())){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Username is already taken!"));
        }
        if (!user.getPassword().matches(PASSWORD_PATTERN)) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Password must contain lowercase letter, uppercase letter, number and special character and between 6 and 40"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        if (!userRepository.existsByLogin(loginRequest.getLogin())){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Invalid credentials!"));
        }
        User user = userRepository.getByLogin(loginRequest.getLogin());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Invalid credentials!"));
        }
        return ResponseEntity.ok(loginRequest);
    }
}
