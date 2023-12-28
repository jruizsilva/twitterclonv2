package twitterclonv2.common.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    USER(Arrays.asList(Permission.FIND_ALL_PRODUCTS,
                       Permission.SAVE_ONE_POST,
                       Permission.GET_USER_AUTHENTICATED,
                       Permission.FIND_ALL_POSTS)),
    ADMINISTRATOR(Arrays.asList(Permission.FIND_ALL_PRODUCTS,
                                Permission.SAVE_ONE_PRODUCT,
                                Permission.SAVE_ONE_USER,
                                Permission.SAVE_ONE_POST,
                                Permission.GET_USER_AUTHENTICATED,
                                Permission.FIND_ALL_POSTS));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
