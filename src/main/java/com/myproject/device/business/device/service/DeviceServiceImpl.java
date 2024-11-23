package com.myproject.device.business.device.service;

import com.myproject.device.dataaccess.device.DeviceRepository;
import com.myproject.device.exceptionhandling.DeviceNotFoundException;
import com.myproject.device.types.data.DeviceDO;
import com.myproject.device.types.data.ModifyDeviceDO;
import com.myproject.device.types.valueobject.DeviceId;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    @Override
    @Transactional
    public DeviceDO createDevice(DeviceDO device) {
        Objects.requireNonNull(device);

        final var deviceId = UUID.randomUUID();
        device.setDeviceId(DeviceId.of(deviceId));

        return repository.upsertDevice(device);
    }

    @Override
    @Transactional
    public DeviceDO modifyDevice(DeviceId deviceId, ModifyDeviceDO modifyDevice) {
        Objects.requireNonNull(modifyDevice);

        final var device = findDeviceById(deviceId);
        populateModifyDevice(modifyDevice, device);

        return repository.upsertDevice(device);
    }

    @Override
    public Page<DeviceDO> findAllDevicesPaged(Pageable pageable) {
        return repository.findAllDevicesPaged(pageable);
    }

    @Override
    public DeviceDO findDeviceById(DeviceId deviceId) {
        final var device = repository.findDeviceById(deviceId);
        if (device == null) {
            throw new DeviceNotFoundException(deviceId.value());
        }
        return device;
    }

    @Override
    public Page<DeviceDO> findAllDevicesByBrandPaged(Map<String, Object> filter, Pageable pageable) {
        return repository.findAllDevicesByBrandPaged(filter, pageable);
    }

    private void populateModifyDevice(ModifyDeviceDO modifyDevice, DeviceDO existingDevice) {
        modifyDevice.getName().ifPresent(existingDevice::setName);
        modifyDevice.getBrand().ifPresent(existingDevice::setBrand);
    }
}
