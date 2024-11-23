package com.myproject.device.business.device.service;

import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.data.ModifyDeviceDO;
import com.myproject.device.types.valueobject.DeviceId;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceService {

    DeviceDO createDevice(DeviceDO device);

    Page<DeviceDO> findAllDevicesPaged(Pageable pageable);

    DeviceDO findDeviceById(DeviceId deviceId);

    Page<DeviceDO> findAllDevicesByBrandPaged(Map<String, Object> filter, Pageable pageable);

    DeviceDO modifyDevice(DeviceId deviceId, ModifyDeviceDO device);
}
