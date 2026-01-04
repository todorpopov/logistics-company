package com.logistics.company.repositories;

import com.logistics.company.models.CourierEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierEmployeeRepository extends JpaRepository<CourierEmployee, Long> {
}
