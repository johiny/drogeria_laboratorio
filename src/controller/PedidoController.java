package controller;

import models.Distribuidor;
import models.Farmacia;
import models.Medicamento;
import models.Pedido;
import models.TipoMedicamento;
import handlers.PedidoException;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    // Datos fijos del negocio
    public List<Farmacia> getFarmaciasDisponibles() {
        List<Farmacia> farmacias = new ArrayList<>();
        farmacias.add(new Farmacia(1, "Principal", "Calle de la Rosa n. 28", true));
        farmacias.add(new Farmacia(2, "Secundaria", "Calle Alcazabilla n. 3", false));
        return farmacias;
    }

    public List<Medicamento> getMedicamentosDisponibles() {
        List<Medicamento> medicamentos = new ArrayList<>();
        medicamentos.add(new Medicamento(1, "Ibuprofeno",  TipoMedicamento.ANALGESICO));
        medicamentos.add(new Medicamento(2, "Amoxicilina", TipoMedicamento.ANTIBIOTICO));
        medicamentos.add(new Medicamento(3, "Omeprazol",   TipoMedicamento.ANTIACIDO));
        medicamentos.add(new Medicamento(4, "Fluoxetina",  TipoMedicamento.ANTIDEPRESIVO));
        medicamentos.add(new Medicamento(5, "Lidocaina",   TipoMedicamento.ANESTESICO));
        medicamentos.add(new Medicamento(6, "Cafeina",     TipoMedicamento.ANALEPTICO));
        return medicamentos;
    }

    // Crea el pedido si todo es válido, null si hay error
    public Pedido crearPedido(Medicamento medicamento,
                              String cantidadTexto,
                              Distribuidor distribuidor,
                              List<Farmacia> farmacias) throws PedidoException {
    if (!validarMedicamento(medicamento))
        throw new PedidoException("Debe seleccionar un medicamento.");
    if (!validarCantidad(cantidadTexto))
        throw new PedidoException("La cantidad debe ser un número entero positivo.");
    if (!validarDistribuidor(distribuidor))
        throw new PedidoException("Debe seleccionar un distribuidor.");
    if (!validarFarmacias(farmacias))
        throw new PedidoException("Debe seleccionar al menos una farmacia.");
    
    int cantidad = Integer.parseInt(cantidadTexto);
    return new Pedido(medicamento, distribuidor, farmacias, cantidad);
}

    // Validaciones
    public boolean validarMedicamento(Medicamento medicamento) {
        return medicamento != null;
    }

    public boolean validarCantidad(String cantidad) {
        try {
            return Integer.parseInt(cantidad) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validarDistribuidor(Distribuidor distribuidor) {
        return distribuidor != null;
    }

    public boolean validarFarmacias(List<Farmacia> farmacias) {
        return farmacias != null && !farmacias.isEmpty();
    }
}