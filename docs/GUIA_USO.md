# Sistema Facturador -- Guia de uso

## 1. Ingreso al sistema

### 1.1 Inicio de sesion

Al ejecutar la aplicacion, se muestra la pantalla de login con dos campos:

- **Mail:** Ingrese su correo electronico registrado
- **Contrasena:** Ingrese su contrasena (oculta con asteriscos)

```mermaid
flowchart TD
    A[Pantalla Login] --> B{Ingresa mail y password}
    B --> C{Click btnIngresar}
    C --> D{Algun campo vacio?}
    D -->|Si| E[Muestra: Ingrese ambos datos]
    D -->|No| F[ControladorLogin.validar]
    F --> G{Busca usuario en BD}
    G --> H{Existe y password coincide?}
    H -->|No| I[Muestra: Credenciales incorrectas]
    H -->|Si| J[Obtiene rol del usuario]
    J --> K[Redirige segun rol]
```

**Botones:**
- **Ingresar:** Valida credenciales y redirige segun el rol del usuario
- **Crear cuenta:** Abre el formulario de registro

### 1.2 Crear cuenta

Al hacer click en "Crear cuenta" se abre el formulario de registro con
los siguientes campos:

- Nombre (solo letras y espacios)
- Apellido (solo letras y espacios)
- DNI (solo digitos)
- Telefono (solo digitos)
- Direccion (letras, digitos y caracteres especiales)
- Mail (sin filtro en tiempo real)
- Contrasena (oculta con asteriscos)

```mermaid
flowchart TD
    A[Formulario Registro] --> B{Completa campos}
    B --> C{Click btnCrearUsuario}
    C --> D[Valida campos requeridos]
    D -->|Faltan| E[Muestra: Los siguientes campos son obligatorios: ...]
    D -->|Ok| F[ControladorRegistro.registrar]
    F --> G{Formato mail valido?}
    G -->|No| H[Muestra: El formato del mail no es valido]
    G -->|Si| I{Mail ya registrado?}
    I -->|Si| J[Muestra: Este mail ya ha sido registrado]
    I -->|No| K[INSERT usuario con rol Ninguno]
    K --> L[Muestra: Registro exitoso]
    L --> M[Vuelve a pantalla de login]
```

Al registrarse, el nuevo usuario obtiene el rol `"Ninguno"`.
Un administrador debe asignarle un rol desde el panel de gestion
de usuarios.

---

## 2. Rol Administrador

### 2.1 Panel principal

Al iniciar sesion como Administrador, se muestra el panel principal
con las siguientes opciones:

- **Administrar Clientes** -- ABM completo de clientes
- **Administrar Proveedores** -- ABM de proveedores
- **Administrar Usuarios** -- Gestion de usuarios del sistema
- **Administrar Deposito** -- Gestion de stock/productos
- **Facturar** -- Modulo de ventas (mismo que el rol Cajero)
- **Cerrar Sesion** -- Vuelve al login

### 2.2 Gestion de clientes

```mermaid
flowchart TD
    A[Panel Clientes] --> B[Selecciona filtro Habilitados/Deshabilitados]
    A --> C[Ingresa DNI en campo de busqueda]
    C --> D{Click btnBuscar}
    D --> E[Busca cliente por DNI exacto]
    E --> F{Existe?}
    F -->|No| G[Muestra: No existe el cliente con ese dni]
    F -->|Si| H[Muestra cliente en la tabla]

    A --> I{Click btnAgregar}
    I --> J[Abre dialogo: Nuevo Cliente]
    J --> K[Completa: Nombre, Apellido, DNI, Telefono, Direccion, Mail]
    K --> L{Click OK}
    L --> M[Valida campos requeridos]
    M -->|Faltan| N[Muestra lista de obligatorios, no agrega]
    M -->|Ok| O[Controlador.agregarCliente]
    O --> P{Formato mail valido?}
    P -->|No| Q[Muestra error, no agrega]
    P -->|Si| R{Mail unico en BD?}
    R -->|No| S[Muestra: mail ya registrado]
    R -->|Si| T[INSERT cliente]
    T --> U[Muestra: Cliente agregado exitosamente]
    U --> V[Actualiza tabla]

    A --> W{Selecciona fila + btnModificar}
    W --> X[Abre dialogo con valores actuales]
    X --> Y[Modifica campos]
    Y --> Z{Click OK}
    Z --> AA[Valida campos requeridos]
    AA -->|Faltan| AB[Muestra lista de obligatorios]
    AA -->|Ok| AC[Misma validacion de mail]
    AC --> AD[UPDATE cliente]
    AD --> AE[Muestra: modificado exitosamente]

    A --> AB{Selecciona fila + btnDeshabilitar}
    AB --> AC{Confirma deshabilitar?}
    AC -->|No| AD[No hace nada]
    AC -->|Si| AE[UPDATE habilitado = CASE]
    AE --> AF[Muestra: Estado cambiado exitosamente]

    A --> AG{Selecciona fila + btnMostrarFacturas}
    AG --> AH[Abre VistaFactura con facturas del cliente]
```

