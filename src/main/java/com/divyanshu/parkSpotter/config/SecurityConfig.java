package com.divyanshu.parkSpotter.config;

import com.divyanshu.parkSpotter.filters.JwtAuthenticationFilter;
import com.divyanshu.parkSpotter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

      @Bean
      public AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
          authProvider.setUserDetailsService(userService.userDetailsService());
          authProvider.setPasswordEncoder(passwordEncoder);
          return authProvider;
      }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
          return new JwtAuthenticationFilter(exceptionResolver);
    }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
          return config.getAuthenticationManager();
      }

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf
          .disable()
        ).cors(cors->cors.disable())
        .sessionManagement(session -> session
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(authorize -> authorize
          .requestMatchers(HttpMethod.POST, "/api/v1/signup", "/api/v1/signin").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/v1/test/**").permitAll()
                .requestMatchers("/v3/api-docs/**",
                        "/swagger-ui**",
                        "/swagger-ui/**").permitAll()
          .anyRequest().authenticated()
        )
        .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
      }
}
