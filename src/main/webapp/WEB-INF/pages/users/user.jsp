<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="u" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>
    <form method="POST" action="${pageContext.request.contextPath}/Users">
        <u:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a href="${pageContext.request.contextPath}/AddUser" class="btn btn-primary btn-ig">Add User</a>
            <button class="btn btn-secondary" type="submit">Invoice</button>
        </u:if>
        <div class="container text-center">
            <u:forEach var="user" items="${users}">
                <div class="row">
                    <div class="col">
                        <u:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                            <input type="checkbox" name="user_ids" value="${user.id}" />
                        </u:if>
                    </div>
                    <div class="col">
                            ${user.username}
                    </div>
                    <div class="col">
                            ${user.email}
                    </div>
                </div>
            </u:forEach>
        </div>
    </form>
    <u:if test="${not empty invoices}">
        <h2>
            Invoices
        </h2>
        <u:forEach var="username" items="${invoices}" varStatus="status">
            ${status.index + 1}. ${username}
            <br />
        </u:forEach>
    </u:if>
</t:pageTemplate>