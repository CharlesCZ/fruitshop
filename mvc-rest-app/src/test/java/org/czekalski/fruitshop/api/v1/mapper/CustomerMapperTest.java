package org.czekalski.fruitshop.api.v1.mapper;


import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.model.CustomerDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {


    CustomerMapper customerMapper=CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer=new Customer();
        customer.setId(1L);
        customer.setFirstName("Joe");
        customer.setLastName("Biden");


        //when
        CustomerDTO returnedCustomerDTO=customerMapper.customerToCustomerDTO(customer);


        Assert.assertEquals("Joe",returnedCustomerDTO.getFirstName());
        Assert.assertEquals("Biden",returnedCustomerDTO.getLastName());

    }
}