**Campos del formulario:**

| Campo | Filtro en tiempo real | Validacion adicional |
|---|---|---|
| Nombre | Solo letras + espacios | -- |
| Apellido | Solo letras + espacios | -- |
| DNI | Solo digitos | -- |
| Telefono | Solo digitos | -- |
| Direccion | Letras, digitos, @._- | -- |
| Mail | Sin filtro | Formato xxxx@xxxx.xxx al enviar |

**Busqueda:** Ingresar DNI exacto en el campo `tfDni` (FiltroNumerico)
y click en "Buscar". Si se deja vacio y se busca, muestra todos los
clientes del filtro seleccionado.

### 2.3 Gestion de proveedores

Idem clientes pero con los campos: Nombre, Telefono, Direccion, Mail.
La busqueda se realiza por ID (numerico). No tiene modulo de facturas.

### 2.4 Gestion de usuarios

```mermaid
flowchart TD
    A[Panel Usuarios] --> B[Buscar por DNI]
    A --> C[Filtrar Habilitados/Deshabilitados]

    A --> D{Click btnAgregar}
    D --> E[Dialogo: Nuevo Usuario]
    E --> F[Campos: Nombre, Apellido, DNI, Telefono, Direccion, Mail, Contrasena, Rol]
    F --> G{Click OK}
    G --> H[Valida campos requeridos]
    H -->|Faltan| I[Muestra lista de obligatorios]
    H -->|Ok| J[Valida mail + unicidad]
    J --> K[INSERT usuario con rol elegido]

    A --> L{Selecciona fila + btnModificar}
    L --> M[Dialogo con valores actuales + Contrasena editable]
    M --> N{Click OK}
    N --> O[Valida campos requeridos]
    O -->|Faltan| P[Muestra lista de obligatorios]
    O -->|Ok| Q[Valida mail + UPDATE]

    A --> R{Selecciona fila + btnDeshabilitar}
    R --> S[Confirma: Deshabilitar/Habilitar]
    S --> T{Es admin habilitado?}
    T -->|Si| U[Hay otro admin disponible?]
    U -->|No| V[Muestra: No se puede deshabilitar al unico admin]
    U -->|Si| W[UPDATE habilitado]
    T -->|No| X[UPDATE habilitado]
```

**Particularidades:**
- La columna "Habilitado" esta oculta en la tabla
- El campo Contrasena se muestra como JPasswordField
- Rol se selecciona de un JComboBox (Administrador, Cajero, Deposito)
- Al modificar, el campo Contrasena trae el valor actual

### 2.5 Gestion de deposito (productos)

```mermaid
flowchart TD
    A[Panel Productos] --> B[Filtrar Habilitados/Deshabilitados]
    A --> C[Buscar por ID o descripcion]

    A --> D{Click btnAgregar}
    D --> E[Dialogo: Nuevo Producto]
    E --> F[Completa: Descripcion, Precio, Stock, Proveedor]
    F --> G{Click OK}
    G --> H[Valida campos requeridos]
    H -->|Faltan| I[Muestra lista de obligatorios]
    H -->|Ok| J[INSERT producto con proveedor]
    J --> K[Muestra: Producto agregado exitosamente]

    A --> L{Selecciona fila + btnModificar}
    L --> M[Dialogo con valores actuales]
    M --> N[Modifica y confirma]
    N --> O[Valida campos requeridos]
    O -->|Faltan| P[Muestra lista de obligatorios]
    O -->|Ok| Q[UPDATE producto con proveedor]
```

