package org.babagroup.repository;

import org.babagroup.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findAddressByCountryAndStateAndCityAndStreet(String country, String state, String city, String street);
    Optional<Address> findById(String id);
}
