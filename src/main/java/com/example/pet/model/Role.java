package com.example.pet.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Authorities.READ)),
    ADMIN(Set.of(Authorities.READ, Authorities.WRITE));

    private final Set<Authorities> authorities;

    Role(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Set<Authorities> getPermissions() {
        return authorities;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(authorities -> new SimpleGrantedAuthority(authorities.getAuthorities()))
                .collect(Collectors.toSet());
    }
}
