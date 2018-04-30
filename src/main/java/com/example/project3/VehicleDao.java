package com.example.project3;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VehicleDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return;
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        if (entityManager.find(Vehicle.class, vehicle.getId()) != null) {
            entityManager.merge(vehicle);
        }
        return vehicle;
    }
    public Vehicle deleteVehicle(int id) {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
        return vehicle;
    }
    public List<Vehicle> getLatestVehicles() {
        List<Vehicle> latestVehicles = new ArrayList<Vehicle>();
        String queryStr = "SELECT * FROM vehicles ORDER BY id DESC LIMIT 10";
        Query query = entityManager.createNativeQuery(queryStr, Vehicle.class);
        latestVehicles = query.getResultList();
        return latestVehicles;
    }
    /*
     *Get 5 most expensive vehicles
     */
    public List<Vehicle> getExpensiveVehicles() {
        List<Vehicle> expensiveVehicles = new ArrayList<Vehicle>();
        String queryStr = "SELECT * FROM vehicles ORDER BY retailPrice DESC LIMIT 5";
        Query query = entityManager.createNativeQuery(queryStr, Vehicle.class);
        expensiveVehicles = query.getResultList();
        return expensiveVehicles;
    }

    /*
     *Get the newest of the cheapest vehicles
     */
    public Vehicle getNewestCheapVehicle() {
        Vehicle v;
        String queryStr = "SELECT * FROM vehicles ORDER BY retailPrice, year DESC LIMIT 1";
        Query query = entityManager.createNativeQuery(queryStr, Vehicle.class);
        v = (Vehicle) query.getResultList().get(0);

        return v;
    }
}
