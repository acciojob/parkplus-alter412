package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int numberOfHours;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Spot spot;

    public Reservation() {
    }

    public Reservation(int id, int numberOfHours, User user, Spot spot, Payment payment) {
        this.id = id;
        this.numberOfHours = numberOfHours;
        this.user = user;
        this.spot = spot;
        this.payment = payment;
    }

    public Reservation(int id, int numberOfHours) {
        this.id = id;
        this.numberOfHours = numberOfHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    Payment payment;
}
