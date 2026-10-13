package ternant.userModule;

import jakarta.validation.Valid;

public interface UserService {
    UserDTO saveUser(@Valid UserDTO userDTO);
}
