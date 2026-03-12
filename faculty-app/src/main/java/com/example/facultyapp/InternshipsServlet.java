package com.example.facultyapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

@WebServlet("/internships")
public class InternshipsServlet extends HttpServlet {

    private final String BACKEND_URL = "http://localhost:8081/api/internships";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Map<String, String>> internships = new ArrayList<>();
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
                    Map<String, String> internship = new HashMap<>();
                    internship.put("id", c.optString("id"));
                    internship.put("companyName", c.optString("companyName"));
                    internship.put("title", c.optString("title"));
                    internship.put("description", c.optString("description"));
                    internship.put("period", c.optString("period"));
                    internship.put("capacity", String.valueOf(c.optInt("capacity")));
                    internships.add(internship);
                }
            } else {
                req.setAttribute("error", "Failed to fetch internships from backend");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while fetching internships");
        }

        req.setAttribute("internships", internships);
        req.getRequestDispatcher("/WEB-INF/views/internships.jsp").forward(req, resp);
    }
}
