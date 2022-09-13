package br.com.fiap.epictaskapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        http.httpBasic()
            .and()
            .authorizeHttpRequests()
                // .antMatchers(HttpMethod.GET, "/api/task/**").permitAll()
                // .antMatchers(HttpMethod.GET, "/api/user/**").permitAll()
                // .antMatchers(HttpMethod.POST, "/api/task").authenticated()
                // .antMatchers(HttpMethod.POST, "/api/user").authenticated()
                .anyRequest().permitAll()
            .and()
                .csrf().disable()
        ;        
        return http.build();
    }

    @Bean
    public UserDetailsService users(){
        UserDetails user = User.builder()
            .username("admin")
            .password("$2a$12$cJXNMSFA0xgB1rVplrSG/OAXbG/MZqIdoW7pJcHmAOFosGXuA5yC6")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
