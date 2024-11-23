package com.myproject.device.dataaccess.converter;

import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.model.DeviceBE;
import com.myproject.device.types.valueobject.DeviceId;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceConverter {

    DeviceBE mapTOBE(DeviceDO source);

    @Mapping(
            target = "deviceId",
            expression = "java(com.myproject.device.types.valueobject.DeviceId.of(source.getDeviceId()))")
    DeviceDO mapToDO(DeviceBE source);

    default UUID mapFromVO(DeviceId deviceId) {
        return deviceId == null ? null : deviceId.value();
    }
}
