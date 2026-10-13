package ternant.authConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ternant.config.JwtUtil;
import ternant.config.UserDetailsImpl;
import ternant.config.UserDetailsServiceImpl;
import ternant.errorHandling.WrongUsernameOrPasswordException;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public ResponseCookie signIn(LoginDTO loginDTO) {
        Authentication authentication;
        try{
            UserDetailsImpl userDetailsFromDB = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginDTO.getEmail());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getPassword()
            );

            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtil.generateCookieFromJwtToken(userDetailsFromDB);

        } catch (Exception e) {
            throw new WrongUsernameOrPasswordException("Wrong username or password");
        }
    }

    @Override
    public ResponseCookie signOut() {
        return jwtUtil.invalidateCookie();
    }
}
