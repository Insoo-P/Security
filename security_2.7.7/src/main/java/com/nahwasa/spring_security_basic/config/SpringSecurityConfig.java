package com.nahwasa.spring_security_basic.config;

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
    LoginIdPwValidator loginIdPwValidator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF 보호 비활성화
                .authorizeRequests() // 요청에 대한 권한 설정 시작
                .antMatchers("/view/signUp",
                        "/action/signUp").permitAll()
                .anyRequest().authenticated() // 그 외의 요청은 인증된 사용자에게만 허용
                .and()
                .formLogin() // 폼 기반 로그인 설정
                    .loginPage("/view/login") // 로그인 페이지의 URL을 "/view/login"으로 설정
                    .loginProcessingUrl("/loginProc") // 로그인 처리 URL을 "/loginProc"으로 설정
                    .usernameParameter("id") // 사용자 이름 매개변수를 "id"로 설정
                    .passwordParameter("pw") // 비밀번호 매개변수를 "pw"로 설정
                    .defaultSuccessUrl("/view/loginSuccess", true) // 로그인 성공 후 기본적으로 이동할 URL을 "/view/loginSuccess"로 설정
                    .permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .and()
                .logout(); // 로그아웃 설정
//                    .logoutUrl("/logout") // 로그아웃 처리 URL (기본값은 /logout)
//                    .logoutSuccessUrl("/view/login") // 로그아웃 성공 후 이동할 URL
//                    .invalidateHttpSession(true) // 로그아웃 시 세션 무효화 (기본값은 true)
//                    .deleteCookies("JSESSIONID") // 로그아웃 시 특정 쿠키 삭제
//                    .permitAll(); // 로그아웃 관련 URL 접근 권한 허용
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/js/**","/static/css/**","/static/img/**","/static/frontend/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginIdPwValidator);
    }
}