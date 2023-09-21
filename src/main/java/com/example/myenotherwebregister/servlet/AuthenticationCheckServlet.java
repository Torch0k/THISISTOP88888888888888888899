package com.example.myenotherwebregister.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check-auth")
public class AuthenticationCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Проверяем, есть ли атрибут "username" в сессии
        String username = (String) request.getSession().getAttribute("username");

        if (username != null) {
            response.getWriter().write("Пользователь " + username + " авторизован.");
        } else {
            response.getWriter().write("Пользователь не авторизован.");
        }
    }
}