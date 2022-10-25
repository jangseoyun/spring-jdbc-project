package java.spring.dao;

import java.spring.domain.ConnectionMaker;
import java.spring.domain.QueryCrud;
import java.spring.domain.UserQueryImpl;
import java.spring.vo.User;
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
        Map<String, String> env = System.getenv();
        Connection conn = localConn.dbConnection();
        try {

            PreparedStatement pstmt = conn.prepareStatement(userQuery.findOne());
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(
                    rs.getInt("id")
                    , rs.getString("name")
                    , rs.getString("password")
            );

            rs.close();
            pstmt.close();
            conn.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
//        userDao.add();
        User user = userDao.findById(1);
        System.out.println(user.getName());
    }
}
