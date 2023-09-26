package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Autowired
    SpotRepository spotRepository1;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
        //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.

        Optional<Reservation> optionalReservation = reservationRepository2.findById(reservationId);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            int bill = reservation.getNumberOfHours()*reservation.getSpot().getPricePerHour();
            if(amountSent<bill){
                throw new Exception("Insufficient Amount");
            }

            mode = mode.toUpperCase();
            PaymentMode paymentMode=null;

            if(mode.equals("CASH")){
                paymentMode = PaymentMode.CASH;
            }else if(mode.equals("CARD")){
                paymentMode = PaymentMode.CARD;
            }else if(mode.equals("UPI")){
                paymentMode = PaymentMode.UPI;
            }else{
                throw new Exception("Payment mode not detected");
            }

            Payment payment = new Payment();
            payment.setPaymentMode(paymentMode);
            payment.setPaymentCompleted(true);
            payment.setReservation(reservation);

            Spot spot = reservation.getSpot();
            spot.setOccupied(false);
            reservation.setPayment(payment);
            spotRepository1.save(spot);
            return  payment;
        }
        return new Payment();
    }
}
