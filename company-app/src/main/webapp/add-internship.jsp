<%@ page import="java.net.*,java.io.*,com.google.gson.*" %>
<%
    if (session.getAttribute("companyId") == null || session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    Long companyId = Long.parseLong(session.getAttribute("companyId").toString());
    String error = null;
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String technologies = request.getParameter("technologies");
        String period = request.getParameter("period");
        String conditions = request.getParameter("conditions");
        String capacityStr = request.getParameter("capacity");
        
        Integer capacity = 0;
        if (capacityStr != null && !capacityStr.isEmpty()) {
            capacity = Integer.parseInt(capacityStr);
        }
        
        JsonObject json = new JsonObject();
        json.addProperty("companyId", companyId);
        json.addProperty("title", title);
        json.addProperty("description", description);
        json.addProperty("period", period);
        json.addProperty("conditions", conditions);
        json.addProperty("capacity", capacity);
        
        JsonArray techArray = new JsonArray();
        if (technologies != null && !technologies.isEmpty()) {
            for (String t : technologies.split(",")) {
                techArray.add(t.trim());
            }
        }
        json.add("technologies", techArray);
        
        try {
            URL url = new URL("http://localhost:8081/api/internships");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);
            
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("utf-8"));
            os.close();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                response.sendRedirect("manage-internships.jsp");
                return;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder respError = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) respError.append(line);
                    br.close();
                    error = respError.toString();
                }
                } catch (Exception e) {
                    error = "Greška: " + e.getMessage();
                }
            }
        %>

        <!DOCTYPE html>
        <html>
            <head>
                <meta charset="UTF-8">
                <title>Add Internship</title>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>
            <body>
                <div class="container mt-4">
                    <h2 class="mb-4 text-center">Add internship</h2>

                    <form method="post">
                        <div class="mb-3">
                            <input type="text" class="form-control" name="title" placeholder="Title" required>
                        </div>
                        <div class="mb-3">
                            <textarea class="form-control" name="description" placeholder="Description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <input type="text" class="form-control" name="period" placeholder="Period" required>
                        </div>
                        <div class="mb-3">
                            <input type="text" class="form-control" name="capacity" placeholder="Capacity" required>
                        </div>
                        <div class="mb-3">
                            <input type="text" class="form-control" name="technologies" placeholder="Technologies (comma separated)">
                        </div>
                        <div class="mb-3">
                            <textarea class="form-control" name="conditions" placeholder="Conditions"></textarea>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Add internship</button>
                        <a href="manage-internships.jsp" class="btn btn-secondary w-100 mt-2">Back</a>
                    </form>

                    <% if (error != null) { %>
                    <div class="alert alert-danger mt-3">
                        <%= error %>
                    </div>
                    <% } %>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            </body>
        </html>