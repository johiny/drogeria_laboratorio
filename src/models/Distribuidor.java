/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author parra
 */
public enum Distribuidor {
    COFARMA("Cofarma"),
    EMPSEPHAR("Empsephar"),
    CEMEFAR("Cemefar");

    private final String nombre;
    
    Distribuidor(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}