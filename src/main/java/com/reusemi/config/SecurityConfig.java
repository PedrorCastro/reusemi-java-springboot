package com.reusemi.config;

import com.reusemi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configurações básicas
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())

                // Autorizações
                .authorizeHttpRequests(auth -> auth
                        // Recursos estáticos
                        .requestMatchers(
                                "/css/**", "/js/**", "/img/**", "/images/**",
                                "/webjars/**", "/fonts/**", "/favicon.ico"
                        ).permitAll()

                        // Páginas públicas
                        .requestMatchers(
                                "/", "/home", "/index", "/inicio",
                                "/login", "/cadastro", "/register",
                                "/sobre", "/como-funciona", "/contato",
                                "/products", "/api/**", "/error", "perfil"
                        ).permitAll()

                        // Admin apenas
                        .requestMatchers("/admin/**", "/exportar-usuarios").hasRole("ADMIN")

                        // Demais requisições
                        .anyRequest().authenticated()
                )

                // Login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            System.out.println("✅ LOGIN SUCESSO - Usuário: " + authentication.getName());
                            response.sendRedirect("/");
                        })
                        .failureHandler((request, response, exception) -> {
                            System.out.println("❌ LOGIN FALHOU - Usuário: " + request.getParameter("username"));
                            System.out.println("❌ Erro: " + exception.getMessage());
                            response.sendRedirect("/login?error=true");
                        })
                )

                // Logout
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout=true")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                // Lembrar de mim
                .rememberMe(rm -> rm
                        .key("reusemi-secure-key")
                        .tokenValiditySeconds(86400) // 24 horas
                        .userDetailsService(customUserDetailsService)
                )

                // Tratamento de erros
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/acesso-negado")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }
}