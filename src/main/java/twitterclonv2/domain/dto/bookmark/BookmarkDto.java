package twitterclonv2.domain.dto.bookmark;

import lombok.*;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;

@Getter
@ToString
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private Long id;
    private PostDto post;
    private UserDto user;
}
