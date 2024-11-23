package com.myproject.device.dataaccess.device.jpa.specification;

import com.myproject.device.types.model.DeviceBE;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;

public class DeviceSpecification {

    public static Specification<DeviceBE> equalsOps(Map<String, Object> filter) {
        return (root, __, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filter.forEach((column, value) -> {
                Predicate predicate = criteriaBuilder.equal(root.get(column), value);
                predicates.add(predicate);
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
