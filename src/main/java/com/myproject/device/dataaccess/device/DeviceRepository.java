package com.myproject.device.dataaccess.device;

import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.valueobject.DeviceId;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceRepository {

    DeviceDO upsertDevice(DeviceDO device);

    Page<DeviceDO> findAllDevicesPaged(Pageable pageable);

    DeviceDO findDeviceById(DeviceId deviceId);

    Page<DeviceDO> findAllDevicesByBrandPaged(Map<String, Object> brand, Pageable pageable);
}
