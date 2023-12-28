package twitterclonv2.domain.dto.post;

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
    private UserDto author;
}
