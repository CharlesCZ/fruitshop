package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO getVendorById(Long id);

    VendorDTO patchVendor(Long id,VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);



}
