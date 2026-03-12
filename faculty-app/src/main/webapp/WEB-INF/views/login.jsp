<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Faculty Login</title>

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

                        <h3 class="text-center mb-4 form-title">Faculty Login</h3>

                        <form method="post" action="login">

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

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3 text-center">
                                ${error}
                            </div>
                        </c:if>

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