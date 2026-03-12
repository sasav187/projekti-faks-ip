<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Companies</title>
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
            <h2 class="mb-3 text-center">Companies</h2>
            <div class="text-center back-btn">
                <a href="dashboard" class="btn btn-secondary">&larr; Back to Dashboard</a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center">${error}</div>
            </c:if>

            <div class="text-center mb-3">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCompanyModal">Add Company</button>
            </div>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                <c:forEach var="company" items="${companies}">
                    <div class="col">
                        <div class="card p-3 h-100">
                            <h5 class="card-title">${company.name}</h5>
                            <h6 class="card-subtitle mb-2">@${company.username}</h6>
                            <p class="card-text mb-2">${company.description}</p>
                            <button class="btn btn-sm ${company.approved eq 'true' ? 'btn-success' : 'btn-danger'} toggle-status"
                            data-id="${company.id}" data-approved="${company.approved}">
                            ${company.approved eq 'true' ? 'Approved' : 'Pending'}
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty companies}">
            <div class="alert alert-info text-center mt-3">No companies found.</div>
        </c:if>

        <div class="modal fade" id="addCompanyModal" tabindex="-1">
            <div class="modal-dialog">
                <form class="modal-content" method="post" action="companies">
                    <div class="modal-header">
                        <h5 class="modal-title">Add Company</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <input class="form-control mb-2" name="name" placeholder="Company Name" required>
                        <input class="form-control mb-2" name="username" placeholder="Username" required>
                        <input class="form-control mb-2" name="password" placeholder="Password" type="password" required>
                        <textarea class="form-control" name="description" placeholder="Description"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Add</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelectorAll('.toggle-status').forEach(btn => {
            btn.addEventListener('click', () => {
                const id = btn.dataset.id;
                const approved = btn.dataset.approved === 'true' ? false : true;
                fetch('http://localhost:8081/api/companies/' + id + '/status', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ approved: approved })
                    }).then(res => {
                        if(res.ok) location.reload();
                        else alert('Failed to update status');
                    });
                });
            });
        </script>
    </body>
</html>