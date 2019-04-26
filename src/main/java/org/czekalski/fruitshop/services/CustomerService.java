package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    void addCustomer(CustomerDTO customerDTO);

    void deleteCustomer(CustomerDTO customerDTO);
}
