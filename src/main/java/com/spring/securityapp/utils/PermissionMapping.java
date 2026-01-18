package com.spring.securityapp.utils;

import com.spring.securityapp.entities.enums.Permissions;
import com.spring.securityapp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.spring.securityapp.entities.enums.Permissions.*;
import static com.spring.securityapp.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permissions>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE, USER_CREATE, USER_DELETE)
    );

    public static Set<SimpleGrantedAuthority> grantedAuthorityForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
