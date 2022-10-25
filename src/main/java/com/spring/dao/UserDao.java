package com.spring.dao;

import com.spring.domain.QueryCrud;
import com.spring.domain.UserQueryImpl;
import com.spring.domain.ConnectionMaker;
import com.spring.vo.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private ConnectionMaker localConn;
    private QueryCrud userQuery;

    public UserDao(ConnectionMaker localConn) {
        this.localConn = localConn;
        this.userQuery = new UserQueryImpl();
    }

    public void add(User user) throws SQLException {
        Map<String, String> env = System.getenv();
        Connection conn = localConn.dbConnection();
        try {

            //쿼리 바인딩
            PreparedStatement pstmt = conn.prepareStatement(userQuery.add());
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(int id) throws SQLException {
        Connection conn = localConn.dbConnection();
        try {

            PreparedStatement pstmt = conn.prepareStatement(userQuery.findOne());
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            User user;
            if (rs.next()) {
                user = new User(
                        rs.getInt("id")
                        , rs.getString("name")
                        , rs.getString("password")
                );
            } else {
                throw new EmptyResultDataAccessException(1);
            }

            rs.close();
            pstmt.close();
            conn.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() throws SQLException {
        Connection conn = localConn.dbConnection();

        PreparedStatement ps = conn.prepareStatement(userQuery.deleteAll());
        ps.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
//        userDao.add();
        User user = userDao.findById(5);
        System.out.println(user.getName());
    }
}
