package com.codewithashith.controller;

import com.codewithashith.dao.TodoDao;
import com.codewithashith.model.Todo;
import com.codewithashith.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TodoController extends HttpServlet {

    private final TodoDao todoDao;

    public TodoController() {
        todoDao = new TodoDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            todoDao.deleteTodo(Integer.parseInt(id));
        }

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("todo.jsp");
        String userId = req.getSession().getAttribute("id").toString();

        String item = req.getParameter("todo");

        if (item != null && item.trim().length() > 0)
            todoDao.addTodo(item, Integer.parseInt(userId));

        List<Todo> todos = todoDao.selectAllTodos(Integer.parseInt(userId));
        req.setAttribute("todos", todos);
        dispatcher.forward(req, resp);

    }
}