package com.geekhub.model;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.SQLException;
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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Blob description;

    @Column
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

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

    public Blob getDescription() {
        return description;
    }

    public void setDescription(Blob description) {
        this.description = description;
    }

    public String getStringDescription() throws SQLException, IOException {
        String s;
        InputStream inStream = description.getBinaryStream();
        InputStreamReader inStreamReader = new InputStreamReader(inStream, "UTF-8");
        BufferedReader reader = new BufferedReader(inStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}
