<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Tracking</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">

            <h2 class="text-center mb-3">Worklogs Tracking</h2>

            <div class="text-center mb-3">
                <a href="dashboard" class="btn btn-secondary">&larr; Back</a>
            </div>

            <h4 class="text-center">Work Logs</h4>

            <c:choose>
                <c:when test="${not empty worklogs}">
                    <div class="row">
                        <c:forEach var="w" items="${worklogs}">
                            <div class="col-md-4">
                                <div class="card p-3 mb-3">
                                    <h5>Week: ${w.weekNumber.intValue()}</h5>
                                    <p>Description: ${w.description}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">No worklogs</div>
                </c:otherwise>
            </c:choose>

            <h4 class="text-center mt-4">Evaluations</h4>

            <c:choose>
                <c:when test="${not empty evaluations}">
                    <c:forEach var="e" items="${evaluations}">
                        <div class="card p-3 mb-2">
                            <h5>Grade: ${e.grade.intValue()}</h5>
                            <h6>Evaluator: ${e.evaluatorRole}</h6>
                            <p>Comment: ${e.comment}</p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">No evaluations</div>
                </c:otherwise>
            </c:choose>

            <div class="card p-3 mt-4">

                <c:if test="${empty applicationId or empty studentId or empty internshipId}">
                    <div class="alert alert-danger text-center">
                        Missing parameters - open page correctly
                    </div>
                </c:if>

                <c:if test="${not empty applicationId and not empty studentId and not empty internshipId}">
                    <form method="post" action="worklogs">

                        <input type="hidden" name="applicationId" value="${applicationId}">
                        <input type="hidden" name="studentId" value="${studentId}">
                        <input type="hidden" name="internshipId" value="${internshipId}">

                        <input class="form-control mb-2" name="grade" type="number" min="1" max="5" required>
                        <textarea class="form-control mb-2" name="comment"></textarea>

                        <button class="btn btn-success w-100">Save Evaluation</button>
                    </form>
                </c:if>

            </div>

        </div>
    </body>
</html>