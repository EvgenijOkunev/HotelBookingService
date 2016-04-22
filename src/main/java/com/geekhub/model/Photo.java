package com.geekhub.model;

import javax.persistence.*;

@Entity
@Table(name = "PHOTOS")
public class Photo {

    @Id
    @GeneratedValue
    @Column
    private Integer photoId;

    @Column
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @Column
    private Boolean mainPhoto;

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Boolean getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(Boolean mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

}
