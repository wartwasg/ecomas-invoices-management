package ternant.userModule;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ternant.errorHandling.ApiResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('TOP_MANAGER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
          UserDTO savedUser = userService.saveUser(userDTO);

          return new ResponseEntity<UserDTO>(savedUser, HttpStatus.CREATED);

    }
}
