package com.geekhub.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "HOTELS")
public class Hotel {

    @Id
    @GeneratedValue
    @Column
    private Integer hotelId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> rooms;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}
