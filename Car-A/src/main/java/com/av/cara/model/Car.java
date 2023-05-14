package com.av.cara.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false , unique = true , updatable = false)
    // 9 Number
    private String buildNumber;

    @Column(nullable = false)
    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd" , shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private Long price;
}
