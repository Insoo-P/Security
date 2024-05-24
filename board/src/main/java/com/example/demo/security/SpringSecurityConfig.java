package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

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
//                    .antMatchers("/").permitAll()
                    .antMatchers("/view/signUp", "/view/login", "/api/login", "/api/signUp").anonymous()
//                    .antMatchers("/view/signUp", "/view/login", "/api/login", "/api/signUp").not().fullyAuthenticated()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/view/login")
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .defaultSuccessUrl("/", true)
                    .failureHandler(customFailHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/view/login")
//                    .logoutSuccessHandler((request, response, authentication) -> {
//                        response.sendRedirect("/view/login");
//                    })
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
//                .authenticationEntryPoint();
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

    private AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        return customAccessDeniedHandler;
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }
}