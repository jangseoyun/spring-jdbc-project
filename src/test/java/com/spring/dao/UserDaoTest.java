package com.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.vo.User;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TODO")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setup() throws SQLException{
        userDao = context.getBean("localUserDao", UserDao.class);
        user1 = new User(1, "seoyun", "1234");
        user2 = new User(2, "seoseo", "1234");
        user3 = new User(3, "yunyun", "1234");
        userDao.deleteAll();
    }

    @Test
    void addAndGet() throws SQLException {
        int id = 1;
        userDao.add(user1);
        User user = userDao.findById(id);
        assertEquals("seoyun", user.getName());
        assertEquals("1234", user.getPassword());
    }

    @DisplayName("user가 null인 경우")
    @Test
    void getUserNull() throws SQLException {
        userDao.add(user1);
        assertThrows(EmptyResultDataAccessException.class
                , () -> userDao.findById(4));
    }
}