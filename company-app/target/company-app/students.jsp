<%@ page import="java.net.*,java.io.*,org.json.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    
    URL url = new URL("http://localhost:8081/api/internship-applications?page=0&size=10");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestProperty("Authorization", "Bearer " + token);
    
    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder json = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) json.append(line);
    
    JSONObject obj = new JSONObject(json.toString());
    JSONArray applications = obj.getJSONArray("content");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Students</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">

        <div class="container mt-4">

            <h2 class="mb-4 text-center">Student Applications</h2>

            <div class="mb-3 text-center">
                <a href="dashboard.jsp" class="btn btn-secondary mb-2">
                    &larr; Back to Dashboard
                </a>
            </div>

            <div class="list-group">

                <%
                    for (int i = 0; i < applications.length(); i++) {
                        
                        JSONObject app = applications.getJSONObject(i);
                        long studentId = app.getLong("studentId");
                        
                        URL sUrl = new URL("http://localhost:8081/api/students/" + studentId);
                        HttpURLConnection sConn = (HttpURLConnection) sUrl.openConnection();
                        sConn.setRequestProperty("Authorization", "Bearer " + token);
                        
                        BufferedReader sBr = new BufferedReader(new InputStreamReader(sConn.getInputStream()));
                        StringBuilder sJson = new StringBuilder();
                        while ((line = sBr.readLine()) != null) sJson.append(line);
                        
                        JSONObject student = new JSONObject(sJson.toString());
                    %>

                    <div class="list-group-item mb-3 shadow-sm">

                        <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center">

                            <div>
                                <h5><%= student.getString("username") %></h5>
                                <p class="mb-1"><strong>Index number:</strong> <%= student.getString("indexNumber") %></p>
                                <p class="mb-1"><strong>Faculty:</strong> <%= student.getString("faculty") %></p>
                                <p class="mb-1"><strong>Year:</strong> <%= student.getInt("year") %></p>

                                <p class="mt-2">
                                    <%
                                        String status = app.isNull("status") ? "PENDING" : app.getString("status");
                                        
                                        if(status.equals("PENDING")) {
                                        %>
                                        <span class="badge bg-warning">Pending</span>
                                        <%
                                            } else if(status.equals("ACCEPTED")) {
                                            %>
                                            <span class="badge bg-success">Accepted</span>
                                            <%
                                                } else {
                                                %>
                                                <span class="badge bg-danger">Rejected</span>
                                                <%
                                                }
                                            %>
                                        </p>
                                    </div>

                                    <div class="mt-3 mt-md-0">
                                        <a href="cv-details.jsp?appId=<%= app.getLong("id") %>&studentId=<%= studentId %>"
                                            class="btn btn-primary">
                                            View CV
                                        </a>
                                    </div>

                                </div>

                            </div>

                            <% } %>

                        </div>

                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                </body>
            </html>