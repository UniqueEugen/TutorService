package kurenkov.tutorservice.config;

import kurenkov.tutorservice.config.headers.CustomHeaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriter;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public HeaderWriter headerWriter() {
        return new CustomHeaderWriter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
                new SecurityContextServerLogoutHandler(), new WebSessionServerLogoutHandler()
        );*/

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/photo/**").permitAll()
                        .requestMatchers("/registration/**").anonymous()
                        .requestMatchers("/profile/**","/account/tutor/**", "/account/seeker/**", "/getcurrentpage").authenticated()
                        /*.requestMatchers("/account/tutor/**").hasRole("TUTOR")
                        .requestMatchers("/account/seeker/**").hasRole("SEEKER")*/
                        .requestMatchers(HttpMethod.GET, "/forum").permitAll()
                        .requestMatchers(HttpMethod.POST, "/forum/**").authenticated()
                        .requestMatchers("/static/pictures/favicon.ico").permitAll()
                        .requestMatchers("/**").permitAll()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .formLogin(form ->form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home").permitAll()
                )
                .logout((logout)-> logout
                        .logoutSuccessUrl("/home").permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/404")
                )
                .headers(headers -> headers
                        .addHeaderWriter(headerWriter()))
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
