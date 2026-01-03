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
@Table(name = "office_employees")
public class OfficeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long office_employee_id;

    @OneToOne(optional = false)
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
