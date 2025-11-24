<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>
    <div class="container text-center">
        <c:forEach var="user" items="${user}">
            <div class="row">
                <div class="col">
                        ${users.username}
                </div>
            </div>
        </c:forEach>
    </div>
</t:pageTemplate>
