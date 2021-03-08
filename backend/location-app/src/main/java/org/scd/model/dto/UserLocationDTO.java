package org.scd.model.dto;

import java.util.Date;

public class UserLocationDTO {
    private String longitude;
    private String latitude;
    private Date creationDate;
    private String email;

    public UserLocationDTO(String latitude, String longitude, String email) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.creationDate = new Date();
        this.email = email;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
