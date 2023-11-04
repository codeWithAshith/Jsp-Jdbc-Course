package com.codewithashith;

import com.codewithashith.controller.AuthController;
import com.codewithashith.controller.TodoController;
import com.codewithashith.dao.TodoDao;
import com.codewithashith.model.Todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
@MultipartConfig(maxFileSize = 16177215)
public class AppController extends HttpServlet {

    private final AuthController authController;
    private final TodoController todoController;

    public AppController() {
        authController = new AuthController();
        todoController = new TodoController();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/todo")) {
            String id = req.getParameter("id");
            if (id != null)
                todoController.deleteTodo(Integer.parseInt(id));
            todoController.viewTodos(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/login")) {
            authController.login(req, resp);
        } else if (action.equals("/todo")) {
            if (req.getParameter("todo") != null) {
                todoController.addTodo(req);
            }
            todoController.viewTodos(req, resp);
        }
    }

}