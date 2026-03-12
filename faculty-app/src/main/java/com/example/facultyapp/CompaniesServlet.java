package com.example.facultyapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    private final String BACKEND_URL = "http://localhost:8081/api/companies";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Map<String, String>> companies = new ArrayList<>();
        try {
            URL url = new URL(BACKEND_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
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

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("username", username);
        json.put("password", password);
        json.put("description", description);
        json.put("approved", approved);

        try {
            URL url = new URL(BACKEND_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();

            if (con.getResponseCode() == 201) {
                resp.sendRedirect("companies");
            } else {
                req.setAttribute("error", "Failed to add company");
                doGet(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while adding company");
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
            URL url = new URL(BACKEND_URL + "/" + id + "/status");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");

            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();

            resp.setStatus(con.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}