<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Companies</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body { background-color: #f8f9fa; }
            .container { margin-top: 30px; }
            .table thead { background-color: #343a40; color: white; }
            .back-btn { margin-bottom: 15px; }
        </style>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center mb-4">Companies Management</h2>

            <div class="text-center back-btn">
                <a href="dashboard" class="btn btn-secondary">&larr; Back to Dashboard</a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center">${error}</div>
            </c:if>

            <div class="d-flex justify-content-between mb-3">
                <h5>All Companies</h5>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCompanyModal">
                    Add Company
                </button>
            </div>

            <c:if test="${not empty companies}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Username</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th style="width:150px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="company" items="${companies}">
                                <tr>
                                    <td>${company.name}</td>
                                    <td>@${company.username}</td>
                                    <td>${company.description}</td>
                                    <td>
                                        <span class="badge ${company.approved eq 'true' ? 'bg-success' : 'bg-danger'}">
                                            ${company.approved eq 'true' ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm ${company.approved eq 'true' ? 'btn-danger' : 'btn-success'} toggle-status"
                                        data-id="${company.id}"
                                        data-approved="${company.approved}">
                                        ${company.approved eq 'true' ? 'Deactivate' : 'Activate'}
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${empty companies}">
            <div class="alert alert-info text-center">No companies found.</div>
        </c:if>
    </div>

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
                    <input class="form-control mb-2" name="password" type="password" placeholder="Password" required>
                    <textarea class="form-control" name="description" placeholder="Description"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Add</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </form>
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
                        if (res.ok) location.reload();
                        else alert('Failed to update status');
                    });
                });
            });
        </script>

    </body>
</html>