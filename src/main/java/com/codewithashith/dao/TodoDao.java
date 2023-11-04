package com.codewithashith.dao;

import com.codewithashith.db.DbConnection;
import com.codewithashith.model.Todo;
import com.codewithashith.model.User;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TodoDao {

    private final Connection con;
    private final String SELECT_ALL = "SELECT id, todo, userId, image FROM todo WHERE userId=?";
    private final String INSERT_TODO = "INSERT INTO todo (todo, userId, image) VALUES (?, ?, ?);";
    private final String DELETE_TODO = "DELETE todo WHERE id=?;";

    public TodoDao() {
        con = DbConnection.getConnection();
    }

    public List<Todo> selectAllTodos(int userId) {
        List<Todo> todos = new ArrayList<Todo>();
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(rs.getString("id")));
                todo.setTodo(rs.getString("todo"));
                todo.setUserId(rs.getInt("userId"));
                Blob blob = rs.getBlob("image");
                todo.setBase64Image(Base64.getEncoder()
                        .encodeToString(blob.getBytes(1, (int) blob.length())));
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void addTodo(String todo, int userId, InputStream file) {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_TODO);
            ps.setString(1, todo);
            ps.setInt(2, userId);
            if (file != null) {
                ps.setBlob(3, file);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTodo(int id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_TODO);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
