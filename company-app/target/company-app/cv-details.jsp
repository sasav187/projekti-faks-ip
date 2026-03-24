<%@ page import="java.net.*,java.io.*,org.json.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    
    String appId = request.getParameter("appId");
    String studentId = request.getParameter("studentId");
    
    JSONObject studentCV = null;
    JSONObject appObj = null;
    
    try {
        URL aUrl = new URL("http://localhost:8081/api/internship-applications/" + appId);
        HttpURLConnection aConn = (HttpURLConnection) aUrl.openConnection();
        aConn.setRequestProperty("Authorization", "Bearer " + token);
        
        BufferedReader aBr = new BufferedReader(new InputStreamReader(aConn.getInputStream()));
        StringBuilder aJson = new StringBuilder();
        String line;
        while ((line = aBr.readLine()) != null) aJson.append(line);
        appObj = new JSONObject(aJson.toString());
        
        URL cvUrl = new URL("http://localhost:8081/api/cvs?page=0&size=50");
        HttpURLConnection cvConn = (HttpURLConnection) cvUrl.openConnection();
        cvConn.setRequestProperty("Authorization", "Bearer " + token);
        
        BufferedReader cvBr = new BufferedReader(new InputStreamReader(cvConn.getInputStream()));
        StringBuilder cvJson = new StringBuilder();
        while ((line = cvBr.readLine()) != null) cvJson.append(line);
        
        JSONObject cvObj = new JSONObject(cvJson.toString());
        JSONArray cvs = cvObj.getJSONArray("content");
        
        for (int i = 0; i < cvs.length(); i++) {
            JSONObject cv = cvs.getJSONObject(i);
            if (cv.getLong("studentId") == Long.parseLong(studentId)) {
                studentCV = cv;
                break;
            }
        }
        
        } catch (Exception e) {
            out.println("Error loading data");
        }
    %>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>CV Details</title>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body class="bg-light">

            <div class="container mt-4">

                <h2 class="text-center mb-4">Student CV</h2>

                <div class="text-center mb-3">
                    <a href="students.jsp" class="btn btn-secondary">&larr; Back</a>
                </div>

                <% if(studentCV != null) { %>

                <div class="card shadow p-4">

                    <div class="row">
                        <div class="col-md-3 text-center">
                            <img src="http://localhost:8081<%= studentCV.optString("imagePath") %>"
                            class="img-fluid rounded mb-3"
                            style="max-height:200px;">
                        </div>

                        <div class="col-md-9">
                            <h3><%= studentCV.optString("firstName") %> <%= studentCV.optString("lastName") %></h3>
                            <p><strong>Email:</strong> <%= studentCV.optString("email") %></p>
                            <p><strong>Phone:</strong> <%= studentCV.optString("phone") %></p>
                            <p><strong>Address:</strong> <%= studentCV.optString("address") %></p>
                            <p><strong>Nationality:</strong> <%= studentCV.optString("nationality") %></p>
                            <p><strong>Date of Birth:</strong> <%= studentCV.optString("dateOfBirth") %></p>
                        </div>
                    </div>

                    <hr>

                    <h5>Summary</h5>
                    <p><%= studentCV.optString("summary") %></p>

                    <h5 class="mt-4">Skills</h5>
                    <%
                        JSONArray skills = studentCV.optJSONArray("skills");
                        if (skills != null) {
                            for (int i = 0; i < skills.length(); i++) {
                                JSONObject s = skills.getJSONObject(i);
                            %>
                            <div class="mb-2">
                                <%= s.optString("name") %> - (<%= s.optString("level") %>)
                            </div>
                            <% }} %>

                            <h5 class="mt-4">Education</h5>
                            <%
                                JSONArray edu = studentCV.optJSONArray("educationList");
                                if (edu != null) {
                                    for (int i = 0; i < edu.length(); i++) {
                                        JSONObject e = edu.getJSONObject(i);
                                    %>
                                    <div class="mb-2">
                                        <strong><%= e.optString("institution") %></strong><br/>
                                        <small>
                                            <%= e.optString("degree") %> - <%= e.optString("fieldOfStudy") %><br/>
                                            <%= e.optString("startDate") %> - <%= e.optString("endDate","Present") %>
                                        </small>
                                        <p><%= e.optString("description") %></p>
                                    </div>
                                    <% }} %>

                                    <h5 class="mt-4">Work Experience</h5>
                                    <%
                                        JSONArray exp = studentCV.optJSONArray("workExperiences");
                                        if (exp != null) {
                                            for (int i = 0; i < exp.length(); i++) {
                                                JSONObject w = exp.getJSONObject(i);
                                            %>
                                            <div class="mb-2">
                                                <strong><%= w.optString("companyName") %></strong><br/>
                                                <small>
                                                    <%= w.optString("position") %><br/>
                                                    <%= w.optString("startDate") %> - <%= w.optString("endDate","Present") %>
                                                </small>
                                                <p><%= w.optString("description") %></p>
                                            </div>
                                            <% }} %>

                                            <h5 class="mt-4">Languages</h5>
                                            <%
                                                JSONArray langs = studentCV.optJSONArray("languages");
                                                if (langs != null) {
                                                    for (int i = 0; i < langs.length(); i++) {
                                                        JSONObject l = langs.getJSONObject(i);
                                                    %>
                                                    <div class="mb-1">
                                                        <strong><%= l.optString("name") %></strong>:<br/>
                                                        Listening: (<%= l.optString("listeningLevel") %>)<br/>
                                                        Reading: (<%= l.optString("readingLevel") %>)<br/>
                                                        Writing: (<%= l.optString("writingLevel") %>)<br/>
                                                        Speaking: (<%= l.optString("spokenLevel") %>)<br/>
                                                    </div>
                                                    <% }} %>

                                                    <h5 class="mt-4">Additional Info</h5>
                                                    <%
                                                        JSONArray add = studentCV.optJSONArray("additionalInfos");
                                                        if (add != null) {
                                                            for (int i = 0; i < add.length(); i++) {
                                                                JSONObject a = add.getJSONObject(i);
                                                            %>
                                                            <p>- <%= a.optString("content") %></p>
                                                            <% }} %>

                                                            <hr>

                                                            <p>
                                                                <%
                                                                    String status = appObj.isNull("status") ? "PENDING" : appObj.getString("status");
                                                                    if(status.equals("PENDING")) {
                                                                    %>
                                                                    <span class="badge bg-warning">Pending</span>
                                                                    <% } else if(status.equals("ACCEPTED")) { %>
                                                                    <span class="badge bg-success">Accepted</span>
                                                                    <% } else { %>
                                                                    <span class="badge bg-danger">Rejected</span>
                                                                    <% } %>
                                                                </p>

                                                                <div class="d-flex flex-wrap gap-2 mt-3">
                                                                    <form action="update-status.jsp" method="post">
                                                                        <input type="hidden" name="id" value="<%= appId %>">
                                                                        <input type="hidden" name="status" value="ACCEPTED">
                                                                        <button class="btn btn-success">Accept</button>
                                                                    </form>

                                                                    <form action="update-status.jsp" method="post">
                                                                        <input type="hidden" name="id" value="<%= appId %>">
                                                                        <input type="hidden" name="status" value="REJECTED">
                                                                        <button class="btn btn-danger">Reject</button>
                                                                    </form>

                                                                    <a href="cv-pdf.jsp?id=<%= studentCV.getLong("id") %>" class="btn btn-secondary">
                                                                        Download PDF
                                                                    </a>
                                                                </div>

                                                            </div>

                                                            <% } %>

                                                        </div>

                                                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                                                    </body>
                                                </html>