<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>

    <!-- Form for DELETE operation -->
    <form method="POST" action="${pageContext.request.contextPath}/DeleteCars">
        <!-- Action Buttons -->
        <div class="d-flex mb-4">
            <a href="${pageContext.request.contextPath}/AddCar"
               class="btn btn-primary btn-lg px-4 me-2">
                Add Car
            </a>
            <button type="submit" class="btn btn-danger btn-lg px-4">
                Delete Cars
            </button>
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
                            <input type="checkbox" name="car_ids" value="${car.id}">
                        </td>
                        <td>${car.licensePlate}</td>
                        <td>${car.parkingSpot}</td>
                        <td>${car.ownerName}</td>
                        <td>
                            <a class="btn btn-secondary btn-sm"
                               href="${pageContext.request.contextPath}/EditCar?id=${car.id}">
                                Edit Car
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
    </form>

</t:pageTemplate>