package pl.aexir.ride2gether.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.aexir.ride2gether.data.User;
import pl.aexir.ride2gether.data.requests.LoginRequest;

public interface IAuthService {
    ResponseEntity<?> registerUser(User user);
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
}
