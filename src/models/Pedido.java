/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author parra
 */
public class Pedido {
    private Medicamento medicamento;
    private Distribuidor distribuidor;
    private List<Farmacia> farmacias;
    private int cantidad;

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public List<Farmacia> getFarmacias() {
        return farmacias;
    }

    public void setFarmacias(List<Farmacia> farmacias) {
        this.farmacias = farmacias;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido(Medicamento medicamento, Distribuidor distribuidor, List<Farmacia> farmacias, int cantidad) {
        this.medicamento = medicamento;
        this.distribuidor = distribuidor;
        this.farmacias = farmacias;
        this.cantidad = cantidad;
    }

}
