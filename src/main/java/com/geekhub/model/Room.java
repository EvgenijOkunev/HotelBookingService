package com.geekhub.model;

import javax.persistence.*;

@Entity
@Table(name = "ROOMS")
public class Room {

    @Id
    @GeneratedValue
    @Column
    private Integer roomId;

    @Column
    private Integer roomNumber;

    @Column
    private Double pricePerNight;

    @Column
    private Integer numberOfBeds;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

}
