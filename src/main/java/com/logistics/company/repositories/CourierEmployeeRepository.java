package com.logistics.company.repositories;

import com.logistics.company.models.CourierEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierEmployeeRepository extends JpaRepository<CourierEmployee, Long> {
    Optional<CourierEmployee> findByUser_UserId(Long userId);
}
