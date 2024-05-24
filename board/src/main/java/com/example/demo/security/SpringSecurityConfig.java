package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomFailHandler customFailHandler;

    @Autowired
    LoginIdPwValidator loginIdPwValidator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                    .antMatchers().permitAll()
                    .antMatchers("/view/signUp", "/api/login", "/api/signUp").anonymous()
//                    .antMatchers("/view/login").not().fullyAuthenticated()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/view/login")
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .defaultSuccessUrl("/", true)
//                     .failureHandler(customFailHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/view/login");
//                    .logoutSuccessHandler((request, response, authentication) -> {
//                        response.sendRedirect("/view/login");
//                    })
//                .and();
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.sendRedirect("/");
//                });
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginIdPwValidator)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }
}