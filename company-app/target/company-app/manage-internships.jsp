<%@ page import="java.net.*,java.io.*,com.google.gson.*" %>
<%
    Object tokenObj = session.getAttribute("token");
    Object companyIdObj = session.getAttribute("companyId");
    
    if (tokenObj == null || companyIdObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = tokenObj.toString();
    Long companyId = Long.parseLong(companyIdObj.toString());
    
    JsonArray internships = new JsonArray();
    
    try {
        String apiUrl = "http://localhost:8081/api/internships/by-company?companyId="
        + companyId + "&page=0&size=100";
        
        URL url = new URL(apiUrl);
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
        internships = obj.getAsJsonArray("content");
        
        } catch (Exception e) {
            internships = new JsonArray();
        }
    %>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>Manage Internships</title>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>
        <body>
            <div class="container mt-4">

                <h2 class="mb-4 text-center">Manage internships</h2>

                <div class="mb-3 text-center">
                    <a href="dashboard.jsp" class="btn btn-secondary mb-2">&larr; Back to Dashboard</a>
                </div>

                <div class="d-flex justify-content-between mb-3">
                    <a href="add-internship.jsp" class="btn btn-success mb-2">+ Add internship</a>
                </div>

                <div class="list-group">
                    <%
                        for (JsonElement el : internships) {
                            JsonObject i = el.getAsJsonObject();
                            
                            String id = i.get("id").getAsString();
                            String title = i.get("title").getAsString();
                            String description = i.get("description").getAsString();
                            String period = i.get("period").getAsString();
                            int capacity = i.get("capacity").getAsInt();
                            
                            String techs = "";
                            if (i.has("technologies")) {
                                JsonArray arr = i.getAsJsonArray("technologies");
                                for (int j = 0; j < arr.size(); j++) {
                                    techs += arr.get(j).getAsString();
                                    if (j < arr.size() - 1) techs += ", ";
                                }
                            }
                        %>
                        <div class="list-group-item mb-2">
                            <h5><%= title %></h5>
                            <p><strong>Description:</strong> <%= description %></p>
                            <p><strong>Period:</strong> <%= period %></p>
                            <p><strong>Capacity:</strong> <%= capacity %></p>
                            <p><strong>Technologies:</strong> <%= techs %></p>
                            <a href="edit-internship.jsp?id=<%= id %>" class="btn btn-primary btn-sm">Edit</a>
                            <a href="delete-internship.jsp?id=<%= id %>" class="btn btn-danger btn-sm"
                                onclick="return confirm('Da li ste sigurni da zelite obrisati praksu?')">Delete</a>
                            </div>
                            <%
                            }
                        %>
                    </div>

                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            </body>
        </html>