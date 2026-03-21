<%@ page import="java.io.*,java.net.*,com.google.gson.*" %>
<%
    String error = null;
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            
            URL url = new URL("http://localhost:8081/api/auth/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("utf-8"));
            os.close();
            
            int status = conn.getResponseCode();
            
            BufferedReader br;
            
            if (status >= 200 && status < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    body.append(line);
                }
                br.close();
                
                if (status == 200) {
                    
                    JsonObject obj = JsonParser.parseString(body.toString()).getAsJsonObject();
                    
                    String role = obj.get("role").getAsString();
                    String token = obj.get("token").getAsString();
                    
                    String[] parts = token.split("\\.");
                    String payloadJson = new String(java.util.Base64.getDecoder().decode(parts[1]));
                    JsonObject payload = JsonParser.parseString(payloadJson).getAsJsonObject();
                    
                    Long companyId = null;
                    
                    if (payload.has("companyId")) {
                        companyId = payload.get("companyId").getAsLong();
                    }
                    
                    if ("COMPANY".equals(role)) {
                        
                        session.setAttribute("token", token);
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);
                        
                        if (companyId != null) {
                            session.setAttribute("companyId", companyId);
                        }
                        
                        response.sendRedirect("dashboard.jsp");
                        return;
                        
                        } else {
                            error = "Access denied";
                        }
                        
                        } else if (status == 403) {
                            error = "Company account deactivated";
                            
                            } else {
                                error = "Invalid username or password";
                            }
                            
                            } catch (Exception e) {
                                error = "Login failed: " + e.getMessage();
                            }
                        }
                    %>

                    <!DOCTYPE html>
                    <html>
                        <head>
                            <meta charset="UTF-8">
                            <title>Company Login</title>
                            <meta name="viewport" content="width=device-width, initial-scale=1">

                            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

                            <style>
                                body{
                                    background: linear-gradient(135deg,#4e73df,#224abe);
                                    min-height:100vh;
                                }
                                .login-card{
                                    background:white;
                                    border-radius:12px;
                                    box-shadow:0 10px 30px rgba(0,0,0,0.25);
                                    padding:40px;
                                }
                            </style>
                        </head>

                        <body>

                            <div class="container h-100">
                                <div class="row justify-content-center align-items-center vh-100">

                                    <div class="col-md-4">

                                        <div class="login-card">

                                            <h3 class="text-center mb-4">Company Login</h3>

                                            <form method="post">

                                                <div class="mb-3">
                                                    <input type="text" class="form-control" name="username" placeholder="Username" required>
                                                </div>

                                                <div class="mb-3">
                                                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>

                                                    <div class="form-check mt-2">
                                                        <input class="form-check-input" type="checkbox" id="showPassword">
                                                        <label class="form-check-label" for="showPassword">
                                                            Show password
                                                        </label>
                                                    </div>
                                                </div>

                                                <button type="submit" class="btn btn-primary w-100">Login</button>

                                            </form>

                                            <% if (error != null) { %>
                                            <div class="alert alert-danger mt-3 text-center">
                                                <%= error %>
                                            </div>
                                            <% } %>

                                        </div>

                                    </div>

                                </div>
                            </div>

                            <script>
                                const pwd = document.getElementById("password");
                                const checkbox = document.getElementById("showPassword");
                                
                                checkbox.addEventListener("change", function() {
                                    pwd.type = this.checked ? "text" : "password";
                                });
                            </script>

                        </body>
                    </html>