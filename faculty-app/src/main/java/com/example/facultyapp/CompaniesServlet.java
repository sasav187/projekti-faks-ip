package com.example.facultyapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.json.*;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    private final String BACKEND_COMPANY_URL = "http://localhost:8081/api/companies";
    private final String BACKEND_USER_URL = "http://localhost:8081/api/users";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Map<String, String>> companies = new ArrayList<>();
        try {
            URL url = new URL(BACKEND_COMPANY_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
                reader.close();

                JSONObject obj = new JSONObject(sb.toString());
                JSONArray arr = obj.getJSONArray("content");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject c = arr.getJSONObject(i);
                    Map<String, String> company = new HashMap<>();
                    company.put("id", c.optString("id"));
                    company.put("name", c.optString("name"));
                    company.put("username", c.optString("username"));
                    company.put("description", c.optString("description"));
                    company.put("approved", c.optBoolean("approved") ? "true" : "false");
                    companies.add(company);
                }
            } else {
                req.setAttribute("error", "Failed to fetch companies from backend");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while fetching companies");
        }

        req.setAttribute("companies", companies);
        req.getRequestDispatcher("/WEB-INF/views/companies.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String description = req.getParameter("description");
        String approved = req.getParameter("approved");

        try {
            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("password", password);
            userJson.put("role", "COMPANY");

            HttpURLConnection userCon = (HttpURLConnection) new URL(BACKEND_USER_URL).openConnection();
            userCon.setDoOutput(true);
            userCon.setRequestMethod("POST");
            userCon.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = userCon.getOutputStream()) {
                os.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
            }

            if (userCon.getResponseCode() != 201) {
                req.setAttribute("error", "Failed to create user");
                doGet(req, resp);
                return;
            }

            String userRespStr = new String(userCon.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject userResp = new JSONObject(userRespStr);
            Long userId = userResp.getLong("id");

            JSONObject companyJson = new JSONObject();
            companyJson.put("userId", userId);
            companyJson.put("name", name);
            companyJson.put("description", description);
            companyJson.put("approved", Boolean.parseBoolean(approved));

            HttpURLConnection companyCon = (HttpURLConnection) new URL(BACKEND_COMPANY_URL).openConnection();
            companyCon.setDoOutput(true);
            companyCon.setRequestMethod("POST");
            companyCon.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = companyCon.getOutputStream()) {
                os.write(companyJson.toString().getBytes(StandardCharsets.UTF_8));
            }

            if (companyCon.getResponseCode() == 201) {
                resp.sendRedirect("companies");
            } else {
                req.setAttribute("error", "Failed to create company");
                doGet(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while creating user or company");
            doGet(req, resp);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String approved = req.getParameter("approved");

        JSONObject json = new JSONObject();
        json.put("approved", Boolean.parseBoolean(approved));

        try {
            URL url = new URL(BACKEND_COMPANY_URL + "/" + id + "/status");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            resp.setStatus(con.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}