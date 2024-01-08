package twitterclonv2.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import twitterclonv2.domain.dto.post.PostDto;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PostDto> postsCreated;
}
