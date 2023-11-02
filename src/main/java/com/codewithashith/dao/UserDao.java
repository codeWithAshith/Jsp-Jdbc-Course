package com.codewithashith.dao;

import com.codewithashith.db.DbConnection;
import com.codewithashith.model.User;

import javax.servlet.RequestDispatcher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final Connection con;

    public UserDao() {
        DbConnection.createData();
        con = DbConnection.getConnection();
    }

    private String selectSQL = "SELECT id, username, password FROM auth WHERE username=? and password=?";

    public User loginUser(String username, String password) {
        User user = null;
        try {
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
