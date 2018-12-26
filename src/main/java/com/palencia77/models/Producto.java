package com.palencia77.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Producto {
    @Id
    private Long id;
    @Index // Indexa el atributo en el datastore
    private String descripcion;
    @Index
    private Double precio;
    @Index
    private Date vencimiento;

    public Producto() {
    }

    public Producto(String descripcion, Double precio, Date vencimiento) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.vencimiento = vencimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
