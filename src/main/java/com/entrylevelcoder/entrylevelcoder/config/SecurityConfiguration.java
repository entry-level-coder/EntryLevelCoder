package com.entrylevelcoder.entrylevelcoder.config;

import com.entrylevelcoder.entrylevelcoder.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                /* User Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/") // user's home page, it can be any URL
                .permitAll()

                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/") // append a query string value

                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(

                        // only authenticated users can create ads
                        "/posts/{id}/update", "/company/profile", "/users/profile", "users/{id}/edit",
                        "/users/{id}/update", "company/{id}/edit", "/company/{id}/update", "/users/{id}/delete", "/company/{id}/delete", "/posts"// only authenticated users can edit ads

                )
                .authenticated()

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(

                        "/", "/posts", "/posts/create", "/users/signup", "/company/signup", "/json", "/error/**", "/css/**",
                        "/js/**", "/images/**", "/users/login", "/aboutus", "/contactus", "/team", "/templates/partials/navbar"

                ) // anyone can see home, the post pages, and sign ups
                .permitAll();


        return http.build();
    }


}