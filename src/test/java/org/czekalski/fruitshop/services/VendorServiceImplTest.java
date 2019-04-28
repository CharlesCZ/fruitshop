package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.VendorMapper;
import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.domain.Vendor;
import org.czekalski.fruitshop.repositories.VendorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    public static final String NAME = "Pan samochodzik";
    @Mock
    VendorRepository vendorRepository;

   VendorService vendorService;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService=new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendorList= Arrays.asList(new Vendor(),new Vendor(),new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorDTOS=vendorService.getAllVendors();

        Assert.assertEquals(3,vendorDTOS.size());

    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor=new Vendor();
        vendor.setName(NAME);
        vendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        //when
        VendorDTO returnedDto=vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(NAME,returnedDto.getName());
        assertEquals("/api/v1/vendors/1",returnedDto.getVendorUrl());



    }

    @Test
    public void deleteVendorById() {


        //when
        vendorService.deleteVendorById(1L);

        //then
        then(vendorRepository).should().deleteById(anyLong());
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor=new Vendor();
        vendor.setName(NAME);
        vendor.setId(1L);

        //mockito BDD syntax
       given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO returnedDto=vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        //JUnit Assert that with matchers
        assertThat(returnedDto.getName(),equalTo(NAME));
        //JUnit Assert
        assertEquals(NAME,returnedDto.getName());
        assertEquals("/api/v1/vendors/1",returnedDto.getVendorUrl());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound(){
        //given
        //mockito BBD syntax since mockito 1.10.0
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO=vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());


    }

    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO=new VendorDTO();

        vendorDTO.setName(NAME);

        Vendor vendor=new Vendor();
        vendor.setName(NAME);
        vendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        //when
        VendorDTO returnedDto=vendorService.saveVendorByDTO(1L,vendorDTO);

        //then
        assertEquals(NAME,returnedDto.getName());
        assertEquals("/api/v1/vendors/1",returnedDto.getVendorUrl());

    }


    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor=new Vendor();
        vendor.setName(NAME);
        vendor.setId(1L);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO returnedDto=vendorService.patchVendor(1L,vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().findById(anyLong());
        then(vendorRepository).should(times(1)).save(any(Vendor.class));
        assertThat(returnedDto.getVendorUrl(),containsString("1"));

    }


}