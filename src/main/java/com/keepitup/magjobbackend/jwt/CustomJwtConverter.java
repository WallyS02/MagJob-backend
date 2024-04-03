package com.keepitup.magjobbackend.jwt;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomJwtConverter implements Converter<Jwt, CustomJwt> {

    @Override
    public CustomJwt convert(Jwt source) {
        List<GrantedAuthority> grantedAuthorityList = extractAuthorities(source);
        var customJwt = new CustomJwt(source, grantedAuthorityList);
        customJwt.setFirstname(source.getClaimAsString("given_name"));
        customJwt.setLastname(source.getClaimAsString("family_name"));
        customJwt.setEmail(source.getClaimAsString("email"));
        customJwt.setExternalId(source.getClaimAsString("sub"));
        return customJwt;
    }

    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        var authorities = new ArrayList<GrantedAuthority>();
        var realm_access = jwt.getClaimAsMap("realm_access");
        if (realm_access != null && realm_access.get("roles") != null) {
            var roles = realm_access.get("roles");
            if (roles instanceof List l) {
                l.forEach(role ->
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role))
                );
            }
        }

        return authorities;
    }
}
