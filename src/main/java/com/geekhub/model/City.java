package com.geekhub.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CITIES")
public class City {

    @Id
    @GeneratedValue
    @Column
    private Integer cityId;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Hotel> hotels;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
