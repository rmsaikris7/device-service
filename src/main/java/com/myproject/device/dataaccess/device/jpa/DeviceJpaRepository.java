package com.myproject.device.dataaccess.device.jpa;

import com.myproject.device.types.model.DeviceBE;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceJpaRepository
        extends JpaRepository<DeviceBE, Long>,
                PagingAndSortingRepository<DeviceBE, Long>,
                JpaSpecificationExecutor<DeviceBE> {
    DeviceBE findByDeviceId(UUID deviceId);
}
