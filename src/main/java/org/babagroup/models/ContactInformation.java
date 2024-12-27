package org.babagroup.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContactInformation {
    private String mobile;
    private String twitter;
    private String instagram;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
