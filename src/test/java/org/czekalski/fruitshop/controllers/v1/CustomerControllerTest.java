package org.czekalski.fruitshop.controllers.v1;

import org.czekalski.fruitshop.api.v1.model.CustomerDTO;
import org.czekalski.fruitshop.services.CustomerService;
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

        mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1=new CustomerDTO();
        customerDTO1.setFirstName("Aaa");
        customerDTO1.setLastName("Aaaaa");
        customerDTO1.setCustomerUrl("/api/v1/customers/1");
        CustomerDTO customerDTO2=new CustomerDTO();
        customerDTO2.setFirstName("Bbbb");
        customerDTO2.setLastName("Bbbbbbb");
        customerDTO2.setCustomerUrl("/api/v1/customers/2");

        List<CustomerDTO> customerDTOList= Arrays.asList(customerDTO1,customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", Matchers.hasSize(2)));

    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO1=new CustomerDTO();
        customerDTO1.setFirstName("Aaa");
        customerDTO1.setLastName("Aaaaa");
        customerDTO1.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(1L)).thenReturn(customerDTO1);

        mockMvc.perform(get("/api/v1/customers/1")
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
retunedCustomer.setCustomerUrl("/api/v1/customers/1");

when(customerService.createNewCustomer(customerDTO1)).thenReturn(retunedCustomer);

        //when/then
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Andrzej")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
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
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }


    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Flintstone");
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",equalTo("Fred")))
                .andExpect(jsonPath("$.lastName",equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));


    }


    @Test
    public void deleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService,times(1)).deleteCustomerById(anyLong());
    }
}