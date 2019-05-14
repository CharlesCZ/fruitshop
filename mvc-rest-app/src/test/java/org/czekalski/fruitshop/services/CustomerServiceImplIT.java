package org.czekalski.fruitshop.services;


import org.czekalski.fruitshop.api.v1.mapper.CustomerMapper;
import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.bootstrap.Bootstrap;
import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.fruitshop.repositories.CategoryRepository;
import org.czekalski.fruitshop.repositories.CustomerRepository;
import org.czekalski.fruitshop.repositories.VendorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsNot.not;
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }


    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        //save original first name
        String originalFirstName=originalCustomer.getFirstName();
        String originialLastName=originalCustomer.getLastName();

        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id,customerDTO);

      CustomerDTO returnedDto=  customerService.getCustomerById(id);

        assertNotNull(returnedDto);
        assertEquals(updatedName, returnedDto.getFirstName());
        assertThat(originalFirstName, not(equalTo(returnedDto.getFirstName())));
        assertThat(originialLastName, equalTo(returnedDto.getLastName()));

    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {

        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        //save original first name
        String originalFirstName=originalCustomer.getFirstName();
        String originalLastName=originalCustomer.getLastName();

        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id,customerDTO);

        CustomerDTO returnedDto=  customerService.getCustomerById(id);

        assertNotNull(returnedDto);
        assertEquals(updatedName, returnedDto.getLastName());
        assertThat(originalLastName, not(equalTo(returnedDto.getLastName())));
        assertThat(originalFirstName, equalTo(returnedDto.getFirstName()));
        assertThat("/api/v1/customers/"+id,equalTo(returnedDto.getCustomerUrl()));
        Assert.assertEquals("/api/v1/customers/"+id,returnedDto.getCustomerUrl());
    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }

}
