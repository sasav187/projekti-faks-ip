<%@ page import="java.net.*,java.io.*" %>
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
    
    try {
        URL url = new URL("http://localhost:8081/api/internships/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        
        int responseCode = conn.getResponseCode();
        if (responseCode != 200 && responseCode != 204) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder respError = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) respError.append(line);
            br.close();
        %>
        <h3>Greska pri brisanju prakse:</h3>
        <pre><%= respError.toString() %></pre>
        <a href="manage-internships.jsp" class="btn btn-secondary mt-2">Nazad</a>
        <%
            return;
        }
        } catch (Exception e) {
        %>
        <h3>Greska pri brisanju prakse:</h3>
        <pre><%= e.getMessage() %></pre>
        <a href="manage-internships.jsp" class="btn btn-secondary mt-2">Nazad</a>
        <%
            return;
        }
        
        response.sendRedirect("manage-internships.jsp");
    %>