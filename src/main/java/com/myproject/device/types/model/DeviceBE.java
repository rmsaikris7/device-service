package com.myproject.device.types.model;

import com.myproject.device.dataaccess.device.jpa.converter.UUIDConverter;
import com.myproject.device.types.enums.Brand;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeviceBE extends BaseEntity {

    @Id
    @Generated
    private Long id;

    @Convert(converter = UUIDConverter.class)
    private UUID deviceId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;
}
