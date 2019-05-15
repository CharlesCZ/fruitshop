package org.czekalski.fruitshop.services;


import org.czekalski.fruitshop.api.v1.mapper.CustomerMapper;

import org.czekalski.fruitshop.controllers.v1.CustomerController;
import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.fruitshop.repositories.CustomerRepository;
import org.czekalski.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerDTO customerDTO=customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL +"/"+ id;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

      /*  Customer customer=customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        CustomerDTO customerDTO=customerMapper
                .customerToCustomerDTO(customer);
        customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
        return customerDTO;*/

        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO)
                .map(customerDTO1 ->{
                    //set API URL
                    customerDTO1.setCustomerUrl(getCustomerUrl(id));
                            return customerDTO1;
                })
                .orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer=customerMapper.customerDTOToCustomer(customerDTO);

        CustomerDTO returnDto = saveAndReturnDTO(customer);

        return returnDto;

    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer=customerRepository.save(customer);

        CustomerDTO returnDto=customerMapper.customerToCustomerDTO(savedCustomer);

        returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstName()!=null)
            customer.setFirstName(customerDTO.getFirstName());

            if(customerDTO.getLastName()!=null)
                customer.setLastName(customerDTO.getLastName());

            CustomerDTO returnedDto=customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            if(customerDTO.getCustomerUrl()!=null)
                returnedDto.setCustomerUrl(customerDTO.getCustomerUrl());
            else returnedDto.setCustomerUrl(getCustomerUrl(customer.getId()));

            return returnedDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
customerRepository.deleteById(id);
    }


}
