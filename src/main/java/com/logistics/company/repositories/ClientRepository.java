package com.logistics.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistics.company.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUser_UserId(Long userId);
}
