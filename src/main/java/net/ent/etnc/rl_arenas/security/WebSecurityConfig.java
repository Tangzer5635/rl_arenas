package net.ent.etnc.rl_arenas.security;

import net.ent.etnc.rl_arenas.security.jwt.AuthJwtFilter;
import net.ent.etnc.rl_arenas.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration Spring Security : stateless JWT, CSRF désactivé, RBAC par méthode.
 *
 * <p>Règles d'accès :
 * <ul>
 *   <li>{@code /api/v1/auth/**} — public (login, refresh, logout).</li>
 *   <li>{@code GET /api/v1/certifications/**} et {@code GET /api/v1/sessions/**} — public
 *       (le service filtre les données selon l'authentification via {@code SecurityContextHolder}).</li>
 *   <li>{@code /actuator/health} — public (sondes de santé).</li>
 *   <li>Tout le reste — authentifié ; les rôles fins sont vérifiés par {@code @PreAuthorize}.</li>
 * </ul>
 *
 * <p>{@code @EnableMethodSecurity} active le support de {@code @PreAuthorize} sur les controllers.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthJwtFilter authJwtFilter;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthJwtFilter authJwtFilter) {
        this.userDetailsService = userDetailsService;
        this.authJwtFilter = authJwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/teams/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/matchs/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (request, response, e) -> response.sendError(401, "Non authentifié"))
                )
                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
