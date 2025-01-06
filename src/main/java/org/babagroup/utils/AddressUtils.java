package org.babagroup.utils;

import jakarta.inject.Inject;
import org.babagroup.models.Address;
import org.babagroup.repository.AddressRepository;
import org.babagroup.resreq.AddressDto;
import org.babagroup.services.UserServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddressUtils {

    @Inject
    AddressRepository addressRepository;

    @Inject
    UserServices userServices;

    public AddressDto mapToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        return addressDto;
    }

    public List<AddressDto> dtoToList(List<Address> addressList) {
        List<AddressDto> dto = new ArrayList<>();
        for (Address address: addressList) {
            dto.add(mapToDto(address));
        }
        return dto;
    }

    public Address mapToObject(AddressDto dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setStreet(dto.getStreet());
        address.setCountry(dto.getCountry());
        return addressRepository.save(address);
    }
}
