<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>

    <!-- Form for DELETE operation -->
    <form method="POST" action="${pageContext.request.contextPath}/DeleteCars">
        <!-- Action Buttons -->
        <div class="d-flex mb-4">
            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                <a href="${pageContext.request.contextPath}/AddCar" class="btn btn-primary btn-ig">Add Car</a>
                <button class="btn btn-danger" type="submit">Delete Cars</button>
            </c:if>
        </div>

        <!-- Cars Table with Checkboxes -->
        <div class="container text-center">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Select</th>
                    <th scope="col">License Plate</th>
                    <th scope="col">Parking Spot</th>
                    <th scope="col">Owner</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>
                            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                                <input type="checkbox" name="car_ids" value="${car.id}" />
                            </c:if>
                        </td>
                        <td>${car.licensePlate}</td>
                        <td>${car.parkingSpot}</td>
                        <td>${car.ownerName}</td>
                        <td>
                            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
    </form>

</t:pageTemplate>