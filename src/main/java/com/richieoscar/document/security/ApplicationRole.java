package com.richieoscar.document.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.richieoscar.document.security.ApplicationPermission.DOCUMENT_READ;
import static com.richieoscar.document.security.ApplicationPermission.DOCUMENT_WRITE;

public enum ApplicationRole {
    //set up Role permission/authorities
    //Sets.newHashets comes from guava dependency
    ADMIN(Sets.newHashSet(DOCUMENT_READ,DOCUMENT_WRITE)),
    USER(Sets.newHashSet()),
    ADMIN_TRAINEE(Sets.newHashSet(DOCUMENT_READ));

    private final Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities= permissions.stream()
                .map(applicationPermission -> new SimpleGrantedAuthority(applicationPermission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return  authorities;
    }


}
