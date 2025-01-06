package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Address;
import org.babagroup.repository.AddressRepository;
import org.babagroup.resreq.AddressDto;
import org.babagroup.services.  AddressService;
import org.babagroup.services.UserServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Inject
    AddressRepository addressRepository;

    @Inject
    UserServices userServices;

    @Override
    public Address findAddressByCountryAndStateAndCityAndStreet(String country, String state, String city, String street) {
        return addressRepository.findAddressByCountryAndStateAndCityAndStreet(country, state, city, street).orElseThrow();
    }

    @Override
    public Address findById(String id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new DataError("Data is not found"));
        return address;
    }
}
