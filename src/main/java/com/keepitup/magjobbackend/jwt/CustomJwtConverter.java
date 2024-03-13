package com.keepitup.magjobbackend.jwt;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class CustomJwtConverter implements Converter<Jwt, CustomJwt> {

    @Override
    public CustomJwt convert(Jwt source) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        var customJwt = new CustomJwt(source, grantedAuthorityList);
        customJwt.setFirstname(source.getClaimAsString("given_name"));
        customJwt.setLastname(source.getClaimAsString("family_name"));
        return customJwt;
    }
}
