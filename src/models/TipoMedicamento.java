/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author parra
 */

public enum TipoMedicamento {
    ANALGESICO("Analgésico"),
    ANALEPTICO("Analéptico"),
    ANESTESICO("Anestésico"),
    ANTIACIDO("Antiácido"),
    ANTIDEPRESIVO("Antidepresivo"),
    ANTIBIOTICO("Antibiótico");

    private final String nombre;
    
    TipoMedicamento(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
