package com.example.facultyapp;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/worklogs")
public class WorkLogServlet extends HttpServlet {

    private static final String WORKLOG_API = "http://localhost:8081/api/worklogs/application/";
    private static final String EVAL_API = "http://localhost:8081/api/evaluations";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Enum.class,
                    (JsonDeserializer<Enum>) (json, type, context) -> Enum.valueOf((Class<Enum>) type,
                            json.getAsString()))
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Long applicationId = parseLongParam(req.getParameter("applicationId"));
        Long studentId = parseLongParam(req.getParameter("studentId"));
        Long internshipId = parseLongParam(req.getParameter("internshipId"));

        if (applicationId == null || studentId == null || internshipId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid required parameters");
            return;
        }

        String worklogResponse = readFromApi(WORKLOG_API + applicationId + "?page=0&size=50");
        JsonObject worklogJson = gson.fromJson(worklogResponse, JsonObject.class);
        JsonArray worklogsJson = worklogJson.getAsJsonArray("content");

        List<Map<String, Object>> worklogs = new ArrayList<>();
        for (JsonElement e : worklogsJson) {
            Map<String, Object> map = gson.fromJson(e, Map.class);
            worklogs.add(map);
        }

        String evalResponse = readFromApi(EVAL_API + "?page=0&size=50");
        JsonElement evalElement = JsonParser.parseString(evalResponse);

        JsonArray evalJsonArr;
        if (evalElement.isJsonObject() && evalElement.getAsJsonObject().has("content")) {
            evalJsonArr = evalElement.getAsJsonObject().getAsJsonArray("content");
        } else if (evalElement.isJsonArray()) {
            evalJsonArr = evalElement.getAsJsonArray();
        } else {
            evalJsonArr = new JsonArray();
        }

        List<Map<String, Object>> evaluations = new ArrayList<>();
        for (JsonElement e : evalJsonArr) {
            Map<String, Object> map = gson.fromJson(e, Map.class);
            evaluations.add(map);
        }

        req.setAttribute("worklogs", worklogs);
        req.setAttribute("evaluations", evaluations);
        req.setAttribute("applicationId", applicationId);
        req.setAttribute("studentId", studentId);
        req.setAttribute("internshipId", internshipId);

        req.getRequestDispatcher("/WEB-INF/views/worklogs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Long studentId = parseLongParam(req.getParameter("studentId"));
        Long internshipId = parseLongParam(req.getParameter("internshipId"));
        Long applicationId = parseLongParam(req.getParameter("applicationId"));
        String gradeStr = req.getParameter("grade");
        String comment = req.getParameter("comment");

        if (studentId == null || internshipId == null || applicationId == null || gradeStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid parameters");
            return;
        }

        int grade;
        try {
            grade = Integer.parseInt(gradeStr);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid grade value");
            return;
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("studentId", studentId);
        obj.addProperty("internshipId", internshipId);
        obj.addProperty("grade", grade);
        obj.addProperty("comment", comment != null ? comment : "");
        obj.addProperty("evaluatorRole", "FACULTY");
        obj.addProperty("evaluationDate", LocalDateTime.now().toString());

        sendRequest("POST", EVAL_API, obj);

        resp.sendRedirect("worklogs?applicationId=" + applicationId +
                "&studentId=" + studentId +
                "&internshipId=" + internshipId);
    }

    private Long parseLongParam(String param) {
        if (param == null)
            return null;
        try {
            double d = Double.parseDouble(param);
            return (long) d;
        } catch (NumberFormatException e) {
            return null;
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
                while ((line = br.readLine()) != null)
                    sb.append(line);
                throw new IOException("HTTP error code: " + conn.getResponseCode() + " " + sb.toString());
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        }
    }

    private void sendRequest(String method, String urlStr, JsonObject body) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
            os.flush();
        }

        if (conn.getResponseCode() >= 400) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    sb.append(line);
                throw new IOException("HTTP error code: " + conn.getResponseCode() + " " + sb.toString());
            }
        }
    }
}