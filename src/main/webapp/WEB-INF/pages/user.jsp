<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <!-- Debug info -->
    <div class="alert alert-info">
        Number of users: ${users.size()}
    </div>

    <div class="container text-center">
        <c:choose>
            <c:when test="${empty users}">
                <div class="alert alert-warning">No users found!</div>
            </c:when>
            <c:otherwise>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <div class="row">
                        <div class="col">
                            <strong>User ${status.index + 1}:</strong> ${user.username}
                        </div>
                        <div class="col">
                            ID: ${user.id}
                        </div>
                    </div>
                    <hr/>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</t:pageTemplate>