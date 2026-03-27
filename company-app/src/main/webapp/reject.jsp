<%@ page import="java.net.*,java.io.*" %>
<%
    String token = session.getAttribute("token").toString();
    String id = request.getParameter("id");
    
    URL url = new URL("http://localhost:8081/api/internship-applications/" + id);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("PUT");
    conn.setRequestProperty("Authorization", "Bearer " + token);
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);
    
    String json = "{\"status\":\"REJECTED\"}";
    OutputStream os = conn.getOutputStream();
    os.write(json.getBytes("UTF-8"));
    os.flush();
    os.close();
    
    int responseCode = conn.getResponseCode();
    conn.disconnect();
    
    response.sendRedirect("students.jsp");
%>