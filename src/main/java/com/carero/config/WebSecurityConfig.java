package com.carero.config;

import com.carero.config.jwt.JwtAccessDeniedHandler;
import com.carero.config.jwt.JwtAuthenticationEntryPoint;
import com.carero.config.jwt.JwtSecurityConfig;
import com.carero.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/v2/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
            .and()
                // ????????? ???????????? ????????? ?????? ?????????????????? Stateless ??????
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // csrf ???????????? ?????? + exceptionHandling ??????
            .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/mails/**").permitAll()
                .antMatchers("/files/**").permitAll()
                .antMatchers(HttpMethod.GET,"/recruits/**").permitAll()
                .antMatchers(HttpMethod.GET,"/resumes/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}
