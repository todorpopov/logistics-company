package com.logistics.company.repositories;

import com.logistics.company.models.CourierEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CourierEmployeeRepository extends JpaRepository<CourierEmployee, Long> {
    Optional<CourierEmployee> findByUser_UserId(Long userId);

    @Modifying
    @Transactional
    void deleteByUser_UserId(Long userId);
}