**Particularidades:**
- La busqueda acepta tanto ID numerico como texto (busca por descripcion LIKE)
- No tiene validacion de mail (los productos no tienen mail)
- Precio y Stock usan FiltroNumerico
- Descripcion y Proveedor son campos obligatorios
- Proveedor se selecciona de un JComboBox cargado con proveedores habilitados

---

## 3. Rol Cajero -- Proceso de venta

```mermaid
flowchart TD
    A[Panel Caja] --> B[Buscar cliente por DNI]
    B --> C{Existe?}
    C -->|No| D{Puede crear cliente nuevo}
    C -->|Si| E[Muestra datos del cliente]

    E --> F[Buscar producto por ID o descripcion]
    F --> G{Existe?}
    G -->|No| H{Puede crear producto nuevo}
    G -->|Si| I[Muestra datos del producto]

    I --> J[Ingresa cantidad y descuento %]
    J --> K{Click Agregar al carro}
    K --> L{Cantidad > 0?}
    L -->|No| M[Muestra error]
    L -->|Si| N[Agrega fila al carrito]
    N --> O[Actualiza subtotal del carrito]
    O --> P[Limpia campos de producto]

    P --> Q{Repite o finaliza?}
    Q -->|Mas productos| F
    Q -->|Finalizar| R[Ingresa descuento global %]
    R --> S{Click Calcular total}
    S --> T{Descuento entre 0 y 100?}
    T -->|No| U[Muestra error]
    T -->|Si| V[Muestra total y valor descontado]

    V --> W{Click Finalizar compra}
    W --> X{Cliente seleccionado?}
    X -->|No| Y[Muestra error]
    X -->|Si| Z{Carrito vacio?}
    Z -->|Si| AA[Muestra error]
    Z -->|No| AB{Total calculado?}
    AB -->|No| AC[Muestra error]
    AB -->|Si| AD[Confirma finalizacion]
    AD --> AE{Confirma?}
    AE -->|No| AF[No hace nada]
    AE -->|Si| AG[ControladorCajero.finalizarCompra]
    AG --> AH[Transaccion: INSERT factura + INSERT detalles + UPDATE stock]
    AH --> AI{Stock suficiente?}
    AI -->|No| AJ[ROLLBACK + Muestra error]
    AI -->|Si| AK[COMMIT + Muestra factura N]
    AK --> AL[Limpia carrito y campos]

    A --> AM{Click Modificar articulo}
    AM --> AN[Selecciona fila del carrito]
    AN --> AO[Dialogo: modificar cantidad y descuento]
    AO --> AP[Actualiza fila y recalcula totales]

    A --> AQ{Click Eliminar articulo}
    AQ --> AR[Confirma eliminacion]
    AR --> AS[Elimina fila del carrito]
    AS --> AT[Actualiza totales]

    A --> AU{Click Cancelar compra}
    AU --> AV{Confirma cancelacion?}
    AV -->|Si| AW[Limpia todo el carrito]

    A --> AX{Click Cerrar Sesion}
    AX --> AY[Vuelve al login segun rol]
```

### 3.1 Seleccion de cliente

1. Ingresar DNI del cliente en el campo `tfBuscarCliente`
2. Click en "Buscar cliente"
3. Si existe: se cargan automaticamente todos los datos del cliente
   (nombre, apellido, DNI, direccion, telefono, mail)
4. Si no existe: se muestra mensaje y se limpian los campos

Si el cliente no existe, se puede crear uno nuevo con `btnNuevoCliente`.
Tambien se puede modificar el cliente seleccionado con `btnModificarCliente`.

### 3.2 Seleccion de producto

1. Ingresar ID o descripcion en `tfBuscarProducto`
2. Click en "Buscar producto"
3. Si existe exactamente 1: se cargan descripcion, precio y stock
4. Si hay multiples: se muestra una lista para seleccionar cual cargar
5. Si no existe: se puede crear uno nuevo con `btnNuevoProducto`

### 3.3 Agregar al carrito

1. Ingresar cantidad (debe ser > 0)
2. Opcional: ingresar descuento % para ese producto
3. Click "Agregar al carro"
4. Se agrega una fila a la tabla del carrito con:
   ID, Descripcion, Precio Unit., Cantidad, Descuento %, Subtotal
