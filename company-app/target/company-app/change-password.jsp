<%@ page import="java.net.*,java.io.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String token = session.getAttribute("token").toString();
    String message = null;
    boolean success = false;
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!newPassword.equals(confirmPassword)) {
            message = "Passwords do not match";
            } else {
                
                try {
                    URL url = new URL("http://localhost:8081/api/users/change-password");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    
                    String json = "{"
                    + "\"oldPassword\":\"" + oldPassword + "\","
                    + "\"newPassword\":\"" + newPassword + "\","
                    + "\"confirmPassword\":\"" + confirmPassword + "\""
                    + "}";
                    
                    OutputStream os = conn.getOutputStream();
                    os.write(json.getBytes());
                    os.close();
                    
                    int status = conn.getResponseCode();
                    
                    if (status == 200) {
                        success = true;
                        message = "Password changed successfully";
                        session.invalidate();
                        } else {
                            message = "Error changing password";
                        }
                        
                        } catch (Exception e) {
                            message = "Error: " + e.getMessage();
                        }
                    }
                }
            %>

            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Change Password</title>
                    <meta name="viewport" content="width=device-width, initial-scale=1">

                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

                    <style>
                        body {
                            background: linear-gradient(135deg, #4e73df, #224abe);
                            min-height: 100vh;
                        }
                        
                        .card {
                            border-radius: 15px;
                            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                        }
                        
                        .toggle-password {
                            cursor: pointer;
                        }
                    </style>
                </head>

                <body>

                    <div class="container d-flex justify-content-center align-items-center vh-100">
                        <div class="card p-4 w-100" style="max-width: 420px;">

                            <h4 class="text-center mb-4">Change Password</h4>

                            <form method="post">

                                <!-- OLD PASSWORD -->
                                <div class="mb-3 position-relative">
                                    <input type="password" id="oldPassword" name="oldPassword" class="form-control" placeholder="Old password" required>
                                    <i class="bi bi-eye-slash position-absolute top-50 end-0 translate-middle-y me-3 toggle-password" onclick="togglePassword('oldPassword', this)"></i>
                                </div>

                                <!-- NEW PASSWORD -->
                                <div class="mb-3 position-relative">
                                    <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="New password" required>
                                    <i class="bi bi-eye-slash position-absolute top-50 end-0 translate-middle-y me-3 toggle-password" onclick="togglePassword('newPassword', this)"></i>
                                </div>

                                <!-- CONFIRM PASSWORD -->
                                <div class="mb-3 position-relative">
                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Confirm password" required>
                                    <i class="bi bi-eye-slash position-absolute top-50 end-0 translate-middle-y me-3 toggle-password" onclick="togglePassword('confirmPassword', this)"></i>
                                </div>

                                <button class="btn btn-primary w-100">Change Password</button>
                            </form>

                            <% if (message != null) { %>
                            <div class="alert <%= success ? "alert-success" : "alert-danger" %> mt-3 text-center">
                                <%= message %>
                            </div>
                            <% } %>

                            <% if (success) { %>
                            <script>
                                setTimeout(function() {
                                    window.location.href = "login.jsp";
                                }, 1500);
                            </script>
                            <% } %>

                            <a href="dashboard.jsp" class="btn btn-outline-secondary w-100 mt-2">Back</a>

                        </div>
                    </div>

                    <script>
                        function togglePassword(fieldId, icon) {
                            const input = document.getElementById(fieldId);
                            
                            if (input.type === "password") {
                                input.type = "text";
                                icon.classList.remove("bi-eye-slash");
                                icon.classList.add("bi-eye");
                                } else {
                                    input.type = "password";
                                    icon.classList.remove("bi-eye");
                                    icon.classList.add("bi-eye-slash");
                                }
                            }
                        </script>

                    </body>
                </html>