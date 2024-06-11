package com.board.demo.config;

import com.board.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    CustomAuthFailureHandler customAuthFailHandler;

    @Autowired
    CustomAuthSuccessHandler customAuthSuccessHandler;

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf()
//                .ignoringAntMatchers("/h2-console/**")
                .disable()
                .authorizeRequests()
//                .antMatchers("/test/board/list").anonymous()
                    .antMatchers("/", "/member/view/signUp", "/member/api/signUp", "/member/view/login").permitAll()
                    .antMatchers("/public/**","/member/view/myPage").hasRole("USER")
                    .antMatchers("/premium/**").hasRole("PREMIUM")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/view/login")
                    .loginProcessingUrl("/member/api/login")
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .successHandler(customAuthSuccessHandler)
                    .failureHandler(customAuthFailHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder.passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/h2-console/**", "/favicon.ico");
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}