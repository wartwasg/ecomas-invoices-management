package ternant.authConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ternant.config.UserDetailsImpl;
import ternant.errorHandling.UserDoesNotAvailable;
import ternant.userModule.User;
import ternant.userModule.UserRepository;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;

    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(()->
                new UserDoesNotAvailable("User does not available please login")
        );
    }
}
