package com.logistics.company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courier_employees")
public class CourierEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courierEmployeeId;

    @OneToOne(optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_courier_employee_user")
    )
    private User user;
}
