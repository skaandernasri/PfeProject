package tn.temporise.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.temporise.infrastructure.security.utils.JwtRequestFilter;
import tn.temporise.application.service.CustomUserDetailsService;

import java.io.IOException;
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    @Autowired
    @Lazy
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    @Lazy
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    @Lazy
    private final JwtConfig jwtConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AccessDeniedHandler accessDeniedHandler) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/oauth2/**",
                                "/v1/auth/signup",
                                "/v1/auth/signin",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        //.requestMatchers(HttpMethod.POST,"/tempo-rise/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth/refresh").authenticated()
                        .requestMatchers(HttpMethod.GET, "/v1/produits/**","/v1/categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/v1/produits/**","/v1/categories/**").hasAnyAuthority("ADMIN","GESTIONNAIRE")
                        .requestMatchers(HttpMethod.PUT, "/v1/produits/**","/v1/categories/**").hasAnyAuthority("ADMIN","GESTIONNAIRE")
                        .requestMatchers(HttpMethod.DELETE,"/v1/produits/**","/v1/categories/**").hasAnyAuthority("ADMIN","GESTIONNAIRE")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler) // Use custom access denied handler
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {
                                    try {
                                        jwt
                                                .decoder(jwtConfig.jwtDecoder(jwtConfig.rsaPublicKey()));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        ))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/v1/auth/signin")
                        .defaultSuccessUrl("/swagger-ui/index.html") // example redirect after login success
                        .failureUrl("/login?error=true")
                )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authenticationProvider(authenticationProvider()) // Make sure it's correctly implemented
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Custom filter

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
