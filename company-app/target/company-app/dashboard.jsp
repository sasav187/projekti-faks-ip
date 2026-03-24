<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%
    if (session.getAttribute("token") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String username = session.getAttribute("username").toString();
    Long companyId = (Long) session.getAttribute("companyId");
%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Company Dashboard</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            
            body{
                background:#f4f6f9;
            }
            
            .navbar-brand{
                font-weight:bold;
            }
            
            .card-hover:hover{
                transform: scale(1.05);
                transition: 0.3s;
                cursor: pointer;
            }
            
            .card-title{
                font-weight:600;
            }
            
        </style>

    </head>

    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">

                <a class="navbar-brand" href="#">
                    Company
                </a>

                <div class="ms-auto text-white">
                    Welcome, <%= username %>
                    <a href="logout.jsp" class="btn btn-light btn-sm ms-3">Logout</a>
                </div>

            </div>
        </nav>


        <div class="container mt-5">

            <div class="row g-4">

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Internships</h4>
                            <p class="card-text">Manage your internships</p>

                            <a href="manage-internships.jsp" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Students</h4>
                            <p class="card-text">View student applications</p>

                            <a href="students.jsp" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Evaluation</h4>
                            <p class="card-text">Evaluate student performance</p>

                            <a href="evaluate.jsp" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Password</h4>
                            <p class="card-text">Change your password</p>

                            <a href="change-password.jsp" class="btn btn-warning">
                                Change
                            </a>

                        </div>
                    </div>
                </div>

            </div>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>