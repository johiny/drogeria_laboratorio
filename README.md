# Sistema de Pedidos de Medicamentos 💊

Sistema de gestión de pedidos farmacéuticos desarrollado en **Java con Swing**, siguiendo arquitectura **MVC**.

---

## 📋 Descripción del Proyecto

Aplicación de escritorio que permite a una farmacia realizar pedidos de medicamentos a distribuidores farmacéuticos. El usuario puede seleccionar el medicamento, la cantidad, el distribuidor y la(s) sucursal(es) de destino. El sistema valida los datos y genera un resumen del pedido.

---

## 🏗️ Arquitectura y Clases

El proyecto sigue el patrón **MVC (Model - View - Controller)** con un paquete adicional para manejo de excepciones.

```
src/
├── models/
│   ├── TipoMedicamento.java     → Enum con los 6 tipos de medicamento
│   ├── Distribuidor.java        → Enum con los 3 distribuidores disponibles
│   ├── Medicamento.java         → Entidad medicamento (id, nombre, tipo)
│   ├── Farmacia.java            → Entidad farmacia (id, nombre, dirección, esPrincipal)
│   └── Pedido.java              → Entidad pedido (medicamento, distribuidor, farmacias, cantidad)
│
├── view/
│   ├── FormularioPedidoView.java → Ventana principal con el formulario
│   └── ResumenPedidoView.java    → Ventana de confirmación del pedido
│
├── controller/
│   └── PedidoController.java    → Validación, lógica de negocio y datos sintéticos
│
└── handlers/
    ├── PedidoException.java     → Excepción personalizada del dominio
    └── ErrorHandler.java        → Centraliza la presentación de errores al usuario
```

### Diagrama de relaciones

```
FormularioPedidoView
        │
        ▼
PedidoController ──────────────► PedidoException
        │                               │
        ▼                               ▼
   Pedido                         ErrorHandler
    ├── Medicamento (TipoMedicamento)
    ├── Distribuidor
    ├── List<Farmacia>
    └── cantidad
```

---

## 🧠 Decisiones de Diseño

### 1. MVC como arquitectura base
Se eligió MVC porque Swing es un framework orientado a eventos visuales. Separar la vista del modelo y el controlador permite:
- Cambiar la UI sin tocar la lógica de negocio
- Testear validaciones sin levantar ventanas
- Mantener responsabilidades claras por capa

### 2. `Distribuidor` y `TipoMedicamento` como enums
Ambos representan **dominios cerrados** definidos por el negocio: exactamente 3 distribuidores y exactamente 6 tipos de medicamento. Usar `enum` en vez de `String` garantiza:
- **Type safety** en tiempo de compilación
- Imposibilidad de crear valores inválidos
- Sin necesidad de validación manual de strings

### 3. `Pedido` usa referencias a objetos, no IDs
```java
// ❌ Diseño SQL (incorrecto en OOP)
private int id_medicamento;

// ✅ Diseño OOP (correcto)
private Medicamento medicamento;
```
Java es orientado a objetos. Los objetos se referencian directamente, no por ID. Los IDs son un concepto de bases de datos relacionales, no de objetos en memoria.

### 4. `List<Farmacia>` en Pedido
El enunciado permite enviar un pedido a **una o ambas farmacias** simultáneamente. Una lista modela exactamente esa cardinalidad (1 o 2 elementos) sin restricciones artificiales.

### 5. Excepciones de dominio con `PedidoException`
En vez de retornar `null` cuando la validación falla, el Controller lanza una excepción con mensaje descriptivo. Esto permite:
- Saber **exactamente qué campo** falló
- Centralizar la presentación del error en `ErrorHandler`
- Mantener la View limpia de lógica de validación

### 6. Datos sintéticos en el Controller
Las farmacias y el catálogo de medicamentos viven en el Controller, no en la View. Son datos del dominio del negocio, no de la interfaz. Si el día de mañana se conecta una base de datos, solo se modifica el Controller.

---

## 📦 Tecnologías

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| Java | 8+ | Lenguaje principal |
| Swing | JDK nativo | Interfaz gráfica |
| Apache Ant | Nativo NetBeans | Build system |
| NetBeans IDE | Cualquiera | Entorno de desarrollo |
