package org.babagroup.resreq;

import org.babagroup.models.Address;
import org.babagroup.models.ContactInformation;

import java.util.List;

public class RestoOwnerCreateReq {
//    User data
    private String email;
    private String password;
    private String street;
    private String state;
    private String country;
    private ContactInformation contactInformation;
    public List<String> images;
    public String profile;

//    Resto Data
    private String restaurant_name;
    private String resto_desc;
    private List<String> images_resto;
    private String cuisineType;
    private Address resto_address;
}
