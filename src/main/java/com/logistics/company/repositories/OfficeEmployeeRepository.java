package com.logistics.company.repositories;

import com.logistics.company.models.CourierEmployee;
import com.logistics.company.models.OfficeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficeEmployeeRepository extends JpaRepository<OfficeEmployee, Long> {
    Optional<OfficeEmployee> findByUser_UserId(Long userId);
}
