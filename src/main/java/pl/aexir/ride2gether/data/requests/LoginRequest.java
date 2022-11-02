package pl.aexir.ride2gether.data.requests;

import lombok.Data;

@Data
public class LoginRequest {
    String login;
    String password;
}
