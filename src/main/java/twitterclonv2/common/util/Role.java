package twitterclonv2.common.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    USER(Arrays.asList(
            Permission.FIND_ALL_USERS,
            Permission.CREATE_ONE_POST,
            Permission.GET_USER_AUTHENTICATED,
            Permission.UPDATE_USER)),
    ADMINISTRATOR(Arrays.asList(
            Permission.FIND_ALL_USERS,
            Permission.CREATE_ONE_POST,
            Permission.GET_USER_AUTHENTICATED,
            Permission.UPDATE_USER));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
