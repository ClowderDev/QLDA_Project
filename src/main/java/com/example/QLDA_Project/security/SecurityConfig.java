package com.example.QLDA_Project.security;

import com.example.QLDA_Project.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;


    private static final List<String> PUBLIC_URLS = Arrays.asList(
        "/",
        "/login",
        "/error",
        "/register",
        "/api/auth/**",
        "/api/public/**",
        "/jobs",
        "/jobs/**",
        "/companies",
        "/companies/**"
    );

    private static final List<String> STATIC_RESOURCES = Arrays.asList(
        "/css/**",
        "/js/**",
        "/images/**",
        "/webjars/**",
        "/static/**"
    );

    private static final class RoleEndpoints {
        static final String ADMIN = "/api/admin/**";
        static final String RECRUITER = "/api/recruiter/**";
        static final String HR_STAFF = "/api/hr/**";
        static final String CV_STAFF = "/api/cv/**";
        static final String CANDIDATE = "/api/candidate/**";
    }

    public SecurityConfig(@Lazy CustomUserDetailsService userDetailsService,
                         SecurityContextRepository securityContextRepository,
                         CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            )
            .securityContext(securityContext -> securityContext
                .securityContextRepository(securityContextRepository)
                .requireExplicitSave(true)  
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .loginProcessingUrl("/api/auth/login") 
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
            )
            .authorizeHttpRequests(auth -> {

                PUBLIC_URLS.forEach(url -> 
                    auth.requestMatchers(url).permitAll()
                );  
                auth.requestMatchers("/admin/index").hasRole("ADMIN")
                    .requestMatchers("/hr/dashboard", "/hr/profile").hasRole("HR_STAFF")
                    .requestMatchers("/user/dashboard").hasRole("CANDIDATE");
                auth.requestMatchers(RoleEndpoints.ADMIN).hasRole("ADMIN")
                    .requestMatchers(RoleEndpoints.HR_STAFF).hasRole("HR_STAFF")
                    .requestMatchers(RoleEndpoints.RECRUITER).hasRole("RECRUITER")
                    .requestMatchers(RoleEndpoints.CV_STAFF).hasRole("CV_STAFF")
                    .requestMatchers(RoleEndpoints.CANDIDATE).hasRole("CANDIDATE")
                    .anyRequest().authenticated();
            })
            .addFilterBefore(new SecurityContextHolderFilter(securityContextRepository), UsernamePasswordAuthenticationFilter.class)
            .rememberMe(remember -> remember
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(86400) // 24 tiếng
                .userDetailsService(userDetailsService)
            );

        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(STATIC_RESOURCES.toArray(new String[0]));
    }
}