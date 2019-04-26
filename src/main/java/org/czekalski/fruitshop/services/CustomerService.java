package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

     CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

    void deleteCustomer(CustomerDTO customerDTO);
}
