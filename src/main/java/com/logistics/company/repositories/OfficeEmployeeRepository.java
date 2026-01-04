package com.logistics.company.repositories;

import com.logistics.company.models.OfficeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeEmployeeRepository extends JpaRepository<OfficeEmployee, Long> {
}
