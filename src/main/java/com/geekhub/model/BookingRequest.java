package com.geekhub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKING_REQUEST")
public class BookingRequest {

    @Id
    @GeneratedValue
    @Column
    private Integer bookingRequestId;

    @Column
    private Integer bookingRequestNumber;

    @Column
    private Date arrivalDate;

    @Column
    private Date departureDate;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @Column
    private String guestName;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User guest;

    @Column
    private String guestEmail;

    @Column
    private String guestPhone;

    @Column
    private Boolean accepted;

    @Column
    private Boolean rejected;

    public Integer getBookingRequestId() {
        return bookingRequestId;
    }

    public void setBookingRequestId(Integer bookingRequestId) {
        this.bookingRequestId = bookingRequestId;
    }

    public Integer getBookingRequestNumber() {
        return bookingRequestNumber;
    }

    public void setBookingRequestNumber(Integer bookingRequestNumber) {
        this.bookingRequestNumber = bookingRequestNumber;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

}
