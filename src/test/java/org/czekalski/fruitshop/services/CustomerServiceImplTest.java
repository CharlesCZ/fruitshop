package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.CustomerMapper;
import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.fruitshop.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;


    CustomerService customerService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService=new CustomerServiceImpl(CustomerMapper.INSTANCE,customerRepository);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customerList= Arrays.asList(new Customer(),new Customer(),new Customer());

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOS=customerService.getAllCustomers();

        Assert.assertEquals(3L,customerDTOS.size());

    }

    @Test
    public void getCustomerById() {
        Customer customer=new Customer();
        customer.setLastName("Black");
        customer.setFirstName("Joe");
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO returnedCustomerDTO=customerService.getCustomerById(1L);

        Assert.assertEquals("Black",returnedCustomerDTO.getLastName());
        Assert.assertEquals("Joe",returnedCustomerDTO.getFirstName());
    }

    @Test
    public void addCustomer() {
    }

    @Test
    public void deleteCustomer() {
    }
}