package group.serverhotelbooking.security;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.filter.JwtFilter;
import group.serverhotelbooking.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    CustomAuthenProvider customAuthenProvider;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.POST, "/modify/**").permitAll()
                .antMatchers(HttpMethod.POST,"/rooms/add").hasRole(Constant.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE,"/rooms/delete").hasRole(Constant.ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT,"/rooms/id={id}").hasRole(Constant.ROLE_ADMIN)
                .antMatchers(HttpMethod.GET,"/users").hasRole(Constant.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE,"/users/**").hasRole(Constant.ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT,"/users/id={id}").hasRole(Constant.ROLE_ADMIN)
                .antMatchers("/sizes").hasRole(Constant.ROLE_ADMIN)
                .antMatchers("/types").hasRole(Constant.ROLE_ADMIN)
                .antMatchers("/roles/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/blog/**").permitAll()
                .antMatchers("/status").permitAll()
                .anyRequest().authenticated()
            .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}