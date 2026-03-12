<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Faculty Dashboard</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            
            body{
                background:#f4f6f9;
            }
            
            .navbar-brand{
                font-weight:bold;
            }
            
            .card-hover:hover{
                transform:scale(1.03);
                transition:0.3s;
                cursor:pointer;
            }
            
        </style>

    </head>

    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">

                <a class="navbar-brand" href="dashboard">
                    Faculty
                </a>

                <div class="ms-auto text-white">
                    Welcome, ${username}
                    <a href="login" class="btn btn-light btn-sm ms-3">Logout</a>
                </div>

            </div>
        </nav>


        <div class="container mt-5">

            <div class="row g-4">

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Companies</h4>
                            <p class="card-text">View partner companies</p>

                            <a href="companies" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Internships</h4>
                            <p class="card-text">View all available internships</p>

                            <a href="internships" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Students</h4>
                            <p class="card-text">View registered students</p>

                            <a href="students" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow card-hover">
                        <div class="card-body text-center">

                            <h4 class="card-title">Worklogs</h4>
                            <p class="card-text">View student worklogs</p>

                            <a href="worklogs" class="btn btn-primary">
                                Open
                            </a>

                        </div>
                    </div>
                </div>

            </div>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>