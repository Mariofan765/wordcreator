package com.mariofan.wordcreator.configurations;

import com.mariofan.wordcreator.views.LoginView;
import com.mariofan.wordcreator.views.LogoutView;
import com.vaadin.flow.spring.security.NavigationAccessControlConfigurer;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> {
                    configurer.loginView(LoginView.class, "/wordcreator/login.html");
                });
        return http.build();
    }


    @Bean
    public UserDetailsManager userDetailsManager() {
        LoggerFactory.getLogger(SecurityConfig.class)
                .warn("NOT FOR PRODUCTION: Using in-memory user details manager!");
        var user = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();
        var admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}