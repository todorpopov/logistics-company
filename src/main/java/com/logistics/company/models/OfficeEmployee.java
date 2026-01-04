package com.logistics.company.models;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "office_employees")
public class OfficeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long officeEmployeeId;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_office_employee_user")
    )
    private User user;

    @OneToOne(optional = false)
    @JoinColumn(
        name = "office_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_office_employee_office")
    )
    private Office office;
}
