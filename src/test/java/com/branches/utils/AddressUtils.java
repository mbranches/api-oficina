package com.branches.utils;

import com.branches.model.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressUtils {
    public List<Address> newAddressList() {
        Address address1 = Address.builder().id(1L).street("Magalhães Barata").district("Nazaré").city("Belém").state("Pa").build();
        Address address2 = Address.builder().id(2L).street("Gentil").district("Nazaré").city("Belém").state("Pa").build();
        Address address3 = Address.builder().id(2L).street("Br-316").district("Centro").city("Ananindeua").state("Pa").build();

        return new ArrayList<>(List.of(address1, address2, address3));
    }

    public Address newAddressToSave() {
        return Address.builder()
                .id(4L)
                .street("Almirante Barroso")
                .district("São Brás")
                .city("Belém")
                .state("Pa")
                .build();
    }
}
