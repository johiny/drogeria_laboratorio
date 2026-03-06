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

    public List<Farmacia> getFarmaciasDisponibles() {
    List<Farmacia> farmacias = new ArrayList<>();
    farmacias.add(new Farmacia(1, "AguaPanelaDrugs", "Calle de la Rosa n. 28", true));
    farmacias.add(new Farmacia(2, "AguaPanelitaDrugs", "Calle Alcazabilla n. 3", false));
    return farmacias;
}

public Pedido crearPedido(String nombre,
                          TipoMedicamento tipo,
                          String cantidadTexto,
                          Distribuidor distribuidor,
                          List<Farmacia> farmacias) throws PedidoException {

    if (!validarNombreMedicamento(nombre))
        throw new PedidoException("El nombre solo puede contener caracteres alfanuméricos.");
    if (!validarTipo(tipo))
        throw new PedidoException("Debe seleccionar un tipo de medicamento.");
    if (!validarCantidad(cantidadTexto))
        throw new PedidoException("La cantidad debe ser un número entero positivo.");
    if (!validarDistribuidor(distribuidor))
        throw new PedidoException("Debe seleccionar un distribuidor.");
    if (!validarFarmacias(farmacias))
        throw new PedidoException("Debe seleccionar al menos una farmacia.");

    Medicamento medicamento = new Medicamento(nombre, tipo);
    int cantidad = Integer.parseInt(cantidadTexto);
    return new Pedido(medicamento, distribuidor, farmacias, cantidad);
}

public boolean validarNombreMedicamento(String nombre) {
    return nombre != null && !nombre.trim().isEmpty()
           && nombre.matches("[a-zA-Z0-9 ]+");
}

public boolean validarTipo(TipoMedicamento tipo) {
    return tipo != null;
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