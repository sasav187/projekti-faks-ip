<%@ page import="java.net.*,java.io.*,com.google.gson.*" %>
<%
    if (session.getAttribute("companyId") == null || session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    String id = request.getParameter("id");
    
    if (id == null || id.isEmpty()) {
        response.sendRedirect("manage-internships.jsp");
        return;
    }
    
    String title = "";
    String description = "";
    String period = "";
    String conditions = "";
    String technologies = "";
    int capacity = 0;
    String error = null;
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        
        title = request.getParameter("title");
        description = request.getParameter("description");
        period = request.getParameter("period");
        conditions = request.getParameter("conditions");
        technologies = request.getParameter("technologies");
        capacity = Integer.parseInt(request.getParameter("capacity"));
        
        JsonObject json = new JsonObject();
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
            URL url = new URL("http://localhost:8081/api/internships/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);
            
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("utf-8"));
            os.close();
            
            int responseCode = conn.getResponseCode();
            
            if (responseCode == 200) {
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
                    error = e.getMessage();
                }
                
                } else {
                    
                    try {
                        URL url = new URL("http://localhost:8081/api/internships/" + id);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", "Bearer " + token);
                        
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder jsonStr = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) jsonStr.append(line);
                        reader.close();
                        
                        JsonObject obj = JsonParser.parseString(jsonStr.toString()).getAsJsonObject();
                        
                        title = obj.get("title").getAsString();
                        description = obj.get("description").getAsString();
                        period = obj.get("period").getAsString();
                        conditions = obj.get("conditions").getAsString();
                        capacity = obj.get("capacity").getAsInt();
                        
                        if (obj.has("technologies")) {
                            JsonArray arr = obj.getAsJsonArray("technologies");
                            for (int i = 0; i < arr.size(); i++) {
                                technologies += arr.get(i).getAsString();
                                if (i < arr.size() - 1) technologies += ", ";
                            }
                        }
                        
                        } catch (Exception e) {
                            error = e.getMessage();
                        }
                    }
                %>

                <!DOCTYPE html>
                <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Edit Internship</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                    </head>
                    <body>

                        <div class="container mt-4">
                            <h2 class="text-center mb-4">Edit Internship</h2>

                            <form method="post">
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="title" value="<%= title %>" required>
                                </div>
                                <div class="mb-3">
                                    <textarea class="form-control" name="description" required><%= description %></textarea>
                                </div>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="period" value="<%= period %>" required>
                                </div>
                                <div class="mb-3">
                                    <input type="number" class="form-control" name="capacity" value="<%= capacity %>" required>
                                </div>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="technologies" value="<%= technologies %>">
                                </div>
                                <div class="mb-3">
                                    <textarea class="form-control" name="conditions"><%= conditions %></textarea>
                                </div>

                                <button type="submit" class="btn btn-primary w-100">Save Changes</button>
                                <a href="manage-internships.jsp" class="btn btn-secondary w-100 mt-2">Cancel</a>
                            </form>

                            <% if (error != null) { %>
                            <div class="alert alert-danger mt-3">
                                <%= error %>
                            </div>
                            <% } %>
                        </div>

                    </body>
                </html>