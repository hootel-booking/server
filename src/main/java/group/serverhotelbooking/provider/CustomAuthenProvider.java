package group.serverhotelbooking.provider;

import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity userEntity = loginRepository.findByEmail(username);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {

                List<GrantedAuthority> roles = new ArrayList<>();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userEntity.getRoleEntity().getName());
                roles.add(grantedAuthority);

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, userEntity.getPassword(), roles);

                SecurityContextHolder.getContext().setAuthentication(token);
                return token;

            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
