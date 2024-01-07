package twitterclonv2.common.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    USER(Arrays.asList(
            Permission.GET_BOOKMARKED_POSTS_BY_USERNAME,
            Permission.FIND_ALL_BOOKMARKS_BY_USERNAME,
            Permission.REMOVE_BOOKMARK,
            Permission.ADD_BOOKMARK,
            Permission.FIND_ALL_POSTS,
            Permission.FIND_ALL_POSTS_OF_CURRENT_USER,
            Permission.DELETE_LIKE,
            Permission.CREATE_LIKE,
            Permission.DELETE_POST,
            Permission.UPDATE_POST,
            Permission.SEARCH_USERS,
            Permission.FIND_ALL_USERS,
            Permission.CREATE_ONE_POST,
            Permission.GET_USER_AUTHENTICATED,
            Permission.UPDATE_USER)),
    ADMINISTRATOR(Arrays.asList(
            Permission.GET_BOOKMARKED_POSTS_BY_USERNAME,
            Permission.FIND_ALL_BOOKMARKS_BY_USERNAME,
            Permission.REMOVE_BOOKMARK,
            Permission.ADD_BOOKMARK,
            Permission.FIND_ALL_POSTS,
            Permission.FIND_ALL_POSTS_OF_CURRENT_USER,
            Permission.DELETE_LIKE,
            Permission.CREATE_LIKE,
            Permission.DELETE_POST,
            Permission.UPDATE_POST,
            Permission.SEARCH_USERS,
            Permission.FIND_ALL_USERS,
            Permission.CREATE_ONE_POST,
            Permission.GET_USER_AUTHENTICATED,
            Permission.UPDATE_USER));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
