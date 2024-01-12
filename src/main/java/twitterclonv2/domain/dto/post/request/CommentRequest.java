package twitterclonv2.domain.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentRequest {
    @NotBlank(message = "El comentario no debe estar vacio")
    @Length(min = 2,
            message = "El post debe contener al menos 2 o mas caracteres")
    private String content;
}
