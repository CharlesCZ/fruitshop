package org.czekalski.fruitshop.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    @ApiModelProperty(value = "This is vendor's name",required = true)
    private String name;

    @ApiModelProperty(value = "This is vendor's url")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
