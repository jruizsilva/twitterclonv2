package twitterclonv2.domain.dto.like.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LikeRequest {
    @NotNull(message = "el post no debe ser nulo")
    private Long postId;
    @NotBlank(message = "El post no debe estar vacio")
    @Length(min = 4,
            message = "El post debe contener al menos 4 o mas caracteres")
    private String username;
}
