package ternant.authConfig;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    @NotBlank
    @Email(message = "Invalid Email")
    private String email;

    @Size(min = 6,max = 12,message = "Password length cannot be less than 6 or more than 12")
    private String password;
}
