package com.example.web.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/v2/api-docs").hasRole("astrav")
                .antMatchers("/swagger-ui.html").hasRole("astrav")
                .antMatchers("/swagger-ui.html/**").hasRole("astrav")
                .antMatchers("/swagger-resources/**").hasRole("astrav")
                .antMatchers("/webjars/springfox-swagger-ui/**").hasRole("astrav")
                .antMatchers("/api/departmentSalary/**").hasAnyRole("USER_Department", "astrav")
                .antMatchers("/api/design/**").hasAnyRole("USER_Design", "astrav")
                .antMatchers("/api/human/**").hasAnyRole("USER_Employee", "astrav")
                .antMatchers("/api/stock/**").hasAnyRole("USER_Storage", "astrav")
                .antMatchers("/api/testers/**").hasAnyRole("USER_Test", "astrav")
                .antMatchers("/api/workshop/**").hasAnyRole("USER_Workshop", "astrav")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/ws/**").permitAll()

                .antMatchers("/home.html").permitAll()
                .antMatchers("/login.html").permitAll()

                .antMatchers("/department.html").hasAnyRole("USER_Department")
                .antMatchers("/design.html").hasAnyRole("USER_Design")
                .antMatchers("/employee.html").hasAnyRole("USER_Employee")
                .antMatchers("/storage.html").hasAnyRole("USER_Storage")
                .antMatchers("/tester.html").hasAnyRole("USER_Test")
                .antMatchers("/workshop.html").hasAnyRole("USER_Workshop")
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login.html").permitAll()
                    .loginProcessingUrl("/login").permitAll()
                    .defaultSuccessUrl("/default")
                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/home.html")
                    .deleteCookies();

    }
}