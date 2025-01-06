package org.babagroup.resreq;

import org.babagroup.models.Address;
import org.babagroup.models.ContactInformation;
import org.babagroup.models.Role;

import java.util.List;
import java.util.Set;

public class UserResponse {
    private String email;
    private List<AddressDto> addresses;
    private ContactInformation contactInformation;


    public UserResponse(String email, List<AddressDto> addresses, ContactInformation contactInformation) {
        this.email = email;
        this.addresses = addresses;
        this.contactInformation = contactInformation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }
}
