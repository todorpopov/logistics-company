package com.logistics.company.repositories;

import com.logistics.company.models.CourierEmployee;
import org.springframework.data.repository.CrudRepository;

public interface CourierEmployeeRepository extends CrudRepository<CourierEmployee, Long> {
}
