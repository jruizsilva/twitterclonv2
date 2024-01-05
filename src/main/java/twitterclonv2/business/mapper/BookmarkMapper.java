package twitterclonv2.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.*;
import twitterclonv2.domain.dto.bookmark.BookmarkDto;
import twitterclonv2.domain.entity.BookmarkEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PostMapper.class, UserMapper.class})
public interface BookmarkMapper {
    @Named("bookmarkEntityToDtoWithoutChildren")
    @Mapping(target = "post",
             qualifiedByName = "postEntityToDtoWithoutChildren")
    @Mapping(target = "user",
             qualifiedByName = "userEntityToDtoWithoutChildren")
    BookmarkDto bookmarkEntityToDtoWithoutChildren(BookmarkEntity bookmarkEntity);

    @IterableMapping(qualifiedByName = "bookmarkEntityToDtoWithoutChildren")
    List<BookmarkDto> bookmarkEntityListToDtoListWithoutChildren(List<BookmarkEntity> bookmarkEntityList);
}
