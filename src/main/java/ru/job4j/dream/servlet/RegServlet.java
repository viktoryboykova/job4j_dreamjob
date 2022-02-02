package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        User user = DbStore.instOf().findUserByEmail(email);
        if (user != null) {
            req.setAttribute("error", "Пользователь с таким e-mail уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        if (!password1.equals(password2)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        User newUser = new User(name, email, password1);
        DbStore.instOf().createUser(newUser);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
