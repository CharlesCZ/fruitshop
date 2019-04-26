package org.czekalski.fruitshop.controllers.v1;

import org.czekalski.fruitshop.api.v1.model.CategoryListDTO;
import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.api.v1.model.CustomerListDTO;
import org.czekalski.fruitshop.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


@GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers(){

        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){

        return new ResponseEntity<>(
                customerService.getCustomerById(id),HttpStatus.OK);
    }

@PostMapping
    public ResponseEntity<CustomerDTO> saveNewCustomer(@RequestBody CustomerDTO customerDTO){

        return new ResponseEntity<>(
                customerService.createNewCustomer(customerDTO),HttpStatus.CREATED);
    }
}
