package com.myproject.device;

import static com.myproject.device.types.enums.Brand.BRAND_A;
import static com.myproject.device.types.enums.Brand.BRAND_D;
import static org.hamcrest.Matchers.equalTo;

import com.myproject.device.types.model.DeviceBE;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DeviceITest extends AbstractITest {

    @Nested
    class CreateDevice {
        @Test
        void shouldCreateDevice() {
            // GIVEN
            final var testDeviceName = "Sample device";
            final var testDeviceBrand = BRAND_D;
            final var payload =
                    """
                        {"name": "$name", "brand": "$brand"}
                        """
                            .replace("$name", testDeviceName)
                            .replace("$brand", testDeviceBrand.name());

            // THEN
            RestAssured.given()
                    .body(payload)
                    .contentType("application/json")
                    .when()
                    .post("/api/v1/device")
                    .then()
                    .statusCode(201)
                    .body("name", equalTo(testDeviceName))
                    .body("brand", equalTo(testDeviceBrand.name()));
        }
    }

    @Nested
    class ModifyDevice {

        @Test
        void shouldModifyDevice() {
            // GIVEN
            final var deviceId = UUID.randomUUID();
            final var existingDevice = new DeviceBE(1L, deviceId, "Sample device", BRAND_D);

            deviceJpaRepository.save(existingDevice);

            final var modifiedDeviceName = "New device";
            final var modifiedDeviceBrand = BRAND_A;
            final var payload =
                    """
                        {"name": "$name", "brand": "$brand"}
                        """
                            .replace("$name", modifiedDeviceName)
                            .replace("$brand", modifiedDeviceBrand.name());

            RestAssured.given()
                    .body(payload)
                    .contentType("application/json")
                    .when()
                    .pathParam("deviceId", deviceId)
                    .patch("/api/v1/device/{deviceId}")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo(modifiedDeviceName));
        }
    }

    @Nested
    class GetDevice {
        @Test
        void shouldReturnDevice() {
            // GIVEN
            final var testDeviceName = "Sample device";
            final var deviceId = UUID.randomUUID();
            final var existingDevice = new DeviceBE(1L, deviceId, testDeviceName, BRAND_D);

            deviceJpaRepository.save(existingDevice);

            // THEN
            RestAssured.given()
                    .when()
                    .pathParam("deviceId", deviceId)
                    .get("/api/v1/device/{deviceId}")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo(testDeviceName));
        }

        @Test
        void shouldReturnNotFoundIfDeviceNotFound() {
            // GIVEN + THEN
            final var deviceId = UUID.randomUUID();
            RestAssured.given()
                    .pathParam("deviceId", deviceId)
                    .when()
                    .get("/api/v1/device/{deviceId}")
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldReturnPaginatedDevices() {
            // GIVEN

            final var testDeviceId1 = UUID.randomUUID();
            final var testDeviceName1 = "Sample device 1";
            final var testDevice1 = new DeviceBE(null, testDeviceId1, testDeviceName1, BRAND_A);

            final var testDeviceId2 = UUID.randomUUID();
            final var testDeviceName2 = "Sample device 2";
            final var testDevice2 = new DeviceBE(null, testDeviceId2, testDeviceName2, BRAND_D);

            deviceJpaRepository.save(testDevice1);
            deviceJpaRepository.save(testDevice2);

            RestAssured.given()
                    .when()
                    .queryParams(Map.of("page", 0, "size", 10))
                    .get("/api/v1/device")
                    .then()
                    .statusCode(200)
                    .body("content[0].name", equalTo(testDeviceName1))
                    .body("content[1].name", equalTo(testDeviceName2));
        }

        @Test
        void shouldReturnPaginatedFilteredDevices() {
            // GIVEN
            final var testDeviceId1 = UUID.randomUUID();
            final var testDeviceName1 = "Sample device 1";
            final var testDevice1 = new DeviceBE(1L, testDeviceId1, testDeviceName1, BRAND_A);

            final var testDeviceId2 = UUID.randomUUID();
            final var testDeviceName2 = "Sample device 2";
            final var testDevice2 = new DeviceBE(2L, testDeviceId2, testDeviceName2, BRAND_D);

            deviceJpaRepository.saveAll(List.of(testDevice1, testDevice2));

            final var payload =
                    """
                        [{"column": "brand", "value": "$brand"}]
                        """
                            .replace("$brand", BRAND_A.name());

            // THEN
            RestAssured.given()
                    .body(payload)
                    .contentType("application/json")
                    .when()
                    .queryParams(Map.of("page", 0, "size", 10))
                    .post("/api/v1/device/filter")
                    .then()
                    .statusCode(200)
                    .body("content[0].name", equalTo(testDeviceName1))
                    .body("content[0].brand", equalTo(BRAND_A.name()));
        }
    }
}
