package com.example.facultyapp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final String BACKEND_URL = "http://localhost:8081/api/auth/login";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        URL url = new URL(BACKEND_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInput = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        if (code == 200) {
            InputStream responseStream = con.getInputStream();
            String responseBody = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(responseBody);
            String token = json.getString("token");
            String role = json.getString("role");

            if (!"FACULTY".equals(role)) {
                req.setAttribute("error", "Nemate pristup ovoj aplikaciji");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            session.setAttribute("jwtToken", token);
            resp.sendRedirect("dashboard");
        } else {
            req.setAttribute("error", "Neispravno korisničko ime ili lozinka");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}