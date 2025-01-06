package org.babagroup.models;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import org.babagroup.resreq.AddressDto;
import org.babagroup.utils.AddressUtils;

@Entity
public class Location {
    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    private String longitude;

    private String latitude;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
