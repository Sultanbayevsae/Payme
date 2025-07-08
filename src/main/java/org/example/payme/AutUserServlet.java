package org.example.payme;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.payme.entity.User;

import java.io.IOException;

public class AutUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullname = req.getParameter("fullname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String about = req.getParameter("about");
        Integer age = Integer.valueOf(req.getParameter("age"));

        User user = User.builder()
                .fullName(fullname)
                .username(username)
                .email(email)
                .about(about)
                .age(age)
                .build();

    }
}
