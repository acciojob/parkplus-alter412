package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<User> optionalUser = userRepository3.findById(userId);
        if(!optionalUser.isPresent()){
            throw new Exception("Cannot make reservation");
        }
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if(!optionalParkingLot.isPresent()){
            throw new Exception("Cannot make reservation");
        }


        ParkingLot parkingLot = optionalParkingLot.get();
        User user = optionalUser.get();

        Spot spot = null;

        for(Spot x: parkingLot.getSpotList()){
            int t;
            if(x.getSpotType()==SpotType.TWO_WHEELER){
                t=2;
            }else if(x.getSpotType()==SpotType.FOUR_WHEELER){
                t=4;
            }else{
                t=Integer.MAX_VALUE;
            }
            if(!x.getOccupied() && t>=numberOfWheels){
                if(spot==null || x.getPricePerHour()< spot.getPricePerHour()){
                    spot=x;
                }
            }
        }

        if(spot == null){
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSpot(spot);
        reservation.setNumberOfHours(timeInHours);

        Reservation savedReservation = reservationRepository3.save(reservation);
        user.getReservationList().add(savedReservation);
        userRepository3.save(user);
        spot.setOccupied(true);
        spot.getReservationList().add(savedReservation);
        spotRepository3.save(spot);

        return savedReservation;
    }
}
