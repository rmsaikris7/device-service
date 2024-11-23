package com.myproject.device.facade.resource;

import com.myproject.device.business.device.service.DeviceService;
import com.myproject.device.types.mapper.DeviceMapper;
import com.myproject.device.types.transport.*;
import com.myproject.device.types.valueobject.DeviceId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/device")
@AllArgsConstructor
@Tag(name = "Device")
public class DeviceResource {

    private final DeviceService deviceService;
    private final DeviceMapper mapper;

    @Operation(summary = "Create device")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Device created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeviceResponseTO.class))
                        })
            })
    @PostMapping
    public ResponseEntity<DeviceResponseTO> createDevice(@RequestBody CreateDeviceTO deviceRequest) {
        final var device = mapper.mapToDO(deviceRequest);
        final var saved = deviceService.createDevice(device);
        final var response = mapper.mapToTO(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Modify device")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Device modified",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeviceResponseTO.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Device not found",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorTO.class))
                        })
            })
    @PatchMapping("{deviceId}")
    public ResponseEntity<DeviceResponseTO> modifyDevice(
            @PathVariable DeviceId deviceId, @RequestBody ModifyDeviceTO modifyDevice) {
        final var device = mapper.mapToDO(modifyDevice);
        final var saved = deviceService.modifyDevice(deviceId, device);
        final var response = mapper.mapToTO(saved);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get device by id")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Get device by id",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeviceResponseTO.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Device not found",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorTO.class))
                        })
            })
    @GetMapping("{deviceId}")
    public ResponseEntity<DeviceResponseTO> getDeviceById(@PathVariable DeviceId deviceId) {
        final var device = deviceService.findDeviceById(deviceId);
        final var response = mapper.mapToTO(device);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get all devices (paginated)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Get all devices (paginated)",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                        })
            })
    @GetMapping
    public Page<DeviceResponseTO> getDevices(
            @SortDefault(sort = "createdOn", direction = Sort.Direction.ASC) Pageable page) {
        return deviceService.findAllDevicesPaged(page).map(mapper::mapToTO);
    }

    @Operation(summary = "Get all devices filtered (paginated)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Get all devices filtered (paginated)",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                        })
            })
    @PostMapping("/filter")
    public Page<DeviceResponseTO> getDevicesFiltered(
            @RequestBody List<FilterTO> filter,
            @SortDefault(sort = "createdOn", direction = Sort.Direction.ASC) Pageable page) {
        final var filterMap = filter.stream().collect(Collectors.toMap(FilterTO::column, FilterTO::value));
        return deviceService.findAllDevicesByBrandPaged(filterMap, page).map(mapper::mapToTO);
    }
}
