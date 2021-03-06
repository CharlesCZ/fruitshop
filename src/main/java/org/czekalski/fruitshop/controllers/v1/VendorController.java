package org.czekalski.fruitshop.controllers.v1;


import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.api.v1.model.VendorListDTO;
import org.czekalski.fruitshop.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public final static String BASE_URL="/api/v1/vendors";

    private final VendorService vendorService;


    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
   public VendorListDTO getAllVendors(){

        return new VendorListDTO(vendorService.getAllVendors());
   }


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
   public VendorDTO createNewVendor(@RequestBody  VendorDTO vendorDTO){

        return vendorService.createNewVendor(vendorDTO);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.OK)
public  void deleteVendorById(@PathVariable  Long id){

        vendorService.deleteVendorById(id);

}
@GetMapping("/{id}")
@ResponseStatus(HttpStatus.OK)
public  VendorDTO getVendorById(@PathVariable  Long id){

        return vendorService.getVendorById(id);
}

@PatchMapping("/{id}")
@ResponseStatus(HttpStatus.OK)
public   VendorDTO patchVendor(@PathVariable  Long id,@RequestBody  VendorDTO vendorDTO){

        return vendorService.patchVendor(id,vendorDTO);
}

@PutMapping("/{id}")
@ResponseStatus(HttpStatus.OK)
public   VendorDTO updateVendor(@PathVariable  Long id,@RequestBody  VendorDTO vendorDTO){

    return vendorService.saveVendorByDTO(id,vendorDTO);
}
}
