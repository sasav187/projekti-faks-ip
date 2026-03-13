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
            conn.setDoOutput(true);
            
            String json = "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
            
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
            
            int status = conn.getResponseCode();
            
            if (status == 200) {
                
                BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
                );
                
                String line;
                StringBuilder body = new StringBuilder();
                
                while ((line = br.readLine()) != null) {
                    body.append(line);
                }
                
                br.close();
                
                JsonObject obj = JsonParser.parseString(body.toString()).getAsJsonObject();
                String role = obj.get("role").getAsString();
                
                if ("COMPANY".equals(role)) {
                    
                    session.setAttribute("token", obj.get("token").getAsString());
                    session.setAttribute("username", username);
                    
                    response.sendRedirect("dashboard.jsp");
                    return;
                    
                    } else {
                        
                        error = "Access denied";
                        
                    }
                    
                    } else {
                        
                        error = "Invalid username or password";
                        
                    }
                    
                    } catch(Exception e) {
                        
                        error = "Login failed";
                        
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
                        
                        @media (max-width:576px){
                            .login-card{
                                padding:25px;
                            }
                        }
                        
                        .form-title{
                            font-weight:600;
                        }
                        
                        .show-password{
                            font-size:0.9rem;
                        }
                    </style>

                </head>

                <body>

                    <div class="container h-100">
                        <div class="row justify-content-center align-items-center vh-100">

                            <div class="col-12 col-sm-10 col-md-6 col-lg-4">

                                <div class="login-card">

                                    <h3 class="text-center mb-4 form-title">Company Login</h3>

                                    <form method="post" action="login.jsp">

                                        <div class="mb-3">
                                            <label class="form-label">Username</label>
                                            <input type="text" class="form-control form-control-lg" name="username" required>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label">Password</label>
                                            <input type="password" class="form-control form-control-lg" id="password" name="password" required>

                                            <div class="form-check mt-2 show-password">
                                                <input class="form-check-input" type="checkbox" id="showPassword">
                                                <label class="form-check-label" for="showPassword">
                                                    Show password
                                                </label>
                                            </div>

                                        </div>

                                        <button type="submit" class="btn btn-primary btn-lg w-100">
                                            Login
                                        </button>

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

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                    <script>
                        const pwd=document.getElementById('password');
                        const show=document.getElementById('showPassword');
                        
                        show.addEventListener('change',()=>{
                            pwd.type=show.checked?'text':'password';
                        });
                    </script>

                </body>
            </html>