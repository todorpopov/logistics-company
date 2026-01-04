package com.logistics.company.repositories;

import com.logistics.company.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
