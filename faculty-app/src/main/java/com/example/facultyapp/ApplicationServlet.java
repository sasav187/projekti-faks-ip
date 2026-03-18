package com.example.facultyapp;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@WebServlet("/applications")
public class ApplicationServlet extends HttpServlet {

    private static final String API = "http://localhost:8081/api/internship-applications?page=0&size=50";
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String response = readFromApi(API);
        JsonObject json = gson.fromJson(response, JsonObject.class);
        JsonArray arr = json.getAsJsonArray("content");
        List<Map<String, Object>> applications = new ArrayList<>();

        for (JsonElement e : arr) {
            Map<String, Object> map = gson.fromJson(e, Map.class);
            fixIds(map, "id");
            fixIds(map, "studentId");
            fixIds(map, "internshipId");
            applications.add(map);
        }

        req.setAttribute("applications", applications);
        req.getRequestDispatcher("/WEB-INF/views/applications.jsp").forward(req, resp);
    }

    private void fixIds(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) {
            long l = ((Number) val).longValue();
            map.put(key, l); 
        }
    }

    private String readFromApi(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() >= 400) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                throw new IOException("HTTP error code: " + conn.getResponseCode() + " " + sb.toString());
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
}