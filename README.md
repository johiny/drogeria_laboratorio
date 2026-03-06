# Sistema de Pedidos de Medicamentos 💊

Sistema de gestión de pedidos farmacéuticos desarrollado en **Java con Swing**, siguiendo arquitectura **MVC**.

---

## 📋 Descripción del Proyecto

Aplicación de escritorio que permite a una farmacia realizar pedidos de medicamentos a distribuidores farmacéuticos. El usuario ingresa el nombre del medicamento, selecciona el tipo, indica la cantidad, elige el distribuidor y la(s) sucursal(es) de destino. El sistema valida los datos y genera un resumen del pedido.

---

## 🏗️ Arquitectura y Clases

El proyecto sigue el patrón **MVC (Model - View - Controller)** con un paquete adicional para manejo de excepciones.

```
src/
├── models/
│   ├── TipoMedicamento.java     → Enum con display names: Analgésico, Analéptico, etc.
│   ├── Distribuidor.java        → Enum con display names: Cofarma, Empsephar, Cemefar
│   ├── Medicamento.java         → nombre: String, tipo: TipoMedicamento
│   ├── Farmacia.java            → id, nombre, direccion, esPrincipal: boolean
│   └── Pedido.java              → medicamento, distribuidor,
│                                  farmacias: List<Farmacia>, cantidad: int
│
├── view/
│   ├── Drogueria.java           → Clase principal con método main()
│   ├── MenuPrincipal.java       → Ventana principal con formulario de pedido
│   └── ResumenPedidoView.java   → Ventana de confirmación del pedido
│
├── controller/
│   └── PedidoController.java    → Validación, lógica de negocio y datos del dominio
│
└── handlers/
    ├── PedidoException.java     → Excepción personalizada del dominio
    └── PopUpErrorHandler.java   → Centraliza la presentación de errores
```

### Componentes Swing del formulario

| Campo | Componente | Descripción |
|-------|-----------|-------------|
| Nombre medicamento | `JTextField` | El usuario escribe el nombre libremente |
| Tipo medicamento | `JComboBox` | Desplegable con los 6 tipos del enum |
| Cantidad | `JTextField` | Número entero positivo |
| Distribuidor | `JRadioButton x3` | Cofarma, Empsephar, Cemefar |
| Sucursal | `JCheckBox (dinámico)` | Se generan desde PedidoController.getFarmaciasDisponibles() |
| Borrar | `JButton` | Limpia todos los campos |
| Confirmar | `JButton` | Valida y abre ventana ResumenPedidoView |

---

## 🧠 Decisiones de Diseño

### 1. MVC como arquitectura base
Swing es un framework orientado a eventos visuales. MVC permite cambiar la UI sin tocar la lógica de negocio, testear validaciones sin levantar ventanas y mantener responsabilidades claras por capa.

### 2. Enums para dominios cerrados
`TipoMedicamento` y `Distribuidor` son enums porque el negocio define exactamente 6 tipos y 3 distribuidores. Ahora incluyen display names en español para mejorar la experiencia de usuario en la interfaz gráfica.

### 3. Referencias a objetos, no IDs
`Pedido` usa referencias directas a objetos Java, no IDs numéricos. Los IDs son un concepto relacional. En OOP los objetos se referencian directamente en memoria.

### 4. `List<Farmacia>` en Pedido
El sistema permite enviar un pedido a una o varias farmacias simultáneamente. Una lista modela exactamente esa cardinalidad sin restricciones artificiales.

### 5. Excepciones de dominio con `PedidoException`
El Controller lanza `PedidoException` con mensaje descriptivo. Esto permite saber exactamente qué campo falló y mantiene la View limpia de lógica de validación compleja.

### 6. Datos del dominio en el Controller
Las farmacias disponibles viven en el Controller, no en la View. Son datos del negocio. Si en el futuro se conecta una base de datos, solo se modifica el Controller.

### 7. Separación de ventanas
`MenuPrincipal` maneja el formulario de entrada y `ResumenPedidoView` muestra la confirmación. Esto permite evolucionar cada ventana independientemente.

---

## 📦 Tecnologías

| Tecnología | Uso |
|-----------|-----|
| Java 8+ | Lenguaje principal |
| Swing (JDK nativo) | Interfaz gráfica |
| Apache Ant | Build system (NetBeans) |
| NetBeans IDE | Entorno de desarrollo |

---

## 🚀 Cómo Compilar y Ejecutar

### Opción 1: Usando NetBeans (Recomendado)
1. Abre el proyecto en NetBeans
2. Click derecho en el proyecto → **Clean and Build**
3. Click en **Run Project** o presiona `F6`

### Opción 2: Usando Scripts de Bash
```bash
# Compilar el proyecto
./compile.sh

# Ejecutar la aplicación
./run.sh
```

### Opción 3: Manualmente con javac
```bash
# Compilar
javac -d build/classes -sourcepath src src/view/Drogueria.java

# Ejecutar
java -cp build/classes view.Drogueria
```

---

## 🎨 Características de la Interfaz

- **Diseño moderno**: Esquema de colores pastel medicinal (verde azulado)
- **Componentes personalizados**: Radio buttons y checkboxes con iconos custom
- **Validación en tiempo real**: Mensajes de error descriptivos
- **Responsive**: GridBagLayout para distribución adaptable
- **Efectos hover**: Feedback visual en botones

---

## 📸 Estructura del Flujo

1. Usuario llena el formulario en `MenuPrincipal`
2. Click en "Confirmar" → Validación con `PedidoController`
3. Si hay errores → Muestra JOptionPane con campos faltantes
4. Si es válido → Abre `ResumenPedidoView` con el mensaje de confirmación
5. Usuario puede hacer nuevo pedido con "Borrar"