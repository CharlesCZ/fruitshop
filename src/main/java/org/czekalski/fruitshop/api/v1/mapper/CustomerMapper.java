package org.czekalski.fruitshop.api.v1.mapper;

import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE= Mappers.getMapper(CustomerMapper.class);


    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

}
