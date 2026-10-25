package com.projects.JeevanHospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {

    private final JwtAuthenticationFilter jwtFilter;

    // @Lazy वापरल्यामुळे सर्कुलर डिपेंडन्सी पूर्णपणे ब्रेक होते
    public AppConfig(@Lazy JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ३ विविध कामांसाठी टेस्ट युझर्स (क्रेडेंशियल्स)
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();

        UserDetails admin = User.withUsername("ANUBHAV")
                .password(encoder.encode("ANUBHAV"))
                .roles("ADMIN")
                .build();

        UserDetails doctor = User.withUsername("DR_SHARMA")
                .password(encoder.encode("DOC123"))
                .roles("DOCTOR")
                .build();

        UserDetails staff = User.withUsername("STAFF_ROHAN")
                .password(encoder.encode("STAFF123"))
                .roles("STAFF")
                .build();

        return new InMemoryUserDetailsManager(admin, doctor, staff);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 1. Authentication Endpoints
                .requestMatchers("/api/auth/login").permitAll()

                // 2. Billing Invoices Rules
                .requestMatchers(HttpMethod.GET, "/api/billings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers("/api/billings/**").hasAnyAuthority("ROLE_ADMIN")

                // 3. Doctor Profiles Rules
                .requestMatchers(HttpMethod.GET, "/api/doctors/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_STAFF")
                .requestMatchers("/api/doctors/**").hasAnyAuthority("ROLE_ADMIN")

                // 4. Insurance Policies Rules
                .requestMatchers(HttpMethod.GET, "/api/insurances/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers("/api/insurances/**").hasAnyAuthority("ROLE_ADMIN")

                // 5. Medical History Rules
                .requestMatchers("/api/medical-records/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")

                // 6. Patient Profiles Rules
                .requestMatchers(HttpMethod.GET, "/api/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_STAFF")
                .requestMatchers(HttpMethod.POST, "/api/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                // 7. Staff Profiles Rules
                .requestMatchers("/api/staff/**").hasAnyAuthority("ROLE_ADMIN")

                // 8. Ward Rooms Rules
                .requestMatchers(HttpMethod.GET, "/api/rooms/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_STAFF")
                .requestMatchers("/api/rooms/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    @Bean(name = "customAuthManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
   
}
