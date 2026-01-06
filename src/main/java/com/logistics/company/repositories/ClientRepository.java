package com.logistics.company.repositories;

import com.logistics.company.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser_UserId(Long userId);
}
