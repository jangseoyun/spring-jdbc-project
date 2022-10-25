package com.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.domain.LocalConnectionImpl;
import java.sql.SQLException;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao localUserDao() throws SQLException {
        return new UserDao(new LocalConnectionImpl());
    }

}


