package com.academy.store.product.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "products")
// con lombok tambien podemos agregar los metodos
@Data
//para generar un contructor con todas las propiedades de nuestra entidad.
@AllArgsConstructor
//para generar un contructor sin atgumentos.
@NoArgsConstructor
// con builder para generar nuevas instancias
@Builder
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del campo no debe ser vacio.") // anotaciones de hibernate
    private String name;
    private String description;
    @Positive (message = "El stock debe ser mayor que cero.")
    private Double stock;
    private Double price;
    private String status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP) //para mapear fecha y hora
    private Date createAT;

    @NotNull(message = "La categoria no puede ser vacia.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id") // join con la columna id de category
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Category category;


}
