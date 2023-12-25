package twitterclonv2.common.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    CUSTOMER(Arrays.asList(Permission.READ_ALL_PRODUCTS)),
    ADMINISTRATOR(Arrays.asList(Permission.READ_ALL_PRODUCTS,
                                Permission.SAVE_ONE_PRODUCT,
                                Permission.SAVE_ONE_ADMINISTRATOR));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
