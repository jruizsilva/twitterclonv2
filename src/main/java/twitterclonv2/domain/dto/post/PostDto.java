package twitterclonv2.domain.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import twitterclonv2.domain.dto.user.UserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private String createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
}
