package org.czekalski.fruitshop.controllers.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.api.v1.model.VendorListDTO;
import org.czekalski.fruitshop.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my vendor controller")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public final static String BASE_URL="/api/v1/vendors";

    private final VendorService vendorService;


    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @ApiOperation(produces = MediaType.APPLICATION_JSON_VALUE, nickname = "getAllVendors",value = "This will get All vendors",notes = "There are some notes about getAllVendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
   public VendorListDTO getAllVendors(){

        return new VendorListDTO(vendorService.getAllVendors());
   }

@ApiOperation(value = "This will create new vendor")
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
   public VendorDTO createNewVendor(@RequestBody  VendorDTO vendorDTO){

        return vendorService.createNewVendor(vendorDTO);
}

@ApiOperation(value = "Delete vendor by id")
@DeleteMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public  void deleteVendorById(@PathVariable  Long id){

        vendorService.deleteVendorById(id);

}
    @ApiOperation(value = "This will get vendor by id")
@GetMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public  VendorDTO getVendorById(@PathVariable  Long id){

        return vendorService.getVendorById(id);
}

    @ApiOperation(value = "Update a vendor property")
@PatchMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public   VendorDTO patchVendor(@PathVariable  Long id,@RequestBody  VendorDTO vendorDTO){

        return vendorService.patchVendor(id,vendorDTO);
}

    @ApiOperation(value = "Update an existing vendor")
@PutMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public   VendorDTO updateVendor(@PathVariable  Long id,@RequestBody  VendorDTO vendorDTO){

    return vendorService.saveVendorByDTO(id,vendorDTO);
}
}
