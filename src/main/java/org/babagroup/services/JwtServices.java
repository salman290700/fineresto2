package org.babagroup.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.babagroup.models.Role;
import org.babagroup.models.User;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.HashSet;
import java.util.Set;
@Singleton
public class JwtServices {
    @Inject
    JsonWebToken jsonWebToken;
    public String generateJwt(User user) {
        Set<String> groups = new HashSet<>();
        for(Role role: user.getRole()) {
            groups.add(role.getName());
        }
        long duration = System.currentTimeMillis() + 3600;
        return Jwt.issuer("fineresto")
                .subject(user.getEmail())
                .groups(groups)
                .expiresAt(duration)
                .sign();
    }

    public String getUsername(String jwt) {
        return jsonWebToken.getName();
    }
}
