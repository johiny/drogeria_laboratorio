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

    /**
     * Retorna las farmacias disponibles para un distribuidor específico.
     * Cada distribuidor tiene 2 farmacias: una principal y una secundaria.
     * @param distribuidor El distribuidor seleccionado
     * @return Lista de farmacias asociadas al distribuidor
     */
    public List<Farmacia> getFarmaciasDisponibles(Distribuidor distribuidor) {
        List<Farmacia> farmacias = new ArrayList<>();
        
        if (distribuidor == null) {
            return farmacias;
        }
        
        switch (distribuidor) {
            case COFARMA:
                farmacias.add(new Farmacia(1, "Farmacia Cofarma Central", "Av. Constitución n. 45", true));
                farmacias.add(new Farmacia(2, "Farmacia Cofarma Norte", "Calle Larios n. 12", false));
                break;
            case EMPSEPHAR:
                farmacias.add(new Farmacia(3, "Farmacia Empsephar Principal", "Paseo del Parque n. 8", true));
                farmacias.add(new Farmacia(4, "Farmacia Empsephar Sur", "Calle Granada n. 33", false));
                break;
            case CEMEFAR:
                farmacias.add(new Farmacia(5, "Farmacia Cemefar Plaza", "Plaza de la Merced n. 20", true));
                farmacias.add(new Farmacia(6, "Farmacia Cemefar Este", "Calle Alcazabilla n. 7", false));
                break;
        }
        
        return farmacias;
    }

/**
 * Valida todos los campos y retorna una lista de campos faltantes o inválidos.
 * Usa los métodos de validación individuales para mantener consistencia.
 * @return Lista de mensajes de error (vacía si todo es válido)
 */
public List<String> validarCampos(String nombre,
                                   TipoMedicamento tipo,
                                   String cantidadTexto,
                                   Distribuidor distribuidor,
                                   List<Farmacia> farmacias) {
    List<String> errores = new ArrayList<>();
    
    // Validar campos vacíos/null usando métodos de validación
    boolean camposVacios = false;
    
    if (nombre == null || nombre.trim().isEmpty()) {
        errores.add("Nombre del medicamento");
        camposVacios = true;
    }
    if (!validarTipo(tipo)) {
        errores.add("Tipo de medicamento");
        camposVacios = true;
    }
    if (cantidadTexto == null || cantidadTexto.trim().isEmpty()) {
        errores.add("Cantidad");
        camposVacios = true;
    }
    if (!validarDistribuidor(distribuidor)) {
        errores.add("Distribuidor");
        camposVacios = true;
    }
    if (!validarFarmacias(farmacias)) {
        errores.add("Sucursal");
        camposVacios = true;
    }
    
    // Si hay campos vacíos, retornar sin validar formato
    if (camposVacios) {
        return errores;
    }
    
    // Validar formato de campos (solo si están llenos)
    if (!validarNombreMedicamento(nombre)) {
        errores.add("El nombre solo puede contener caracteres alfanuméricos");
    }
    if (!validarCantidad(cantidadTexto)) {
        errores.add("La cantidad debe ser un número entero positivo");
    }
    
    return errores;
}

/**
 * Crea un pedido después de validar todos los campos.
 * Lanza PedidoException con todos los errores encontrados.
 */
public Pedido crearPedido(String nombre,
                          TipoMedicamento tipo,
                          String cantidadTexto,
                          Distribuidor distribuidor,
                          List<Farmacia> farmacias) throws PedidoException {

    List<String> errores = validarCampos(nombre, tipo, cantidadTexto, distribuidor, farmacias);
    
    if (!errores.isEmpty()) {
        StringBuilder mensaje = new StringBuilder();
        for (String error : errores) {
            mensaje.append("* ").append(error).append("\n");
        }
        throw new PedidoException(mensaje.toString().trim());
    }

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