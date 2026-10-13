package ternant.authConfig;

import org.springframework.http.ResponseCookie;

public interface AuthService {
    ResponseCookie signIn(LoginDTO loginDTO);

    ResponseCookie signOut();
}
