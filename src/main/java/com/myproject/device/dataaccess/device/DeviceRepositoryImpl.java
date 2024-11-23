package com.myproject.device.dataaccess.device;

import com.myproject.device.dataaccess.converter.DeviceConverter;
import com.myproject.device.dataaccess.device.jpa.DeviceJpaRepository;
import com.myproject.device.dataaccess.device.jpa.specification.DeviceSpecification;
import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.valueobject.DeviceId;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceJpaRepository deviceJpaRepository;
    private final DeviceConverter converter;

    @Override
    public DeviceDO upsertDevice(DeviceDO device) {
        final var deviceBE = converter.mapTOBE(device);
        final var savedDeviceBE = deviceJpaRepository.save(deviceBE);
        return converter.mapToDO(savedDeviceBE);
    }

    @Override
    public Page<DeviceDO> findAllDevicesPaged(Pageable pageable) {
        final var page = deviceJpaRepository.findAll(pageable);
        return page.map(converter::mapToDO);
    }

    @Override
    public DeviceDO findDeviceById(DeviceId deviceId) {
        final var device = deviceJpaRepository.findByDeviceId(deviceId.value());
        return converter.mapToDO(device);
    }

    @Override
    public Page<DeviceDO> findAllDevicesByBrandPaged(Map<String, Object> filter, Pageable pageable) {
        final var page = deviceJpaRepository.findAll(DeviceSpecification.equalsOps(filter), pageable);
        return page.map(converter::mapToDO);
    }
}
