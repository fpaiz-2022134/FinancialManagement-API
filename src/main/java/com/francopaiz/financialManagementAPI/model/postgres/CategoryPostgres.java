package com.francopaiz.financialManagementAPI.model.postgres;


// Importa las anotaciones de JPA.

import jakarta.persistence.*;
import lombok.*;



@Data
@Entity
@Table(name = "category")
public class CategoryPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_category")
    private Long id;

    private String name;

}
