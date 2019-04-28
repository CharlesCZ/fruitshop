package org.czekalski.fruitshop.controllers.v1;

import org.czekalski.fruitshop.api.RestResponseEntityExceptionHandler;
import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.services.VendorService;

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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class VendorControllerTest {

    @Mock
 VendorService vendorService;

    @InjectMocks
  VendorController vendorController;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc= MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        List<VendorDTO> vendorList= Arrays.asList(new VendorDTO(),new VendorDTO(),new VendorDTO());

        when(vendorService.getAllVendors()).thenReturn(vendorList);

        mockMvc.perform(get(VendorController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", Matchers.hasSize(3)));


    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName("Pan samochodzik");

        VendorDTO returnedDto=new VendorDTO();
        returnedDto.setName("Pan samochodzik");
        returnedDto.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(post(VendorController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo("Pan samochodzik")))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));



    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL+"/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService,times(1)).deleteVendorById(1L);
    }

    @Test
    public void getVendorById() throws Exception {
        VendorDTO returnedDto=new VendorDTO();
        returnedDto.setName("Pan samochodzik");
        returnedDto.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(returnedDto);

        mockMvc.perform(get(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.name",equalTo("Pan samochodzik")))
        .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));



    }

    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName("Pan samochodzik");

        VendorDTO returnedDto=new VendorDTO();
        returnedDto.setName("Pan samochodzik");
        returnedDto.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(patch(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(returnedDto.getName())))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));

    }

    @Test
    public void updateNewVendor() throws Exception {
        //given
        VendorDTO vendorDTO=new VendorDTO();
        vendorDTO.setName("Pan samochodzik");

        VendorDTO returnedDto=new VendorDTO();
        returnedDto.setName("Pan samochodzik");
        returnedDto.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.saveVendorByDTO(anyLong(),any(VendorDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(put(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Pan samochodzik")))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));


    }
}