5. El subtotal general se actualiza automaticamente

### 3.4 Gestion del carrito

- **Modificar articulo:** seleccionar fila, click "Modificar articulo",
  cambiar cantidad y/o descuento
- **Eliminar articulo:** seleccionar fila, click "Eliminar articulo",
  confirmar
- **Cancelar compra:** elimina todos los articulos del carrito

### 3.5 Calcular total y finalizar

1. Opcional: ingresar descuento global en `tfDescuento` (0-100)
2. Click "Calcular total" -> muestra total y valor descontado
3. Click "Finalizar compra"
4. Validaciones:
   - Debe haber un cliente seleccionado
   - El carrito no puede estar vacio
   - Debe haberse calculado el total
5. Confirmacion: "Confirmar finalizacion de la compra?"
6. Si ok: transaccion en BD que incluye:
   - INSERT en tabla facturas
   - INSERT batch en detalles_de_facturas
   - UPDATE stock de cada producto
   - Si algun producto tiene stock insuficiente -> rollback + error
7. Exito: muestra "Compra finalizada exitosamente. Factura N°: ..."
   y limpia todo para una nueva venta

### 3.6 Ver facturas de un cliente

Desde el panel de Administrador, seleccionar un cliente en la tabla
y click en "Mostrar facturas". Se abre una vista con:

- Lista de facturas del cliente (N°, Fecha, Total, Vendedor)
- Click en "Ver detalle" para ver la factura completa:
  - Numero de factura y fecha
  - Datos del cliente y vendedor
  - Subtotal, descuento, valor descontado, total
  - Tabla con los productos comprados (cantidad, precio unitario,
    descuento, subtotal)

---

## 4. Rol Deposito

El usuario con rol Deposito ve directamente el panel de gestion de
productos (VistaDepositoABM) al iniciar sesion.

**Funcionalidades:**
- Filtrar productos habilitados/deshabilitados
- Buscar producto por ID o descripcion
- Agregar nuevo producto (descripcion, precio, stock)
- Modificar producto existente
- Deshabilitar/habilitar producto

No tiene acceso a clientes, proveedores, usuarios ni modulo de ventas.

---

## 5. Mensajes de error comunes

| Mensaje | Donde aparece | Causa probable |
|---|---|---|
| "Ingrese ambos datos" | Login | Mail o password vacio |
| "Credenciales incorrectas" | Login | Mail no existe o password incorrecto |
| "Sin rol asignado" | Login | Usuario registrado con rol Ninguno |
| "El formato del mail no es valido" | ABMs / Registro | Mail no cumple formato xxxx@xxxx.xxx |
| "Este mail ya ha sido registrado" | Agregar cliente/proveedor/usuario | Mail duplicado en BD |
| "Ese mail ya pertenece a otro cliente" | Modificar cliente | Mail ya usado por otro registro |
| "Los siguientes campos son obligatorios: ..." | Registro / ABMs | Campos requeridos vacios (Nombre, Apellido, DNI, Mail, Password, Proveedor, etc.) |
| "No se puede deshabilitar al unico administrador habilitado" | Gestion Usuarios | Intento de deshabilitar el unico admin activo |
| "No existe el cliente con ese dni" | Buscar cliente | DNI no encontrado |
| "Seleccione un cliente de la tabla" | Modificar cliente | Ninguna fila seleccionada |
| "Seleccione un proveedor de la tabla" | Modificar proveedor | Ninguna fila seleccionada |
| "La cantidad debe ser mayor a 0" | Agregar al carro | Cantidad <= 0 |
| "El descuento debe ser entre 0 y 100" | Calcular total / Modificar articulo | Descuento fuera de rango |
| "Debe seleccionar un cliente" | Finalizar compra | No hay cliente cargado |
| "El carrito esta vacio" | Finalizar compra | No hay productos en el carrito |
| "Debe calcular el total primero" | Finalizar compra | No se calculo el total |
| "Stock insuficiente para el producto ID: N" | Finalizar compra | Stock menor a la cantidad solicitada |
| "No hay producto seleccionado" | Modificar/Agregar al carro | No se busco un producto primero |
| "No hay articulos en el carrito" | Calcular total | Carrito vacio |
