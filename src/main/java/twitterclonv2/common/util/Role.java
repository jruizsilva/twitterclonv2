package twitterclonv2.common.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    USER(Arrays.asList(
            Permission.ADD_COMMENT,
            Permission.REMOVE_COMMENT,
            Permission.LIKE_COMMENT,
            Permission.FIND_ALL_USERS_FOLLOWING_BY_USERNAME,
            Permission.FIND_ALL_FOLLOWERS_BY_USERNAME,
            Permission.ADD_FOLLOWER,
            Permission.REMOVE_FOLLOWER,
            Permission.DELETE_BACKGROUND_IMAGE,
            Permission.DELETE_PROFILE_IMAGE,
            Permission.UPLOAD_PROFILE_IMAGE,
            Permission.UPLOAD_BACKGROUND_IMAGE,
            Permission.FIND_USER_BY_USERNAME,
            Permission.FIND_ALL_POSTS_LIKE_BY_USERNAME,
            Permission.FIND_ALL_POSTS_SAVED_BY_USERNAME,
            Permission.FIND_ALL_POSTS_CREATED_BY_USERNAME,
            Permission.DELETE_USER_BY_USERNAME,
            Permission.ADD_POST_TO_POSTS_SAVED,
            Permission.REMOVE_POST_FROM_POSTS_SAVED,
            Permission.FIND_ALL_POSTS,
            Permission.FIND_ALL_POSTS_BY_USERNAME,
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
            Permission.ADD_COMMENT,
            Permission.REMOVE_COMMENT,
            Permission.LIKE_COMMENT,
            Permission.FIND_ALL_USERS_FOLLOWING_BY_USERNAME,
            Permission.FIND_ALL_FOLLOWERS_BY_USERNAME,
            Permission.ADD_FOLLOWER,
            Permission.REMOVE_FOLLOWER,
            Permission.DELETE_BACKGROUND_IMAGE,
            Permission.DELETE_PROFILE_IMAGE,
            Permission.UPLOAD_PROFILE_IMAGE,
            Permission.UPLOAD_BACKGROUND_IMAGE,
            Permission.FIND_USER_BY_USERNAME,
            Permission.FIND_ALL_POSTS_LIKE_BY_USERNAME,
            Permission.FIND_ALL_POSTS_SAVED_BY_USERNAME,
            Permission.FIND_ALL_POSTS_CREATED_BY_USERNAME,
            Permission.DELETE_USER_BY_USERNAME,
            Permission.ADD_POST_TO_POSTS_SAVED,
            Permission.REMOVE_POST_FROM_POSTS_SAVED,
            Permission.FIND_ALL_POSTS,
            Permission.FIND_ALL_POSTS_BY_USERNAME,
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
