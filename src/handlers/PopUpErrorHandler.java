/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handlers;
import javax.swing.JOptionPane;

/**
 *
 * @author parra
 */
public class PopUpErrorHandler {
    public static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(
            null, 
            mensaje, 
            "Error en el pedido", 
            JOptionPane.ERROR_MESSAGE
        );
    }
}



// LLAMAR EN VIEW ASI EN CASO DE ALGUNA EXCEPCION
//try {
//    Pedido pedido = controller.crearPedido(...);
//    // abrir ventana resumen
//} catch (PedidoException e) {
//    ErrorHandler.mostrar(e.getMessage());
//}