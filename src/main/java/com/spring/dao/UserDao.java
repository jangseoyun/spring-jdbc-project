package com.spring.dao;

import com.spring.domain.QueryCrud;
import com.spring.domain.UserQueryImpl;
import com.spring.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private QueryCrud userQuery;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userQuery = new UserQueryImpl();
    }

    public void add(User user) {
        jdbcTemplate.update(userQuery.add(), user.getId(), user.getName(), user.getPassword());
    }

    public User findById(int id) {
        RowMapper<User> rowMapper = new RowMapper<>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User getUser = new User(rs.getInt("id")
                        , rs.getString("name")
                        , rs.getString("password"));
                return getUser;
            }
        };

        return jdbcTemplate.queryForObject(userQuery.findOne(), rowMapper, id);
    }

    public void deleteAll() {
        jdbcTemplate.update(userQuery.deleteAll());
    }

    public List<User> findAll() {
        RowMapper<User> rowMapper = new RowMapper<>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getInt("id")
                        , rs.getString("name")
                        , rs.getString("password")
                );
                return user;
            }
        };
        return jdbcTemplate.query(userQuery.findAll(), rowMapper);
    }

    public int getCountAll() {
        return jdbcTemplate.queryForObject(userQuery.getCountAll(), Integer.class);
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        //userDao.add();
        //System.out.println(userDao.findAll());
        User user = userDao.findById(1);
        System.out.println(user.getName());
    }
}
