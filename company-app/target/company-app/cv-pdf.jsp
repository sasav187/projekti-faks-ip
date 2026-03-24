<%@ page import="java.net.*,java.io.*,org.json.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    String id = request.getParameter("id");
    
    JSONObject cv = null;
    
    try {
        URL url = new URL("http://localhost:8081/api/cvs/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + token);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) json.append(line);
        
        cv = new JSONObject(json.toString());
        
        } catch(Exception e) {
            out.println("Error");
        }
    %>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>CV PDF</title>

            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    background: #ccc;
                }
                
                .page {
                    width: 210mm;
                    min-height: 297mm;
                    margin: auto;
                    background: white;
                    display: flex;
                }
                
                @page {
                    size: A4;
                    margin: 0;
                }
                
                .container {
                    display: flex;
                    width: 100%;
                }
                
                /* SIDEBAR */
                .sidebar {
                    width: 30%;
                    background: #2c3e50;
                    color: white;
                    padding: 20px;
                }
                
                /* MAIN */
                .main {
                    width: 70%;
                    padding: 20px;
                }
                
                h2, h3 {
                    margin-bottom: 10px;
                }
                
                .section {
                    margin-bottom: 20px;
                }
                
                img {
                    width: 100%;
                    border-radius: 10px;
                    margin-bottom: 15px;
                }
                
                @media print {
                    body {
                        background: white;
                    }
                    
                    button {
                        display: none;
                    }
                    
                    .page {
                        margin: 0;
                        width: 210mm;
                        height: 297mm;
                    }
                }
            </style>

            <script>
                window.onload = function() {
                    window.print();
                }
            </script>

        </head>

        <body>

            <button onclick="window.print()" style="margin:10px;">Print / Save PDF</button>

            <% if(cv != null) { %>

            <div class="page">
                <div class="container">

                    <div class="sidebar">
                        <img src="http://localhost:8081<%= cv.optString("imagePath") %>">

                        <h2><%= cv.optString("firstName") %> <%= cv.optString("lastName") %></h2>

                        <p><strong>Email:</strong><br><%= cv.optString("email") %></p>
                        <p><strong>Phone:</strong><br><%= cv.optString("phone") %></p>
                        <p><strong>Address:</strong><br><%= cv.optString("address") %></p>

                        <h3>Languages</h3>
                        <%
                            JSONArray langs = cv.optJSONArray("languages");
                            if(langs != null){
                                for(int i=0;i<langs.length();i++){
                                    JSONObject l = langs.getJSONObject(i);
                                %>
                                <div style="margin-bottom:10px;">
                                    <strong><%= l.optString("name") %></strong><br/>
                                    Listening: (<%= l.optString("listeningLevel") %>)<br/>
                                    Reading: (<%= l.optString("readingLevel") %>)<br/>
                                    Writing: (<%= l.optString("writingLevel") %>)<br/>
                                    Speaking: (<%= l.optString("spokenLevel") %>)
                                </div>
                                <% }} %>

                            </div>

                            <div class="main">

                                <div class="section">
                                    <h3>Profile</h3>
                                    <p><%= cv.optString("summary") %></p>
                                </div>

                                <div class="section">
                                    <h3>Skills</h3>
                                    <%
                                        JSONArray skills = cv.optJSONArray("skills");
                                        if(skills != null){
                                            for(int i=0;i<skills.length();i++){
                                                JSONObject s = skills.getJSONObject(i);
                                            %>
                                            <div class="mb-2">
                                                <%= s.optString("name") %> - (<%= s.optString("level") %>)
                                            </div>
                                            <% }} %>
                                        </div>

                                        <div class="section">
                                            <h3>Work Experience</h3>
                                            <%
                                                JSONArray exp = cv.optJSONArray("workExperiences");
                                                if(exp != null){
                                                    for(int i=0;i<exp.length();i++){
                                                        JSONObject w = exp.getJSONObject(i);
                                                    %>
                                                    <p>
                                                        <strong><%= w.optString("position") %></strong> - <%= w.optString("companyName") %><br>
                                                        <small><%= w.optString("startDate") %> - <%= w.optString("endDate","Present") %></small><br>
                                                        <%= w.optString("description") %>
                                                    </p>
                                                    <% }} %>
                                                </div>

                                                <div class="section">
                                                    <h3>Education</h3>
                                                    <%
                                                        JSONArray edu = cv.optJSONArray("educationList");
                                                        if(edu != null){
                                                            for(int i=0;i<edu.length();i++){
                                                                JSONObject e = edu.getJSONObject(i);
                                                            %>
                                                            <p>
                                                                <strong><%= e.optString("institution") %></strong><br>
                                                                <%= e.optString("degree") %> - <%= e.optString("fieldOfStudy") %><br>
                                                                <small><%= e.optString("startDate") %> - <%= e.optString("endDate","Present") %></small>
                                                            </p>
                                                            <% }} %>
                                                        </div>

                                                        <div class="section">
                                                            <h3>Additional Info</h3>
                                                            <%
                                                                JSONArray add = cv.optJSONArray("additionalInfos");
                                                                if(add != null){
                                                                    for(int i=0;i<add.length();i++){
                                                                        JSONObject a = add.getJSONObject(i);
                                                                    %>
                                                                    <p>- <%= a.optString("content") %></p>
                                                                    <% }} %>
                                                                </div>

                                                            </div>

                                                        </div>
                                                    </div>

                                                    <% } %>

                                                </body>
                                            </html>