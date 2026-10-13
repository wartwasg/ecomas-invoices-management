package ternant.userModule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ternant.errorHandling.UserAlreadyExists;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User userInDb = userRepository.findByEmail(userDTO.getEmail())
                .orElse(null);

        if(userInDb == null){
            List<Roles> userRoles = new ArrayList<>();
            userDTO.getRoles().forEach(
                    role -> {
                        switch (role){
                            case "TOP_MANAGER":
                                Roles roleTopManager = roleRepository.findByRoleName(AppRole.ROLE_TOP_MANAGER)
                                        .orElse(null);
                                userRoles.add(roleTopManager);
                                break;
                            case "REGULAR_MANAGER":
                                Roles roleRegularManager = roleRepository.findByRoleName(AppRole.ROLE_REGULAR_MANAGER)
                                        .orElse(null);
                                userRoles.add(roleRegularManager);
                                break;
                        }
                    }
            );
            if (userRoles.isEmpty()){
                userRoles.add(roleRepository.findByRoleName(AppRole.ROLE_REGULAR_MANAGER)
                        .orElse(null));
            }
            User userFromAdmin = User.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .invoices(null)
                    .roles(userRoles)
                    .build();
            User savedUser = userRepository.save(userFromAdmin);

            UserDTO savedUserDTO =  modelMapper.map(savedUser, UserDTO.class);

            savedUserDTO.setRoles(userDTO.getRoles());

            savedUserDTO.setPassword(null);

            return savedUserDTO;
        }
        else {
            throw new UserAlreadyExists("User Already Exists");
        }
    }
}
