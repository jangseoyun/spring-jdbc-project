package com.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.vo.User;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TODO")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @BeforeEach
    static void beforeAll() {

    }

    @Test
    void addAndGet() throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        int id = 44;
        userDao.add(new User(id, "kyeongrok", "12345"));
        User user = userDao.findById(id);
        assertEquals("kyeongrok", user.getName());
        assertEquals("12345", user.getPassword());
    }


}