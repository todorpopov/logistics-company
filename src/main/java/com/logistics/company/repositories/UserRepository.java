package com.logistics.company.repositories;

import com.logistics.company.dtos.user.InternalEmployeeDTO;
import com.logistics.company.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT new com.logistics.company.dtos.user.InternalEmployeeDTO(
            u.userId,
            oe.officeEmployeeId,
            ce.courierEmployeeId,
            u.firstName,
            u.lastName,
            u.email,
            oe.office.officeId,
            u.userRole
        )
        FROM User u
        LEFT JOIN OfficeEmployee oe ON oe.user = u
        LEFT JOIN CourierEmployee ce ON ce.user = u
        WHERE oe.officeEmployeeId IS NOT NULL OR ce.courierEmployeeId IS NOT NULL
    """)
    List<InternalEmployeeDTO> findAllEmployeesAsDTO();
}
