package twitterclonv2.domain.dto.user.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @Length(min = 4,
            message = "el nombre debe tener 4 o mas caracteres")
    private String name;
    @Length(min = 4,
            message = "la descripcion debe tener 4 o mas caracteres")
    private String description;
}
