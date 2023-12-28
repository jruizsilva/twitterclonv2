package twitterclonv2.business.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.mapper.PostMapper;
import twitterclonv2.business.mapper.UserMapper;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.entity.PostEntity;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {
    public final UserMapper userMapper;

    @Override
    public PostDto toDto(PostEntity postEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM 'at' HH:mm");
        String formattedDate = postEntity.getCreatedAt()
                                         .format(formatter);

        return PostDto.builder()
                      .id(postEntity.getId())
                      .content(postEntity.getContent())
                      .author(userMapper.toDto(postEntity.getAuthor()))
                      .createdAt(formattedDate)
                      .build();
    }
}
