package com.blogger.blog.blog_app_apis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogger.blog.blog_app_apis.security.CustomUserDetailService;
import com.blogger.blog.blog_app_apis.security.JwtAuthenticationEntryPoint;
import com.blogger.blog.blog_app_apis.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * UserDetails normalUser = User.withUsername("User")
     * .password(passwordEncoder().encode("password"))
     * .roles("NORMAL")
     * .build();
     * 
     * UserDetails adminUser = User.withUsername("Admin")
     * .password(passwordEncoder().encode("password"))
     * .roles("ADMIN")
     * .build();
     * 
     * return new InMemoryUserDetailsManager(normalUser, adminUser);
     * }
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());

        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*
     * @Bean
     * public FilterRegistrationBean<UrlBasedCorsConfigurationSource> coresFilter()
     * {
     * UrlBasedCorsConfigurationSource source = new
     * UrlBasedCorsConfigurationSource();
     * 
     * CorsConfiguration corsConfiguration = new CorsConfiguration();
     * corsConfiguration.setAllowCredentials(true);
     * corsConfiguration.addAllowedOriginPattern("*");
     * corsConfiguration.addAllowedHeader("Authorization");
     * corsConfiguration.addAllowedHeader("Content-Type");
     * corsConfiguration.addAllowedMethod("*"); // Allows all HTTP methods
     * source.registerCorsConfiguration("/**", corsConfiguration);
     * 
     * FilterRegistrationBean<UrlBasedCorsConfigurationSource> bean = new
     * FilterRegistrationBean<>(source);
     * bean.setOrder(0);
     * return bean;
     * }
     */
}