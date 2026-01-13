package com.logistics.company.models;

import com.logistics.company.models.enums.ShipmentDeliveryType;
import com.logistics.company.models.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long shipmentId;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "sender_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_shipment_sender")
    )
    private Client sender;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "receiver_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_shipment_receiver")
    )
    private Client receiver;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "registered_by",
        foreignKey = @ForeignKey(name = "fk_shipment_office_employee")
    )
    private OfficeEmployee registeredBy;

    @ManyToOne
    @JoinColumn(
        name = "delivery_office_id",
        foreignKey = @ForeignKey(name = "fk_shipment_office")
    )
    private Office deliveryOffice;

    @ManyToOne
    @JoinColumn(
        name = "courier_employee_id",
        foreignKey = @ForeignKey(name = "fk_shipment_courier_employee")
    )
    private CourierEmployee courierEmployee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentDeliveryType deliveryType;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer weightGram;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDate sentDate;

    private LocalDate deliveredDate;
}
