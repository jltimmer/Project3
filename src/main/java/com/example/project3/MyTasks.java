package com.example.project3;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MyTasks {

    RestTemplate restTemplate = new RestTemplate();

    @Logged @Timed @Counted
    @Scheduled(fixedRate=3000)
    public void addVehicle() {
        String url = "http://localhost:8080/addVehicle";
        Vehicle newVehicle = new Vehicle(RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1986, 2016), RandomUtils.nextInt(15000, 45000));
        restTemplate.postForObject(url, newVehicle, Vehicle.class);
    }

    @Logged @Timed @Counted
    @Scheduled(fixedRate=5000)
    public void deleteVehicle() {
        int randID = RandomUtils.nextInt(1, 20);
        String url = "http://localhost:8080/deleteVehicle/" + randID;
        restTemplate.delete(url);
    }

    @Logged @Timed @Counted
    @Scheduled(cron="0 5 * * * *")
    public void updateVehicle() {
        String url = "http://localhost:8080/updateVehicle";
        int randID = RandomUtils.nextInt(1, 20);
        Vehicle newVehicle = new Vehicle(randID, RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1986, 2016), RandomUtils.nextInt(15000, 45000));
        restTemplate.put(url, newVehicle);
    }

    @Logged @Timed @Counted
    @Scheduled(cron="15 * * * * *")
    public void getLatestVehicles() {
        String getUrl = "http://localhost:8080/getLatestVehicles";
        List<Vehicle> vehicles = restTemplate.getForObject(getUrl, List.class);
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            System.out.println(vehicles.get(i));
        }
    }

    @Logged @Timed @Counted
    @Scheduled(cron="30 * * * * *")
    public void getExpensiveVehicles() {
        String getUrl = "http://localhost:8080/getExpensiveVehicles";
        List<Vehicle> vehicles = restTemplate.getForObject(getUrl, List.class);
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println(vehicles.get(i));
        }
    }

    @Logged @Timed @Counted
    @Scheduled(fixedRate=5100)
    public void getNewestCheapVehicle() {
        String getUrl = "http://localhost:8080/getNewestCheapVehicle";
        System.out.println(restTemplate.getForObject(getUrl, Vehicle.class));
    }
}