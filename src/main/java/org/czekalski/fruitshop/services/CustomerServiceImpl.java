package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.CategoryMapper;
import org.czekalski.fruitshop.api.v1.mapper.CustomerMapper;
import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.fruitshop.repositories.CustomerRepository;
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
                    customerDTO.setCustomerUrl("/api/v1/customers/"+customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        Customer customer=customerRepository.findById(id).orElseThrow(RuntimeException::new);

        CustomerDTO customerDTO=customerMapper
                .customerToCustomerDTO(customer);
        customerDTO.setCustomerUrl("/api/v1/customers/"+customer.getId());
        return customerDTO;

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

        returnDto.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public void deleteCustomer(CustomerDTO customerDTO) {

    }
}
