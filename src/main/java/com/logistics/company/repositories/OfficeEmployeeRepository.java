package com.logistics.company.repositories;

import com.logistics.company.models.OfficeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OfficeEmployeeRepository extends JpaRepository<OfficeEmployee, Long> {
    Optional<OfficeEmployee> findByUser_UserId(Long userId);

    @Modifying
    @Transactional
    void deleteByUser_UserId(Long userId);
}
