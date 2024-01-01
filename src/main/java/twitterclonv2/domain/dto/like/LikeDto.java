package twitterclonv2.domain.dto.like;

import lombok.*;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private Long id;
    private PostDto post;
    private UserDto user;
}
