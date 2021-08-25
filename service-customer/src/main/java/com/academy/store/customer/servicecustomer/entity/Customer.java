package com.academy.store.customer.servicecustomer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El numero de documento no puede ser vacio.")
    @Size(min = 8, max = 8, message = "El tamaño del numero de documento es 8.")
    @Column(name = "number_id", unique = true, length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "El nombre no puede ser vacio.")
    @Column(name = "first_name", nullable = false)
    private String firstname;

    @NotEmpty(message = "El apellido no puede ser vacio.")
    @Column(name = "last_name", nullable = false)
    private String lastname;

    @NotEmpty(message = "El correo no puede ser vacio.")
    @Email(message = "No es una direccion de correo bien formada.")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photourl;

    @NotNull(message = "La region no puede ser vacia.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    private String state;


}
