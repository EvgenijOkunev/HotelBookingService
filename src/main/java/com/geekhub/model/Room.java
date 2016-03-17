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
    private Integer pricePerNight;

    @Column
    private Integer numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "roomTypeId")
    private RoomType roomType;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

}
