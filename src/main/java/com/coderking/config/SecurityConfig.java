package com.coderking.config;

import com.coderking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/contests").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contests/**","/api/courses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/contest/*/join").authenticated()
                        .requestMatchers("/api/auth/**", "/api/public/**", "/api/profile/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                // ✅ new style for JWT
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                // ✅ new style for session management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }

    @Autowired
    private JwtUtil jwtUtil;
    // ✅ Add your JwtDecoder bean here
    @Bean
    public JwtDecoder jwtDecoder() {
        // For symmetric secret (HS256)
        SecretKeySpec secretKey = new SecretKeySpec(jwtUtil.getSecret().getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();

        // For RSA public key (RS256)
        // RSAPublicKey publicKey = ... load from file or keystore
        // return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }


    // ✅ Define the PasswordEncoder bean here
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

