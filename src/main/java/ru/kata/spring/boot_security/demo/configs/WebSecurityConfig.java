package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
//    private final PersonDetailsService personDetailsService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(@Lazy UserDetailsService userDetailsService, SuccessUserHandler successUserHandler){
        this.userDetailsService = userDetailsService;
//        this.authProvider = authProvider;
        this.successUserHandler = successUserHandler;
    }
//    public WebSecurityConfig(AuthProviderImpl authProvider, SuccessUserHandler successUserHandler) {
//
//        this.authProvider = authProvider;
//        this.successUserHandler = successUserHandler;
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //congigure spring security
        //config authorithe
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/login","/user/error").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .failureUrl("/auth/login?error")
                .successHandler(successUserHandler) // обработчик успешной аутентификации
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");

//        http
//                .authorizeRequests()
//                .antMatchers("/", "/index").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();


    }

//    Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService  );
    }

    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("Ivan")
                        .password("gghh")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}