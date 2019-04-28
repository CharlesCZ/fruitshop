package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.VendorMapper;
import org.czekalski.fruitshop.api.v1.model.VendorDTO;
import org.czekalski.fruitshop.domain.Vendor;
import org.czekalski.fruitshop.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorDTO vendorDTO=vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/"+vendor.getId());

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendor=vendorMapper.vendorDtoToVendor(vendorDTO);


        return saveAndReturnVendorDTO(vendor);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO getVendorById(Long id) {

        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO=vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl("/api/v1/vendors/"+vendor.getId());

            return vendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return  vendorRepository.findById(id).map(vendor -> {

            if(vendorDTO.getName()!=null)
                vendor.setName(vendorDTO.getName());

            VendorDTO vendorDto=vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            vendorDto.setVendorUrl("/api/v1/vendors"+id);
            return vendorDto;

        }).orElseThrow(ResourceNotFoundException::new);


    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor=vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnVendorDTO(vendor);
    }

    private VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
        Vendor returnedVendor=vendorRepository.save(vendor);
        VendorDTO returnedDto=vendorMapper.vendorToVendorDTO(returnedVendor);
        returnedDto.setVendorUrl("/api/v1/vendors/"+returnedVendor.getId());
        return returnedDto;
    }
}
