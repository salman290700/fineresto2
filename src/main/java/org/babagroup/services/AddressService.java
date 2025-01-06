package org.babagroup.services;

import org.babagroup.models.Address;
import org.babagroup.resreq.AddressDto;

import java.util.List;

public interface AddressService {
    Address findAddressByCountryAndStateAndCityAndStreet(String country, String state, String city, String street);
    Address findById(String id);
}
