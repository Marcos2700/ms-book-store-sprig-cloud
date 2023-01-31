package com.example.store.customer.customer.service.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El numero de id no puede estar vacio.")
    @Size(min = 8, max = 20, message = "El minimo de caracteres es 8 y el maximo de 25.")
    @Column(name = "number_id",unique = true, length = 25, nullable = false)
    private String numberId;
    @NotEmpty(message = "El nombre no puede estar vacio.")
    @Column(name = "first_name", nullable = false)
    private String firstname;
    @NotEmpty(message = "El apellido no puede estar vacio.")
    @Column(name = "last_name", nullable = false)
    private String lastname;
    @NotEmpty(message = "El correo no puede estar vacio")
    @Email(message = "Direccion de correo electronico no valida.")
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column
    @NotEmpty(message = "El estado no puede estar vacio.")
    private String state;
    @NotNull(message = "La region debe ser definida.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
}
