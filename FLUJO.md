# Flujo de la Aplicación - Sistema de Pedidos de Medicamentos

## 🔄 Flujo Principal

### 1. Inicio de la Aplicación
```
Drogueria.main()
    ↓
Configura UIManager (colores para JOptionPane)
    ↓
Crea y muestra MenuPrincipal
```

### 2. Interacción del Usuario en MenuPrincipal

#### 2.1 Llenar Formulario
- **Nombre del medicamento**: JTextField (texto libre, alfanumérico)
- **Tipo**: JComboBox con TipoMedicamento enum (6 opciones)
- **Cantidad**: JTextField (número entero positivo)
- **Distribuidor**: JRadioButton group con Distribuidor enum (3 opciones)
- **Farmacias**: JCheckBox dinámicos (generados desde PedidoController.getFarmaciasDisponibles())

#### 2.2 Click en "Borrar"
```
borrarCampos()
    ↓
Limpia todos los campos del formulario
```

#### 2.3 Click en "Confirmar"
```
confirmarPedido()
    ↓
1. Validación de campos vacíos (nivel UI)
   ├─ Si faltan campos → JOptionPane con lista de campos faltantes
   └─ Si todos completos → continuar
    ↓
2. Validación de negocio (PedidoController)
   controller.crearPedido(nombre, tipo, cantidad, distribuidor, farmacias)
    ├─ Validar nombre (alfanumérico)
    ├─ Validar tipo (no null)
    ├─ Validar cantidad (entero positivo)
    ├─ Validar distribuidor (no null)
    └─ Validar farmacias (lista no vacía)
    ↓
3. Resultado de validación
   ├─ Si hay PedidoException → JOptionPane con mensaje de error
   └─ Si es válido → Abrir ResumenPedidoView
```

### 3. Ventana ResumenPedidoView

```
ResumenPedidoView(parent, pedido)
    ↓
Muestra:
    - Mensaje principal: "¡Aquí faltan cosas!"
    - Mensaje secundario: "(Pero tu pedido fue validado correctamente)"
    - Botón "Cerrar"
    ↓
Usuario cierra ventana
    ↓
Vuelve a MenuPrincipal (puede hacer nuevo pedido)
```

## 🎨 Componentes de Vista

### MenuPrincipal
- **Responsabilidad**: Capturar datos del formulario
- **Validaciones**:
  - Campos vacíos (nivel UI)
  - Delegación a PedidoController para validaciones de negocio
- **Componentes custom**:
  - CustomRadioIcon (círculos personalizados)
  - CustomCheckIcon (cuadrados personalizados)

### ResumenPedidoView
- **Responsabilidad**: Mostrar confirmación del pedido
- **Diseño**: Modal dialog que bloquea MenuPrincipal
- **Colores**: Hereda COLOR_* de MenuPrincipal para consistencia

## 🎮 Controlador

### PedidoController
- **getFarmaciasDisponibles()**: Retorna lista de farmacias hardcodeadas
  - AguaPanelaDrugs (Principal)
  - AguaPanelitaDrugs (Secundaria)

- **crearPedido(...)**: Valida y crea objeto Pedido
  - Lanza PedidoException si hay errores de validación
  - Retorna Pedido si todo es válido

## 📦 Modelos

### TipoMedicamento (Enum)
```java
ANALGESICO("Analgésico")
ANALEPTICO("Analéptico")
ANESTESICO("Anestésico")
ANTIACIDO("Antiácido")
ANTIDEPRESIVO("Antidepresivo")
ANTIBIOTICO("Antibiótico")
```

### Distribuidor (Enum)
```java
COFARMA("Cofarma")
EMPSEPHAR("Empsephar")
CEMEFAR("Cemefar")
```

### Farmacia (Clase)
```java
{
    id: int
    nombre: String
    direccion: String
    principal: boolean
}
```

### Pedido (Clase)
```java
{
    medicamento: Medicamento
    distribuidor: Distribuidor
    farmacias: List<Farmacia>
    cantidad: int
}
```

## ⚠️ Manejo de Excepciones

### PedidoException
- Excepción custom del dominio
- Mensajes descriptivos según el tipo de error:
  - "El nombre solo puede contener caracteres alfanuméricos."
  - "Debe seleccionar un tipo de medicamento."
  - "La cantidad debe ser un número entero positivo."
  - "Debe seleccionar un distribuidor."
  - "Debe seleccionar al menos una farmacia."

## 🎨 Paleta de Colores

```java
COLOR_FONDO        = #1E2323 (30,  35,  35)  - Fondo oscuro
COLOR_PANEL        = #283030 (40,  48,  48)  - Paneles
COLOR_TEXTO        = #F0F5F5 (240, 245, 245) - Texto claro
COLOR_ACCENT       = #4DB6AC (77,  182, 172) - Verde azulado
COLOR_ACCENT_HOVER = #80CBC4 (128, 203, 196) - Verde azulado claro
```

## 📋 Validaciones Implementadas

| Campo | Validación UI | Validación Controller |
|-------|--------------|---------------------|
| Nombre | No vacío | Alfanumérico (regex: `[a-zA-Z0-9 ]+`) |
| Tipo | Seleccionado | No null |
| Cantidad | No vacío | Entero > 0 |
| Distribuidor | Seleccionado | No null |
| Farmacias | Al menos una | Lista no vacía |

## 🔧 Extensiones Futuras

1. **Base de datos**: Reemplazar lista hardcodeada en PedidoController
2. **Persistencia**: Guardar pedidos en archivo/BD
3. **Historial**: Mostrar lista de pedidos anteriores
4. **Impresión**: Generar PDF del resumen del pedido
5. **Stock**: Validar disponibilidad con inventario
6. **Autenticación**: Login para diferentes usuarios
