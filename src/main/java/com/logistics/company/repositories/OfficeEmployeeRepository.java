package com.logistics.company.repositories;

import com.logistics.company.models.OfficeEmployee;
import org.springframework.data.repository.CrudRepository;

public interface OfficeEmployeeRepository extends CrudRepository<OfficeEmployee, Long> {
}
