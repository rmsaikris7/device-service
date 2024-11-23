package com.myproject.device.types.data;

import com.myproject.device.types.enums.Brand;
import com.myproject.device.types.valueobject.DeviceId;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeviceDO {

    private Long id;
    private DeviceId deviceId;
    private String name;
    private Brand brand;
    private Instant createdOn;
}
