<%@ page import="java.net.*,java.io.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    String id = request.getParameter("id");
    String status = request.getParameter("status"); // "ACCEPTED" ili "REJECTED"
    
    if(id != null && status != null) {
        try {
            URL url = new URL("http://localhost:8081/api/internship-applications/" + id + "/status?status=" + status);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            
            int responseCode = conn.getResponseCode();
            conn.disconnect();
            } catch(Exception e) {
                out.println("Error updating status: " + e.getMessage());
            }
        }
        
        response.sendRedirect("students.jsp");
    %>