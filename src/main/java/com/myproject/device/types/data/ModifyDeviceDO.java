package com.myproject.device.types.data;

import com.myproject.device.types.enums.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@AllArgsConstructor
@Getter
@Setter
public class ModifyDeviceDO {

    private JsonNullable<String> name;
    private JsonNullable<Brand> brand;
}
