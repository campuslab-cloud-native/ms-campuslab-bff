package bff.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        List<String> roles = jwt.getClaimAsStringList("roles");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (roles == null || roles.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        } else {
            roles.forEach(role ->
                    authorities.add(
                            new SimpleGrantedAuthority("ROLE_" + role)
                    )
            );
        }

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getSubject()
        );
    }
}