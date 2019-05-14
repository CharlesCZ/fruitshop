package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.VendorMapper;
import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.bootstrap.Bootstrap;
import org.czekalski.fruitshop.domain.Vendor;
import org.czekalski.fruitshop.repositories.CategoryRepository;
import org.czekalski.fruitshop.repositories.CustomerRepository;
import org.czekalski.fruitshop.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Vendor Data");
        System.out.println(vendorRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data

        vendorService = new VendorServiceImpl(vendorRepository,VendorMapper.INSTANCE);
    }



    @Test
    public void patchVendor() {
        String updatedName="UpdatedName";
        long id=getVendorsIdValue();

        Vendor originalVendor=vendorRepository.getOne(id);

        String originalName=originalVendor.getName();

        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id,vendorDTO);

        VendorDTO retunedDto=vendorService.getVendorById(id);

        assertNotNull(retunedDto);
        assertEquals(updatedName,retunedDto.getName());
        assertThat("/api/v1/vendors/"+id,equalTo(retunedDto.getVendorUrl()));





    }

    private Long getVendorsIdValue(){
        List<Vendor> vendors=vendorRepository.findAll();

        System.out.println("Vendors Found: " + vendors.size());

        //return first id
        return vendors.get(0).getId();
    }
}
