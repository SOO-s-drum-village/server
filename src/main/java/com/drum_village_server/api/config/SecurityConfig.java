package com.drum_village_server.api.config;


import com.drum_village_server.api.config.filter.EmailPasswordAuthFilter;
import com.drum_village_server.api.config.handler.Http401Handler;
import com.drum_village_server.api.config.handler.Http403Handler;
import com.drum_village_server.api.config.handler.LoginFailHandler;
import com.drum_village_server.api.config.handler.LoginSuccessHandler;
import com.drum_village_server.api.domain.User;
import com.drum_village_server.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final ObjectMapper objectMapper;
  private final UserRepository userRepository;

  // security ignore
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/favicon.ico", "/error");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeHttpRequests(request -> request
        .requestMatchers("/test*").permitAll()
        .requestMatchers("/lectures").permitAll()
        .requestMatchers("/enums").permitAll()
        .requestMatchers("/auth/*").permitAll()
        .requestMatchers("/docs/*").permitAll()
        .requestMatchers("/users/*").hasRole("USER")
        .requestMatchers("/lectures/{lectureId}").hasRole("USER")
      )
      .addFilterBefore(emailPasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
      .logout(logout -> logout
        .logoutUrl("/auth/logout")
        .logoutSuccessHandler((request, response, authentication) -> {
          response.setStatus(HttpServletResponse.SC_OK);
        })
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
      )
      .exceptionHandling(e -> {
        e.accessDeniedHandler(new Http403Handler(objectMapper));
        e.authenticationEntryPoint(new Http401Handler(objectMapper));
      })
      .csrf(AbstractHttpConfigurer::disable)
      .build();
  }

  @Bean
  public EmailPasswordAuthFilter emailPasswordAuthFilter() {
    EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/auth/login/email",objectMapper);
    filter.setAuthenticationManager(authenticationManager());
    filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
    filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
    filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

    return filter;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService(userRepository));
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> {
      User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

      return new UserPrincipal(user);
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new SCryptPasswordEncoder(16, 8, 1, 32, 64);
  }
}
