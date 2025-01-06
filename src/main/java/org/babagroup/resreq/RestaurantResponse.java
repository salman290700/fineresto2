package org.babagroup.resreq;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToOne;
import org.babagroup.models.Address;
import org.babagroup.models.ContactInformation;

import java.util.List;

public class RestaurantResponse {
    private String name;
    private String description;
    private String cuisineType;
    private AddressDto address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
    private boolean isOpened;

    public RestaurantResponse(String name, String description, String cuisineType, AddressDto address, ContactInformation contactInformation, String openingHours, List<String> images, boolean isOpened) {
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.address = address;
        this.contactInformation = contactInformation;
        this.openingHours = openingHours;
        this.images = images;
        this.isOpened = isOpened;
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

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
