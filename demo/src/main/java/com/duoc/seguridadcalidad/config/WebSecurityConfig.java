package com.duoc.seguridadcalidad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracion de seguridad centralizada en el paquete config.
 * Se movio desde la raiz del paquete para separar responsabilidades por capa.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // Solo se exponen como publicas las rutas pedidas por la actividad.
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                // Pacientes y citas quedan privadas con control por rol.
                .requestMatchers("/pacientes/**", "/citas/**")
                    .hasAnyRole("ADMIN", "VETERINARIO", "RECEPCION")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                // Se fuerza redireccion a home para un flujo consistente tras autenticacion.
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    @Description("In memory users for week 1 activity")
    public UserDetailsService users() {
        // Usuarios en memoria por alcance de actividad (sin persistencia en BD).
        //* Se podría mejorar intengrando una BD, pero la entrega no lo solicita.
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails veterinario = User.builder()
                .username("vet")
                .password(passwordEncoder().encode("vet123"))
                .roles("VETERINARIO")
                .build();

        UserDetails recepcion = User.builder()
                .username("recep")
                .password(passwordEncoder().encode("recep123"))
                .roles("RECEPCION")
                .build();

        return new InMemoryUserDetailsManager(admin, veterinario, recepcion);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt se usa para evitar credenciales en texto plano aun en entorno educativo.
        return new BCryptPasswordEncoder();
    }
}
