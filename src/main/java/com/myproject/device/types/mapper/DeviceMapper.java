package com.myproject.device.types.mapper;

import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.data.ModifyDeviceDO;
import com.myproject.device.types.transport.CreateDeviceTO;
import com.myproject.device.types.transport.DeviceResponseTO;
import com.myproject.device.types.transport.ModifyDeviceTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DeviceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deviceId", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    DeviceDO mapToDO(CreateDeviceTO source);

    DeviceResponseTO mapToTO(DeviceDO source);

    ModifyDeviceDO mapToDO(ModifyDeviceTO source);
}
