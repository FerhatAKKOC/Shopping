package com.example.demo.config;

import com.example.demo.constant.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "static/css", "static/js").permitAll()            // static files
                .antMatchers("/admin").hasRole(UserRoles.ADMIN)
                .antMatchers("/customer").hasRole(UserRoles.CUSTOMER)
                .antMatchers("/carts/**").hasAnyRole(UserRoles.CUSTOMER)
                .and().formLogin();
    }

    /*
        Swagger Security Configurations
     */
    @Override
    public void init(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v3/api-docs",
                "/swagger-ui.html",
                "/swagger-ui/**");
        super.init(web);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}