package org.czekalski.fruitshop.api.v1.mapper;

import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final String NAME = "Pan samochodzik";
    VendorMapper vendorMapper=VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO(){
    //
        Vendor vendor=new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);

        VendorDTO vendorDTO=vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(),vendorDTO.getName());

    }

    @Test
    public void vendorDtoToVendor(){
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor=vendorMapper.vendorDtoToVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),vendor.getName());


    }
}