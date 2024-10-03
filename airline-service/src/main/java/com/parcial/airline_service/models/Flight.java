package com.parcial.airline_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "flights")
@Builder
@AllArgsConstructor
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airline;

    @Column(nullable = false)
    private String plate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date departureDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date returnDate;

    @Column(nullable = false)
    private Boolean isDirect;

    @Column(nullable = false)
    private Integer durationHours;

    @Column(nullable = false)
    private Integer passengersNumber;

    @OneToOne(optional = true)
    @JoinColumn(name = "origins", referencedColumnName = "id")
    private Origin origin;

    @OneToOne(optional = true)
    @JoinColumn(name = "destinies", referencedColumnName = "id")
    private Destiny destiny;

    @Column(nullable = false)
    private Integer economyPrice;

    @Column(nullable = false)
    private Integer businessPrice;

    @Column(nullable = false)
    private Integer totalRevenue;

    @ElementCollection
    @CollectionTable(name = "flight_seats", joinColumns = @JoinColumn(name = "flight_id"))
    @MapKeyColumn(name = "seat_number")
    @Column(name = "client_id")
    private Map<Integer, Long> assignedSeats = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "flight_clients", joinColumns = @JoinColumn(name = "flight_id"))
    @Column(name = "client_id")
    private Set<Long> assignedClients = new HashSet<>();

}
