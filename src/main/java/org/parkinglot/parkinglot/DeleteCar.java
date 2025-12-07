package org.parkinglot.parkinglot;

import com.parking.parkinglot.ejb.CarsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteCars", value = "/DeleteCars")
public class DeleteCar extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] carIdsAsString = request.getParameterValues("car_ids");

        if (carIdsAsString != null && carIdsAsString.length > 0) {
            List<Long> carIds = new ArrayList<>();

            for (String carIdAsString : carIdsAsString) {
                if (carIdAsString != null && !carIdAsString.trim().isEmpty()) {
                    try {
                        carIds.add(Long.parseLong(carIdAsString.trim()));
                    } catch (NumberFormatException e) {
                        // Log error but continue with other IDs
                        System.err.println("Invalid car ID format: " + carIdAsString);
                    }
                }
            }

            if (!carIds.isEmpty()) {
                carsBean.deleteCarsByIds(carIds);
            }
        }

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}