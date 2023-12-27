package twitterclonv2.domain.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotBlank(message = "el username no debe estar vacio")
    @Length(min = 4,
            message = "el username debe tener 4 o mas caracteres")
    private String username;
    @NotBlank(message = "la contraseña no debe estar vacia")
    @Length(min = 4,
            message = "la contraseña debe tener 4 o mas caracteres")
    private String password;
    @NotBlank(message = "el nombre no debe estar vacio")
    @Length(min = 4,
            message = "el nombre debe tener 4 o mas caracteres")
    private String name;
}
