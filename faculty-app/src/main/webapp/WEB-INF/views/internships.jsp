<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Internships</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body { background-color: #f8f9fa; min-height: 100vh; margin:0; padding:0; }
            .container { margin-top: 20px; max-width: 100%; padding: 0 10px; }
            .card { border-radius: 12px; box-shadow: 0 6px 15px rgba(0,0,0,0.15); margin-bottom: 20px; transition: transform 0.2s, box-shadow 0.2s; }
            .card:hover { transform: translateY(-3px); box-shadow: 0 8px 20px rgba(0,0,0,0.2); }
            .card-title { font-weight: 600; font-size: 1.25rem; word-wrap: break-word; }
            .card-subtitle { color: #6c757d; font-size: 1rem; word-wrap: break-word; }
            .card-text { font-size: 1.05rem; word-wrap: break-word; }
            .back-btn { margin-bottom: 20px; }
            .card .btn { white-space: normal; word-wrap: break-word; }
            @media (max-width: 768px) {
                .row-cols-md-3 { row-gap: 1.5rem; }
                .card-title { font-size: 1.2rem; }
                .card-subtitle { font-size: 1rem; }
            }
            @media (max-width: 576px) {
                .row-cols-sm-2 { row-gap: 1rem; }
                .card { padding: 15px; }
                .card-title { font-size: 1.15rem; }
                .card-subtitle { font-size: 0.95rem; }
                .card-text { font-size: 1rem; }
                .btn-sm { font-size: 0.9rem; padding: 0.45rem 0.7rem; }
            }
            @media (max-width: 400px) {
                .card-title { font-size: 1.1rem; }
                .card-subtitle { font-size: 0.9rem; }
                .card-text { font-size: 0.95rem; }
                .btn-sm { font-size: 0.85rem; padding: 0.4rem 0.6rem; }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="mb-3 text-center">Internships</h2>
            <div class="text-center back-btn">
                <a href="dashboard" class="btn btn-secondary">&larr; Back to Dashboard</a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center">${error}</div>
            </c:if>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                <c:forEach var="internship" items="${internships}">
                    <div class="col">
                        <div class="card p-3 h-100">
                            <h5 class="card-title">${internship.title}</h5>
                            <h6 class="card-subtitle mb-2">@${internship.companyName}</h6>
                            <p class="card-text mb-2">Description: ${internship.description}</p>
                            <p class="card-text mb-2">Period: ${internship.period}</p>
                            <p class="card-text mb-2">Capacity: ${internship.capacity}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${empty internships}">
                <div class="alert alert-info text-center mt-3">No internships found.</div>
            </c:if>

        </div>

    </body>
</html>