<%@ page import="java.net.*,java.io.*,com.google.gson.*" %>
<%
    Object tokenObj = session.getAttribute("token");
    
    if (tokenObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = tokenObj.toString();
    
    String applicationId = request.getParameter("applicationId");
    String studentId = request.getParameter("studentId");
    String internshipId = request.getParameter("internshipId");
    
    JsonArray logs = new JsonArray();
    String message = "";
    
    if (applicationId != null) {
        try {
            URL url = new URL("http://localhost:8081/api/worklogs/application/" + applicationId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            
            JsonObject obj = JsonParser.parseString(json.toString()).getAsJsonObject();
            logs = obj.getAsJsonArray("content");
            
            } catch (Exception e) {
                logs = new JsonArray();
            }
        }
        
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String grade = request.getParameter("grade");
                String comment = request.getParameter("comment");
                
                URL url = new URL("http://localhost:8081/api/evaluations");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                
                String jsonInput = "{"
                + "\"studentId\":" + studentId + ","
                + "\"internshipId\":" + internshipId + ","
                + "\"evaluatorRole\":\"COMPANY\","
                + "\"grade\":" + grade + ","
                + "\"comment\":\"" + comment + "\""
                + "}";
                
                OutputStream os = conn.getOutputStream();
                os.write(jsonInput.getBytes());
                os.flush();
                
                if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                    message = "Evaluation saved successfully";
                    } else {
                        message = "Error saving evaluation";
                    }
                    
                    } catch (Exception e) {
                        message = "Error occurred";
                    }
                }
            %>

            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Evaluate Student</title>
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>

                <body>
                    <div class="container mt-4">

                        <h2 class="text-center mb-4">Student Work Logs</h2>

                        <div class="mb-3 text-center">
                            <a href="dashboard.jsp" class="btn btn-secondary">&larr; Back to Dashboard</a>
                        </div>

                        <div class="row">
                            <div class="col-md-7">

                                <h4>Weekly Activities</h4>

                                <%
                                    for (JsonElement el : logs) {
                                        JsonObject log = el.getAsJsonObject();
                                    %>
                                    <div class="card mb-3">
                                        <div class="card-body">
                                            <h5>Week <%= log.get("weekNumber").getAsInt() %></h5>
                                            <p><%= log.get("description").getAsString() %></p>
                                            <small class="text-muted"><%= log.get("createdAt").getAsString() %></small>
                                        </div>
                                    </div>
                                    <%
                                    }
                                %>

                            </div>

                            <div class="col-md-5">

                                <h4>Evaluate Student</h4>

                                <form method="post">

                                    <input type="hidden" name="studentId" value="<%= studentId %>"/>
                                    <input type="hidden" name="internshipId" value="<%= internshipId %>"/>

                                    <div class="mb-3">
                                        <label class="form-label">Grade</label>
                                        <input type="number" name="grade" class="form-control" min="5" max="10" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Comment</label>
                                        <textarea name="comment" class="form-control" rows="4"></textarea>
                                    </div>

                                    <button type="submit" class="btn btn-primary w-100">Submit Evaluation</button>
                                </form>

                                <div class="mt-3 text-center">
                                    <span><%= message %></span>
                                </div>

                            </div>
                        </div>

                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                </body>
            </html>