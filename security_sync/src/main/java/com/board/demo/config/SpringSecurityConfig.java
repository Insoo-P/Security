package com.board.demo.config;

import com.board.demo.security.CustomAuthFailureHandler;
import com.board.demo.security.CustomAuthSuccessHandler;
import com.board.demo.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthFailureHandler customAuthFailHandler;

    @Autowired
    CustomAuthSuccessHandler customAuthSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable()
                .authorizeRequests()
//                .antMatchers("/view/signUp", "/view/login").anonymous()
                    .antMatchers("/", "/member/view/signUp", "/member/api/signUp", "/favicon.ico", "/h2-console/**").permitAll()
//                    .antMatchers("/boardP**").hasRole("ROLE_PREMIUM")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/css/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/view/login")
                    .loginProcessingUrl("/member/api/login")
                    .usernameParameter("id")
                    .passwordParameter("pw")
//                    .defaultSuccessUrl("/")
                    .successHandler(customAuthSuccessHandler)
                    .failureHandler(customAuthFailHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/");
//                    .logoutSuccessHandler((request, response, authentication) -> {
//                        response.sendRedirect("/view/login");
//                    })
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(new CustomAccessDeniedHandler());
//                .authenticationEntryPoint();
//                    response.sendRedirect("/");
//                });
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    private AccessDeniedHandler accessDeniedHandler() {
//        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
//        return customAccessDeniedHandler;
//    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }
}