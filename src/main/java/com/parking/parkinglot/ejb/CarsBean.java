package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    CarsBean carsBean;

    public List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            dtos.add(carDto);
        }
        return dtos;
    }
    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public CarDto findById(Long carId) {
        LOG.info("findById for car ID: " + carId);
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car == null) {
                LOG.warning("Car not found with ID: " + carId);
                return null;
            }

            // Create CarDto using the existing car data
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner() != null ? car.getOwner().getUsername() : "Unknown"
            );
            return carDto;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("updateCar - ID: " + carId +
                ", License: " + licensePlate +
                ", Spot: " + parkingSpot +
                ", Owner: " + ownerId);
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car == null) {
                LOG.warning("Car not found for update: " + carId);
                throw new EJBException("Car not found with ID: " + carId);
            }

            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            if (ownerId != null) {
                User newOwner = entityManager.find(User.class, ownerId);
                if (newOwner == null) {
                    LOG.warning("Owner not found with ID: " + ownerId);
                    throw new EJBException("Owner not found with ID: " + ownerId);
                }

                User oldOwner = car.getOwner();
                if (oldOwner != null) {
                    oldOwner.getCars().remove(car);
                }

                car.setOwner(newOwner);
                newOwner.getCars().add(car);
            }
            LOG.info("Car updated successfully: " + carId);

        } catch (Exception ex) {
            LOG.severe("Error updating car " + carId + ": " + ex.getMessage());
            throw new EJBException(ex);
        }
    }
    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }
}
