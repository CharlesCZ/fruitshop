package org.czekalski.fruitshop.controllers.v1;

import org.czekalski.fruitshop.api.RestResponseEntityExceptionHandler;

import org.czekalski.fruitshop.services.CustomerService;
import org.czekalski.fruitshop.services.ResourceNotFoundException;
import org.czekalski.model.CustomerDTO;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.czekalski.fruitshop.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc= MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1=new CustomerDTO();
        customerDTO1.setFirstName("Aaa");
        customerDTO1.setLastName("Aaaaa");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL+"/1");
        CustomerDTO customerDTO2=new CustomerDTO();
        customerDTO2.setFirstName("Bbbb");
        customerDTO2.setLastName("Bbbbbbb");
        customerDTO2.setCustomerUrl("/api/v1/customers/2");

        List<CustomerDTO> customerDTOList= Arrays.asList(customerDTO1,customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", Matchers.hasSize(2)));

    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO1=new CustomerDTO();
        customerDTO1.setFirstName("Aaa");
        customerDTO1.setLastName("Aaaaa");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL+"/1");

        when(customerService.getCustomerById(1L)).thenReturn(customerDTO1);

        mockMvc.perform(get(CustomerController.BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",equalTo("Aaa")));
    }


    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO1=new CustomerDTO();
        customerDTO1.setFirstName("Andrzej");
        customerDTO1.setLastName("Lepper");


CustomerDTO retunedCustomer=new CustomerDTO();
retunedCustomer.setFirstName(customerDTO1.getFirstName());
retunedCustomer.setLastName(customerDTO1.getLastName());
retunedCustomer.setCustomerUrl(CustomerController.BASE_URL+"/1");

when(customerService.createNewCustomer(any())).thenReturn(retunedCustomer);

        //when/then
        mockMvc.perform(post("/api/v1/customers/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.firstName", equalTo("Andrzej")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL+"/1")));
    }


    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL+"/1");

        when(customerService.saveCustomerByDTO(anyLong(), any())).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL+"/1")));
    }


    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Flintstone");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL+"/1");

        when(customerService.patchCustomer(anyLong(),any())).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(CustomerController.BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",equalTo("Fred")))
                .andExpect(jsonPath("$.lastName",equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL+"/1")));


    }


    @Test
    public void deleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL+"/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService,times(1)).deleteCustomerById(anyLong());
    }


    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL+"/221")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //verify(customerService,times(1)).deleteCustomerById(anyLong());
    }
}