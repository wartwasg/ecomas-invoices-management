package ternant.authConfig;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ternant.errorHandling.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    public AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        ResponseCookie responseCookie = authService.signIn(loginDTO);

        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.SET_COOKIE,responseCookie.toString());

        return new ResponseEntity<ApiResponse>(new ApiResponse("Successful logged in","success"),
                header,
                HttpStatus.OK);
    }
    @PostMapping("/signout")
    public ResponseEntity<?> signOut(){
        ResponseCookie responseCookie = authService.signOut();

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,responseCookie.toString());

        return new ResponseEntity<ApiResponse>(
                new ApiResponse("successful logged out","success"),
                headers,
                HttpStatus.OK);
    }

}
