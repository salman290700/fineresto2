package org.babagroup.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Otp {
    @Id
    @Column(nullable = false, updatable = false)
    String id;

    private String otp;

    private String email;

    private Date expiration_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public LocalDateTime getExpiration_date() {
//        return expiration_date;
//    }
//
//    public void setExpiration_date(LocalDateTime expiration_date) {
//        this.expiration_date = expiration_date;
//    }


    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }
}
