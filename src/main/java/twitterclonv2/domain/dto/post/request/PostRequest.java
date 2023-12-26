package twitterclonv2.domain.dto.post.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequest {
    @Length(min = 4,
            message = "El post debe contener al menos 4 o mas caracteres")
    private String content;
}
