package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setSpotList(new ArrayList<>());
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
        if(optionalParkingLot.isPresent()){
         ParkingLot parkingLot = optionalParkingLot.get();
         Spot spot = new Spot();
         spot.setOccupied(false);
         spot.setParkingLot(parkingLot);
         spot.setReservationList(new ArrayList<>());
         spot.setPricePerHour(pricePerHour);
         if(numberOfWheels<=2){
             spot.setSpotType(SpotType.TWO_WHEELER);
         }else if(numberOfWheels<=4){
             spot.setSpotType(SpotType.FOUR_WHEELER);
         }else{
             spot.setSpotType(SpotType.OTHERS);
         }
         parkingLot.getSpotList().add(spot);
         parkingLotRepository1.save(parkingLot);
         return  spot;
        }
        return new Spot();
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Optional<Spot> optionalSpot = spotRepository1.findById(spotId);
        if(optionalSpot.isPresent()){
            Spot spot = optionalSpot.get();
            spot.setPricePerHour(pricePerHour);
            Spot savedSpot = spotRepository1.save(spot);
           return  spot;
        }
        Spot spot = new Spot();
        spot.setPricePerHour(5);
        spot.setSpotType(SpotType.TWO_WHEELER);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
