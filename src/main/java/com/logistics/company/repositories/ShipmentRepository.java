package com.logistics.company.repositories;

import com.logistics.company.models.Shipment;
import com.logistics.company.models.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findAllByStatusIs(ShipmentStatus status);
    List<Shipment> findAllByRegisteredBy_OfficeEmployeeId(Long officeEmployeeId);
    List<Shipment> findAllBySender_ClientId(Long clientId);
    List<Shipment> findAllByReceiver_ClientId(Long clientId);
    List<Shipment> findAllByDeliveredDateBetween(LocalDate deliveredDateAfter, LocalDate deliveredDateBefore);
    List<Shipment> findAllByCourierEmployee_User_UserId(Long userId);
}
