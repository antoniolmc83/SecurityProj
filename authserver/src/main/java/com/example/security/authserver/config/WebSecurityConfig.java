package com.example.security.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@Order(SecurityProperties.BASIC_AUTH_ORDER-2)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig() {//BCryptPasswordEncoder bCryptPasswordEncoder
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Autowired
//    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        // @formatter:
//        String username = "user1";
//        String encodedPassword = bCryptPasswordEncoder.encode("pass");
//        List<SimpleGrantedAuthority> authList = new ArrayList<>();
//        authList.add(new SimpleGrantedAuthority("ADMIN"));
//
//        User user = new User(username, encodedPassword, authList);
//        auth.inMemoryAuthentication()
//                .passwordEncoder(bCryptPasswordEncoder)
//                .withUser(user);
////        auth.inMemoryAuthentication()
////                .passwordEncoder(bCryptPasswordEncoder)
////                .withUser("john").password(bCryptPasswordEncoder.encode("123")).roles("USER").and()
////                .withUser("tom").password(bCryptPasswordEncoder.encode("111")).roles("ADMIN").and()
////                .withUser("user1").password(bCryptPasswordEncoder.encode("pass")).roles("USER").and()
////                .withUser("admin").password(bCryptPasswordEncoder.encode("nimda")).roles("ADMIN");
//    }// @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());//.passwordEncoder(bCryptPasswordEncoder);


    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1").password("pass").roles("ADMIN").build());
        manager.createUser(User.withUsername("app1").password("passapp").roles("USER").build());
        return manager;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/oauth/token/revokeById/**").permitAll()
                .antMatchers("/tokens/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll();
                //.and().csrf().disable();
        // @formatter:on
    }


    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}