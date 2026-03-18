package com.example.facultyapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/students")
@MultipartConfig
public class StudentsServlet extends HttpServlet {

    private final String BACKEND_URL = "http://localhost:8081/api";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Map<String, String>> students = new ArrayList<>();
        try {
            URL url = new URL(BACKEND_URL + "/students?page=0&size=1000");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() == 200) {
                String jsonStr = new String(con.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JSONObject json = new JSONObject(jsonStr);
                JSONArray arr = json.getJSONArray("content");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject s = arr.getJSONObject(i);
                    Map<String, String> student = new HashMap<>();
                    student.put("id", String.valueOf(s.getLong("id")));
                    student.put("username", s.optString("username")); // direktno iz JSON-a
                    student.put("indexNumber", s.optString("indexNumber"));
                    student.put("faculty", s.optString("faculty"));
                    student.put("year", String.valueOf(s.optInt("year")));
                    students.add(student);
                }
            } else {
                req.setAttribute("error", "Failed to fetch students from backend");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while fetching students");
        }

        req.setAttribute("students", students);
        req.getRequestDispatcher("/WEB-INF/views/students.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        try {

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String indexNumber = req.getParameter("indexNumber");
            String faculty = req.getParameter("faculty");
            String yearStr = req.getParameter("year");

            if (username == null || password == null || indexNumber == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            Integer year = null;
            if (yearStr != null && !yearStr.isBlank()) {
                year = Integer.parseInt(yearStr);
            }

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("password", password);
            userJson.put("role", "STUDENT");

            HttpURLConnection userCon = (HttpURLConnection) new URL(BACKEND_URL + "/users").openConnection();
            userCon.setRequestMethod("POST");
            userCon.setRequestProperty("Content-Type", "application/json");
            userCon.setDoOutput(true);

            try (OutputStream os = userCon.getOutputStream()) {
                os.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
            }

            int userResponseCode = userCon.getResponseCode();
            if (userResponseCode != 201) {
                String errorMsg = new String(userCon.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User creation failed: " + errorMsg);
                return;
            }

            String userResp = new String(userCon.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            Long userId = new JSONObject(userResp).getLong("id");

            JSONObject studentJson = new JSONObject();
            studentJson.put("userId", userId);
            studentJson.put("indexNumber", indexNumber);
            studentJson.put("faculty", faculty != null ? faculty : JSONObject.NULL);
            studentJson.put("year", year != null ? year : JSONObject.NULL);

            HttpURLConnection studentCon = (HttpURLConnection) new URL(BACKEND_URL + "/students").openConnection();
            studentCon.setRequestMethod("POST");
            studentCon.setRequestProperty("Content-Type", "application/json");
            studentCon.setDoOutput(true);

            try (OutputStream os = studentCon.getOutputStream()) {
                os.write(studentJson.toString().getBytes(StandardCharsets.UTF_8));
            }

            int studentResponseCode = studentCon.getResponseCode();
            if (studentResponseCode != 200 && studentResponseCode != 201) {
                String errorMsg = new String(studentCon.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Student creation failed: " + errorMsg);
                return;
            }

            resp.sendRedirect("students");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            HttpURLConnection con = (HttpURLConnection) new URL(BACKEND_URL + "/students/" + id).openConnection();
            con.setRequestMethod("DELETE");
            con.getResponseCode();
        }
        resp.setStatus(200);
    }
}