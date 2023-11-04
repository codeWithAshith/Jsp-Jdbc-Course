package com.codewithashith.controller;

import com.codewithashith.dao.TodoDao;
import com.codewithashith.model.Todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TodoController {
    private final TodoDao todoDao;

    public TodoController() {
        todoDao = new TodoDao();
    }

    public void viewTodos(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("todo.jsp");
        HttpSession session = req.getSession();
        String userId = session.getAttribute("id").toString();
        if (userId != null) {
            List<Todo> todos = todoDao.selectAllTodos(Integer.parseInt(userId));
            req.setAttribute("todos", todos);
            dispatcher.forward(req, resp);
        }
    }

    public void addTodo(HttpServletRequest req) throws ServletException, IOException {
        String userId = req.getSession().getAttribute("id").toString();
        String item = req.getParameter("todo");
        Part filePart = req.getPart("file");
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            if (item != null && item.trim().length() > 0)
                todoDao.addTodo(item, Integer.parseInt(userId), inputStream);
        }
    }

    public void deleteTodo(int id) {
        todoDao.deleteTodo(id);
    }
}
