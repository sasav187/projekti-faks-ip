<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Students</title>
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
            <h2 class="mb-3 text-center">Students</h2>
            <div class="text-center back-btn">
                <a href="dashboard" class="btn btn-secondary">&larr; Back to Dashboard</a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center">${error}</div>
            </c:if>

            <div class="text-center mb-3">
                <button class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#addStudentModal">Add Student</button>
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#uploadCSVModal">Upload CSV</button>
            </div>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                <c:forEach var="s" items="${students}">
                    <div class="col">
                        <div class="card p-3 h-100">
                            <h5 class="card-title">Index: ${s.indexNumber}</h5>
                            <h6 class="card-subtitle mb-2">@${s.username}</h6>
                            <h6 class="card-subtitle mb-2">Faculty: ${s.faculty}</h6>
                            <p class="card-text mb-2">Year: ${s.year}</p>
                            <div class="d-grid gap-2">
                                <button class="btn btn-warning btn-sm"
                                data-bs-toggle="modal"
                                data-bs-target="#editStudentModal"
                                data-id="${s.id}"
                                data-username="${s.username}"
                                data-index="${s.indexNumber}"
                                data-faculty="${s.faculty}"
                                data-year="${s.year}">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn" data-id="${s.id}">Delete</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${empty students}">
                <div class="alert alert-info text-center mt-3">No students found.</div>
            </c:if>

            <div class="modal fade" id="addStudentModal" tabindex="-1">
                <div class="modal-dialog">
                    <form class="modal-content" method="post" action="students">
                        <div class="modal-header">
                            <h5 class="modal-title">Add Student</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input class="form-control mb-2" name="username" placeholder="Username" required>
                            <input class="form-control mb-2" name="password" type="password" placeholder="Password" required>
                            <input class="form-control mb-2" name="indexNumber" placeholder="Index Number" required>
                            <input class="form-control mb-2" name="faculty" placeholder="Faculty">
                            <input class="form-control mb-2" name="year" type="number" placeholder="Year" min="1" max="4" required>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Add</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal fade" id="editStudentModal" tabindex="-1">
                <div class="modal-dialog">
                    <form class="modal-content" method="post" action="students">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Student</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="id" id="edit-id">
                            <input class="form-control mb-2" name="username" id="edit-username" placeholder="Username" required>
                            <input class="form-control mb-2" name="password" id="edit-password" type="password" placeholder="Password">
                            <input class="form-control mb-2" name="indexNumber" id="edit-index" placeholder="Index Number" required>
                            <input class="form-control mb-2" name="faculty" id="edit-faculty" placeholder="Faculty">
                            <input class="form-control mb-2" name="year" id="edit-year" type="number" placeholder="Year" min="1" max="4">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Update</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal fade" id="uploadCSVModal" tabindex="-1">
                <div class="modal-dialog">
                    <form class="modal-content" method="post" action="students" enctype="multipart/form-data">
                        <div class="modal-header">
                            <h5 class="modal-title">Upload Students CSV</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="file" name="file" class="form-control" accept=".csv" required>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Upload</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            var editModal = document.getElementById('editStudentModal');
            editModal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;
                document.getElementById('edit-id').value = button.getAttribute('data-id');
                document.getElementById('edit-username').value = button.getAttribute('data-username');
                document.getElementById('edit-index').value = button.getAttribute('data-index');
                document.getElementById('edit-faculty').value = button.getAttribute('data-faculty');
                document.getElementById('edit-year').value = button.getAttribute('data-year');
                document.getElementById('edit-password').value = "";
            });
            
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                    fetch('students?id=' + btn.dataset.id, { method: 'DELETE' })
                    .then(() => location.reload());
                });
            });
        </script>
    </body>
</html>