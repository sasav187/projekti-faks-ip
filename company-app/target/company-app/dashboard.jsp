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
        <title>Company Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body { padding-top: 70px; }
            @media (max-width: 576px) {
                .container h2 { font-size: 1.5rem; }
                .container p { font-size: 1rem; }
            }
        </style>
    </head>

    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Company Dashboard</a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto text-center">

                        <li class="nav-item">
                            <a class="nav-link" href="manage-internships.jsp">Upravljanje praksama</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="students.jsp">Pregled studenata</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="evaluate.jsp">Ocjenjivanje</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="change-password.jsp">Promjena lozinke</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link text-warning" href="logout.jsp">Logout</a>
                        </li>

                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-4 text-center">
            <h2>Welcome, <%= username %>!</h2>

            <div class="row mt-4">

                <div class="col-12 col-sm-6 col-md-3 mb-3">
                    <a href="manage-internships.jsp" class="btn btn-primary w-100">Upravljanje praksama</a>
                </div>

                <div class="col-12 col-sm-6 col-md-3 mb-3">
                    <a href="students.jsp" class="btn btn-secondary w-100">Pregled studenata</a>
                </div>

                <div class="col-12 col-sm-6 col-md-3 mb-3">
                    <a href="evaluate.jsp" class="btn btn-success w-100">Ocjenjivanje</a>
                </div>

                <div class="col-12 col-sm-6 col-md-3 mb-3">
                    <a href="change-password.jsp" class="btn btn-warning w-100">Promjena lozinke</a>
                </div>

            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>