package ternant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ternant.userModule.User;
import ternant.userModule.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userInDb = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return UserDetailsImpl.build(userInDb);
    }
}
