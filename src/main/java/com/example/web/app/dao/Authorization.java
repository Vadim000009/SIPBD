package com.example.web.app.dao;

import com.example.web.app.api.request.ByEmailRequest;
import com.example.web.app.dao.model.AuthorizationUser;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Authorization implements UserDetailsService {
        private final Main main;

        public Authorization(Main main) {
            this.main = main;
        }

        @Override
        public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            AuthorizationUser userFromDb = ByEmailRequest.selectUserByEmail(login);

            UserDetails userDetails = User
                    .withUsername(userFromDb.getLogin())
                    .password(userFromDb.getPassword())
                    .roles(userFromDb.getRole_name())
                    .build();
            return userDetails;
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}
