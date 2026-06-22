# Referencia de metodos del sistema

> Documentacion tecnica de todos los metodos del sistema Facturador.
> Formato JavaDoc-like. Generado a partir del codigo fuente.

---

## 1. Paquete `aplicacion.modelos`

Clases POJO (Plain Old Java Objects) que representan las entidades del
dominio. Cada clase tiene constructor vacio, getters y setters para
todos sus campos.

---

### `Usuario`

`aplicacion.modelos.Usuario`

Representa un usuario del sistema. Puede tener rol Administrador,
Cajero, Deposito o Ninguno.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico |
| nombre | `String` | Nombre del usuario |
| apellido | `String` | Apellido del usuario |
| dni | `String` | Documento de identidad |
| telefono | `String` | Numero telefonico |
| direccion | `String` | Domicilio |
| mail | `String` | Correo electronico (UNIQUE en BD) |
| rol | `String` | Administrador, Cajero, Deposito o Ninguno |
| password | `String` | Contrasena en texto plano |
| habilitado | `int` | 1 = activo, 0 = deshabilitado |

#### `Usuario()`
- **Descripcion:** Constructor vacio.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.
- **Retorna (get):** El valor del campo correspondiente.
- **Parametros (set):** El nuevo valor del campo.

---

### `Cliente`

`aplicacion.modelos.Cliente`

Representa un cliente registrado en el sistema.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico |
| nombre | `String` | Nombre del cliente |
| apellido | `String` | Apellido del cliente |
| dni | `String` | Documento de identidad |
| telefono | `String` | Numero telefonico |
| direccion | `String` | Domicilio |
| mail | `String` | Correo electronico (UNIQUE en BD) |
| habilitado | `int` | 1 = activo, 0 = deshabilitado |

#### `Cliente()`
- **Descripcion:** Constructor vacio.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.

---

### `Proveedor`

`aplicacion.modelos.Proveedor`

Representa un proveedor registrado en el sistema.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico |
| nombre | `String` | Nombre del proveedor |
| direccion | `String` | Domicilio |
| telefono | `String` | Numero telefonico |
| mail | `String` | Correo electronico (UNIQUE en BD) |
| habilitado | `int` | 1 = activo, 0 = deshabilitado |

#### `Proveedor()`
- **Descripcion:** Constructor vacio.

#### `Proveedor(int id, String nombre, String direccion, String telefono, String mail, int habilitado)`
- **Descripcion:** Constructor con todos los campos.
- **Parametros:**
  - `id` — Identificador unico.
  - `nombre` — Nombre del proveedor.
  - `direccion` — Domicilio.
  - `telefono` — Numero telefonico.
  - `mail` — Correo electronico.
  - `habilitado` — 1 activo, 0 deshabilitado.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.

---

### `Producto`

`aplicacion.modelos.Producto`

Representa un producto en el deposito, opcionalmente asociado a un proveedor.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico |
| descripcion | `String` | Nombre o descripcion del producto |
| precio | `int` | Precio unitario en pesos |
| stock | `int` | Cantidad disponible |
| habilitado | `int` | 1 = activo, 0 = deshabilitado |
| idProveedor | `int` | FK hacia proveedores(id), 0 si no tiene |
| nombreProveedor | `String` | Nombre del proveedor (JOIN en consultas) |

#### `Producto()`
- **Descripcion:** Constructor vacio.

#### `Producto(int id, String descripcion, int precio, int stock)`
- **Descripcion:** Constructor sin habilitado.
- **Parametros:**
  - `id` — Identificador unico.
  - `descripcion` — Nombre o descripcion.
  - `precio` — Precio unitario.
  - `stock` — Cantidad disponible.

#### `Producto(int id, String descripcion, int precio, int stock, int habilitado)`
- **Descripcion:** Constructor con todos los campos basicos.
- **Parametros:**
  - `id` — Identificador unico.
  - `descripcion` — Nombre o descripcion.
  - `precio` — Precio unitario.
  - `stock` — Cantidad disponible.
  - `habilitado` — 1 activo, 0 deshabilitado.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.
- **Retorna (get):** El valor del campo correspondiente.

---

### `Factura`

`aplicacion.modelos.Factura`

Representa una factura emitida. Contiene datos del cliente y vendedor
al momento de la compra, ademas de una lista de detalles.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico |
| numeroFactura | `String` | Formato FACT-YYYYMMDD-HHmmss |
| idCliente | `int` | FK hacia clientes(id) |
| nombreCliente | `String` | Nombre del cliente al momento de la compra |
| apellidoCliente | `String` | Apellido del cliente |
| dniCliente | `String` | DNI del cliente |
| direccionCliente | `String` | Direccion del cliente |
| idVendedor | `int` | FK hacia usuarios(id) |
| nombreVendedor | `String` | Nombre del vendedor |
| apellidoVendedor | `String` | Apellido del vendedor |
| fechaEmision | `LocalDate` | Fecha de la factura |
| subtotal | `int` | Suma de subtotales de detalles sin descuento global |
| descuentoPorcentaje | `int` | Descuento global aplicado (0-100) |
| valorDescontado | `int` | Monto descontado (subtotal * descuento / 100) |
| totalCompra | `int` | Monto final (subtotal - valorDescontado) |
| detalles | `List<DetalleFactura>` | Lineas de la factura |

#### `Factura()`
- **Descripcion:** Constructor vacio.

#### `Factura(int id, String numeroFactura, int idCliente, String nombreCliente, String apellidoCliente, String dniCliente, String direccionCliente, int idVendedor, String nombreVendedor, String apellidoVendedor, LocalDate fechaEmision, int subtotal, int descuentoPorcentaje, int valorDescontado, int totalCompra)`
- **Descripcion:** Constructor con todos los campos excepto detalles.
- **Parametros:**
  - `id` — Identificador unico.
  - `numeroFactura` — Numero de factura generado.
  - `idCliente` — FK del cliente.
  - `nombreCliente` — Nombre del cliente.
  - `apellidoCliente` — Apellido del cliente.
  - `dniCliente` — DNI del cliente.
  - `direccionCliente` — Direccion del cliente.
  - `idVendedor` — FK del vendedor.
  - `nombreVendedor` — Nombre del vendedor.
  - `apellidoVendedor` — Apellido del vendedor.
  - `fechaEmision` — Fecha de emision.
  - `subtotal` — Subtotal antes de descuento.
  - `descuentoPorcentaje` — Descuento global porcentual.
  - `valorDescontado` — Monto descontado.
  - `totalCompra` — Total final de la compra.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.

---

### `DetalleFactura`

`aplicacion.modelos.DetalleFactura`

Representa una linea de detalle dentro de una factura.

**Campos:**

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | `int` | Identificador unico del detalle |
| descripcion | `String` | Nombre del producto (JOIN con productos) |
| cantidad | `int` | Cantidad comprada |
| precioUnitario | `int` | Precio unitario al momento de la compra |
| descuento | `int` | Descuento aplicado a esa linea (0-100) |
| subtotal | `int` | precio * cantidad * (100 - descuento) / 100 |

#### `DetalleFactura()`
- **Descripcion:** Constructor vacio.

#### `DetalleFactura(int id, String descripcion, int cantidad, int precioUnitario, int descuento, int subtotal)`
- **Descripcion:** Constructor con todos los campos.
- **Parametros:**
  - `id` — Identificador unico.
  - `descripcion` — Nombre del producto.
  - `cantidad` — Cantidad comprada.
  - `precioUnitario` — Precio unitario.
  - `descuento` — Descuento porcentual de la linea.
  - `subtotal` — Subtotal calculado.

#### `get / set` para cada campo
- **Descripcion:** Getter/setter estandar.

---

## 2. Paquete `aplicacion.servicios`

Clases de infraestructura: conexion a base de datos y autenticacion.

---

### `Conexion`

`aplicacion.servicios.Conexion`

Gestiona la conexion a la base de datos MySQL.

**Campos:**
- `private Connection con` — Objeto de conexion JDBC.

#### `Conexion()`
- **Descripcion:** Constructor vacio. No abre conexion.

#### `void conectar()`
- **Descripcion:** Establece conexion a MySQL.
- **Algoritmo:**
  1. Carga el driver con `Class.forName("com.mysql.cj.jdbc.Driver")`.
  2. Conecta via `DriverManager.getConnection()` con:
     - URL: `jdbc:mysql://localhost:3306/comercial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
     - User: `root`
     - Password: `1284`
- **Lanza:** `RuntimeException` si falla `Class.forName` o `DriverManager.getConnection`.

#### `Connection getConnection()`
- **Descripcion:** Retorna la conexion activa.
- **Retorna:** `Connection` — el objeto de conexion JDBC.
- **Nota:** Debe llamarse a `conectar()` antes de este metodo.

---

### `Autenticacion`

`aplicacion.servicios.Autenticacion`

Valida credenciales de usuario comparando mail y password.

**Campos:**
- `private String mail` — Mail ingresado en login.
- `private String password` — Password ingresada en login.

#### `Autenticacion(String mail, String password)`
- **Descripcion:** Almacena las credenciales a validar.
- **Parametros:**
  - `mail` — Mail ingresado por el usuario.
  - `password` — Password ingresada por el usuario.

#### `boolean autenticar(Usuario usuario)`
- **Descripcion:** Compara las credenciales almacenadas con las del usuario obtenido de BD.
- **Parametros:**
  - `usuario` — Objeto Usuario con los datos de la BD.
- **Retorna:** `boolean` — `true` si el mail y password del usuario coinciden con los almacenados.
- **Algoritmo:**
  1. Compara `this.mail` con `usuario.getMail()`.
  2. Compara `this.password` con `usuario.getPassword()`.
  3. Retorna `true` solo si ambos coinciden.

---

## 3. Paquete `aplicacion.filtros`

Filtros de entrada para controles Swing (`DocumentFilter`), validadores
de campos requeridos y de cantidad de administradores.

---

### `FiltroNumerico`

`aplicacion.filtros.FiltroNumerico`

`extends javax.swing.text.DocumentFilter`

Filtra caracteres para permitir solo digitos (0-9).

#### `void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)`
- **Descripcion:** Intercepta insercion de texto y elimina caracteres no numericos.
- **Parametros:**
  - `fb` — Objeto FilterBypass para aplicar el cambio filtrado.
  - `offset` — Posicion de insercion.
  - `text` — Texto a insertar (se filtraran los no digitos).
  - `attr` — Atributos del texto.
- **Algoritmo:** Recorre `text` y conserva solo `Character.isDigit(c)`.

#### `void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)`
- **Descripcion:** Intercepta reemplazo de texto y elimina caracteres no numericos.
- **Parametros:**
  - `fb` — Objeto FilterBypass.
  - `offset` — Posicion de inicio del reemplazo.
  - `length` — Cantidad de caracteres a reemplazar.
  - `text` — Texto de reemplazo (se filtraran los no digitos).
  - `attr` — Atributos del texto.
- **Algoritmo:** Recorre `text` y conserva solo `Character.isDigit(c)`.

---

### `FiltroTexto`

`aplicacion.filtros.FiltroTexto`

`extends javax.swing.text.DocumentFilter`

Filtra caracteres para permitir solo letras y espacios.

#### `void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)`
- **Descripcion:** Intercepta insercion y conserva solo letras y espacios.
- **Algoritmo:** Recorre `text` y conserva `Character.isLetter(c) || c == ' '`.

#### `void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)`
- **Descripcion:** Intercepta reemplazo y conserva solo letras y espacios.
- **Algoritmo:** Recorre `text` y conserva `Character.isLetter(c) || c == ' '`.

---

### `FiltroAlfanumerico`

`aplicacion.filtros.FiltroAlfanumerico`

`extends javax.swing.text.DocumentFilter`

Filtra caracteres para permitir letras, digitos, espacios y los
caracteres especiales `@`, `.`, `_`, `-`.

#### `void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)`
- **Descripcion:** Intercepta insercion y conserva caracteres alfanumericos, espacios, `@`, `.`, `_`, `-`.
- **Algoritmo:** Recorre `text` con `Character.isLetterOrDigit(c) || c == ' ' || c == '@' || c == '.' || c == '_' || c == '-'`.

#### `void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)`
- **Descripcion:** Intercepta reemplazo con el mismo filtro.
- **Algoritmo:** Mismo filtro que `insertString`.

---

### `ValidadorMail`

`aplicacion.filtros.ValidadorMail`

Validador de formato de correo electronico.

#### `static boolean esValido(String mail)`
- **Descripcion:** Verifica si un string cumple el formato basico de email.
- **Parametros:**
  - `mail` — Cadena a validar.
- **Retorna:** `boolean` — `true` si el formato es valido, `false` si es `null` o no coincide con el patron.
- **Algoritmo:** Aplica regex `^[\w.-]+@[\w.-]+\.\w{2,}$`.
  - `[\w.-]+` — uno o mas caracteres alfanumericos, punto o guion.
  - `@` — arroba literal.
  - `[\w.-]+` — uno o mas caracteres alfanumericos, punto o guion.
  - `\.` — punto literal.
  - `\w{2,}` — dos o mas caracteres alfanumericos (dominio de nivel superior).

---

### `ValidadorCampos`

`aplicacion.filtros.ValidadorCampos`

Validador de campos requeridos en formularios.

#### `static boolean validarRequeridos(String[][] campos)`
- **Descripcion:** Verifica que ningun campo de la lista este vacio o null.
- **Parametros:**
  - `campos` — Arreglo `{nombre, valor}` por cada campo a validar.
- **Retorna:** `boolean` — `true` si todos los campos tienen valor, `false` si alguno esta vacio.
- **Algoritmo:** Itera los pares. Si algun `valor` es `null` o `trim().isEmpty()`,
  lo agrega a un mensaje acumulador. Al final, si hay faltantes muestra
  `JOptionPane("Los siguientes campos son obligatorios: ...")` y retorna `false`.

---

### `ValidadorCantidadAdmin`

`aplicacion.filtros.ValidadorCantidadAdmin`

Evita deshabilitar al unico administrador habilitado en el sistema.

#### `static boolean permitirDeshabilitar(int idUsuario)`
- **Descripcion:** Verifica si existe al menos otro administrador habilitado
  ademas del que se intenta deshabilitar.
- **Parametros:**
  - `idUsuario` — ID del usuario que se quiere deshabilitar.
- **Retorna:** `boolean` — `true` si hay otro admin disponible, `false` si es el unico.
- **Algoritmo:**
  1. `SELECT COUNT(*) FROM usuarios WHERE rol = 'Administrador' AND habilitado = 1 AND id != ?`
  2. Si `count == 0`: `JOptionPane("No se puede deshabilitar al unico administrador habilitado")`,
     retorna `false`.
  3. Si `count > 0`: retorna `true`.

---

## 4. Paquete `aplicacion.controladores`

Logica de negocio y acceso a base de datos. Cada controlador contiene
los metodos CRUD de su entidad correspondiente.

---

### `ControladorLogin`

`aplicacion.controladores.ControladorLogin`

Controla el inicio de sesion y redireccion por rol.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.
- `private Usuario usuario = new Usuario()` — Usuario autenticado.

#### `ControladorLogin()`
- **Descripcion:** Constructor vacio.

#### `ControladorLogin(VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de login.
- **Parametros:**
  - `ventanaPrincipal` — JFrame contenedor.
- **Algoritmo:**
  1. Crea `VistaLogin(ventanaPrincipal)`.
  2. Toma `vista.panelLogin`.
  3. `ventanaPrincipal.mostrarVista(panel)`.

#### `boolean validar(String mail, String password)`
- **Descripcion:** Valida credenciales contra la base de datos.
- **Parametros:**
  - `mail` — Correo electronico ingresado.
  - `password` — Contrasena ingresada.
- **Retorna:** `boolean` — `true` si las credenciales son correctas.
- **Algoritmo:**
  1. `c.conectar()`.
  2. `SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password FROM usuarios WHERE mail = ?`.
  3. Si `rs.next()`:
     - Setea todos los campos en `this.usuario`.
     - Crea `Autenticacion(mail, password)`.
     - Retorna `autenticacion.autenticar(usuario)`.
  4. Si no existe: retorna `false`.
- **SQL:**
  ```sql
  SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password
  FROM usuarios WHERE mail = ?
  ```

#### `void iniciarSesion(VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Redirige segun el rol del usuario autenticado.
- **Parametros:**
  - `ventanaPrincipal` — JFrame contenedor.
- **Algoritmo:**
  - `"Administrador"` → `new ControladorAdmin(usuario, ventanaPrincipal)`.
  - `"Cajero"` → `new ControladorCajero(usuario, ventanaPrincipal)`.
  - `"Deposito"` → `new ControladorDepositoABM(usuario, ventanaPrincipal)`.
  - `"Ninguno"` → `JOptionPane("Sin rol asignado. Contacte a un administrador")`.
  - default → `JOptionPane("USUARIO INVALIDO")`.

---

### `ControladorRegistro`

`aplicacion.controladores.ControladorRegistro`

Controla el registro de nuevos usuarios.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

#### `ControladorRegistro()`
- **Descripcion:** Constructor vacio.

#### `ControladorRegistro(VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de registro.
- **Parametros:**
  - `ventanaPrincipal` — JFrame contenedor.

#### `boolean registrar(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password)`
- **Descripcion:** Registra un nuevo usuario con rol "Ninguno".
- **Parametros:**
  - `nombre` — Nombre del usuario.
  - `apellido` — Apellido del usuario.
  - `dni` — Documento de identidad.
  - `telefono` — Numero telefonico.
  - `direccion` — Domicilio.
  - `mail` — Correo electronico.
  - `password` — Contrasena.
- **Retorna:** `boolean` — `true` si el registro es exitoso.
- **Validaciones (orden):**
  1. `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail, password})`:
     si `false` → retorna `false`.
  2. `ValidadorMail.esValido(mail)`: si `false` → `JOptionPane("El formato del mail no es valido")`, retorna `false`.
  3. `SELECT mail FROM usuarios WHERE mail = ?`: si existe → `JOptionPane("Este mail ya ha sido registrado")`, retorna `false`.
- **SQL insert:**
  ```sql
  INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password)
  VALUES(?, ?, ?, ?, ?, ?, 'Ninguno', ?)
  ```
- **Post-insercion:** `JOptionPane("Registro exitoso")`.

---

### `ControladorAdmin`

`aplicacion.controladores.ControladorAdmin`

Controlador del panel principal del Administrador. No contiene metodos
de negocio; la navegacion se maneja desde los listeners de `VistaAdmin`.

#### `ControladorAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra el panel de administrador.
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
- **Algoritmo:**
  1. Crea `VistaAdmin(usuario, ventanaPrincipal)`.
  2. Muestra `vista.panelAdmin`.

---

### `ControladorCajero`

`aplicacion.controladores.ControladorCajero`

Controla el modulo de ventas (caja).

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.
- `private Usuario usuario` — Usuario vendedor.

#### `ControladorCajero()`
- **Descripcion:** Constructor vacio.

#### `ControladorCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la interfaz de caja.
- **Parametros:**
  - `usuario` — Vendedor autenticado.
  - `ventanaPrincipal` — JFrame contenedor.

#### `String generarNumeroFactura()`
- **Descripcion:** Genera un numero de factura unico basado en timestamp.
- **Retorna:** `String` con formato `"FACT-" + yyyyMMdd-HHmmss`.
  - Ejemplo: `FACT-20260622-045030`.

#### `boolean finalizarCompra(int idCliente, String nombreCliente, String apellidoCliente, int idVendedor, String nombreVendedor, String apellidoVendedor, List<Object[]> carrito, int subtotal, int descuentoPorcentaje, int valorDescontado, int totalCompra)`
- **Descripcion:** Procesa la compra completa en una transaccion. Inserta factura, detalles y descuenta stock.
- **Parametros:**
  - `idCliente` — FK del cliente.
  - `nombreCliente` — Nombre del cliente.
  - `apellidoCliente` — Apellido del cliente.
  - `idVendedor` — FK del vendedor.
  - `nombreVendedor` — Nombre del vendedor.
  - `apellidoVendedor` — Apellido del vendedor.
  - `carrito` — Lista de arreglos, cada uno con: `[idProducto (int), descripcion (String), precio (int), cantidad (int), descuento (int), subtotal (int)]`.
  - `subtotal` — Suma de subtotales sin descuento global.
  - `descuentoPorcentaje` — Descuento global (0-100).
  - `valorDescontado` — Monto descontado.
  - `totalCompra` — Total final de la compra.
- **Retorna:** `boolean` — `true` si la compra se finaliza correctamente.
- **Algoritmo:**
  1. `c.conectar()`.
  2. `c.getConnection().setAutoCommit(false)` — inicia transaccion.
  3. Genera numero de factura: `generarNumeroFactura()`.
  4. **INSERT factura:**
     ```sql
     INSERT INTO facturas(numero_factura, id_cliente, nombre_cliente, apellido_cliente,
                          id_vendedor, nombre_vendedor, apellido_vendedor, fecha_emision,
                          subtotal, descuento_porcentaje, valor_descontado, total_compra)
     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
     ```
     Con `RETURN_GENERATED_KEYS`. Obtiene `idFactura`.
  5. Para cada item del `carrito`:
     ```sql
     INSERT INTO detalles_de_facturas(id_factura, id_producto, cantidad,
                                       precio_unitario, descuento, subtotal)
     VALUES(?, ?, ?, ?, ?, ?)
     ```
  6. Para cada item del `carrito`:
     ```sql
     UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?
     ```
     - Si `affectedRows == 0`: `rollback()`, muestra `"Stock insuficiente para el producto ID: N"`, retorna `false`.
  7. `commit()`.
  8. `JOptionPane("Compra finalizada exitosamente. Factura N: " + numeroFactura)`.
  9. Retorna `true`.
- **Lanza:** `RuntimeException` si ocurre error SQL (con `rollback()` previo).

---

### `ControladorClienteABM`

`aplicacion.controladores.ControladorClienteABM`

CRUD de clientes con validacion de mail.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

#### `ControladorClienteABM()`
- **Descripcion:** Constructor vacio.

#### `ControladorClienteABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de ABM de clientes.
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.

#### `List<Cliente> obtenerClientesPorHabilitado(int habilitado)`
- **Descripcion:** Obtiene clientes filtrados por estado.
- **Parametros:**
  - `habilitado` — 1 activos, 0 deshabilitados.
- **Retorna:** `List<Cliente>` — lista de clientes (nunca `null`).
- **SQL:**
  ```sql
  SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado
  FROM clientes WHERE habilitado = ?
  ```

#### `Cliente buscarCliente(String dni, int habilitado)`
- **Descripcion:** Busca un cliente por DNI.
- **Parametros:**
  - `dni` — Documento a buscar.
  - `habilitado` — Filtro de estado.
- **Retorna:** `Cliente` si existe, `null` si no.
- **Comportamiento en no encontrado:** `JOptionPane("No existe el cliente con ese dni")`.
- **SQL:**
  ```sql
  SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado
  FROM clientes WHERE dni = ? AND habilitado = ?
  ```

#### `int agregarCliente(String nombre, String apellido, String dni, String telefono, String direccion, String mail)`
- **Descripcion:** Inserta un nuevo cliente.
- **Parametros:**
  - `nombre`, `apellido` — Nombre y apellido.
  - `dni` — Documento.
  - `telefono` — Telefono (puede ser vacio).
  - `direccion` — Direccion (puede ser vacia).
  - `mail` — Correo electronico.
- **Retorna:** `int` — ID generado (> 0) si exito, `-1` si falla validacion.
- **Validaciones (orden):**
  1. `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail})`: si `false` → retorna `-1`.
  2. `ValidadorMail.esValido()`: si invalido → `JOptionPane("El formato del mail no es valido")`, retorna `-1`.
  3. `SELECT mail FROM clientes WHERE mail = ?`: si existe → `JOptionPane("Este mail ya ha sido registrado")`, retorna `-1`.
- **SQL:**
  ```sql
  INSERT INTO clientes(nombre, apellido, dni, telefono, direccion, mail)
  VALUES(?, ?, ?, ?, ?, ?)
  ```
  Con `RETURN_GENERATED_KEYS`.
- **Post-insercion:** `JOptionPane("Cliente agregado exitosamente")`.

#### `boolean modificarCliente(int id, String nombre, String apellido, String dni, String telefono, String direccion, String mail)`
- **Descripcion:** Actualiza un cliente existente.
- **Parametros:**
  - `id` — Identificador del cliente.
  - `nombre`, `apellido`, `dni`, `telefono`, `direccion`, `mail` — Nuevos valores.
- **Retorna:** `boolean` — `true` si exito, `false` si falla validacion.
- **Validaciones (orden):**
  1. `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail})`: si `false` → retorna `false`.
  2. `ValidadorMail.esValido()`: si invalido → retorna `false`.
  3. `SELECT mail FROM clientes WHERE mail = ? AND id != ?`: si existe otro → `JOptionPane("Ese mail ya pertenece a otro cliente")`, retorna `false`.
- **SQL:**
  ```sql
  UPDATE clientes SET nombre=?, apellido=?, dni=?, telefono=?, direccion=?, mail=?
  WHERE id=?
  ```

#### `boolean alternarHabilitadoCliente(int id)`
- **Descripcion:** Invierte el estado habilitado de un cliente.
- **Parametros:**
  - `id` — Identificador del cliente.
- **Retorna:** `boolean` — `true` siempre.
- **SQL:**
  ```sql
  UPDATE clientes SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END
  WHERE id = ?
  ```

#### `Cliente mapearCliente(ResultSet rs)` (privado)
- **Descripcion:** Construye un `Cliente` desde la fila actual del `ResultSet`.
- **Parametros:**
  - `rs` — ResultSet posicionado en una fila.
- **Retorna:** `Cliente` con datos de la fila.

---

### `ControladorProveedorABM`

`aplicacion.controladores.ControladorProveedorABM`

CRUD de proveedores con validacion de mail.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

Estructura identica a `ControladorClienteABM` pero sobre la tabla `proveedores`.

#### `ControladorProveedorABM()`
- **Descripcion:** Constructor vacio.

#### `ControladorProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de ABM de proveedores.

#### `List<Proveedor> obtenerProveedoresPorHabilitado(int habilitado)`
- **Descripcion:** Obtiene proveedores filtrados por estado.
- **SQL:**
  ```sql
  SELECT id, nombre, telefono, direccion, mail, habilitado
  FROM proveedores WHERE habilitado = ?
  ```

#### `Proveedor buscarProveedor(int id, int habilitado)`
- **Descripcion:** Busca un proveedor por ID.
- **Parametros:**
  - `id` — ID del proveedor.
  - `habilitado` — Filtro de estado.
- **Retorna:** `Proveedor` o `null`.

#### `int agregarProveedor(String nombre, String telefono, String direccion, String mail)`
- **Descripcion:** Inserta un nuevo proveedor. Valida campos requeridos, mail y unicidad.
- **Retorna:** `int` — ID generado o `-1` si falla validacion.
- **Validaciones (orden):**
  1. `ValidadorCampos.validarRequeridos({nombre, telefono, direccion, mail})`: si `false` → retorna `-1`.
  2. `ValidadorMail.esValido(mail)`: si invalido → retorna `-1`.

#### `boolean modificarProveedor(int id, String nombre, String telefono, String direccion, String mail)`
- **Descripcion:** Actualiza un proveedor. Valida campos requeridos, mail y unicidad excluyendo propio ID.
- **Validaciones (orden):**
  1. `ValidadorCampos.validarRequeridos({nombre, telefono, direccion, mail})`: si `false` → retorna `false`.
  2. `ValidadorMail.esValido(mail)`: si invalido → retorna `false`.

#### `boolean alternarHabilitadoProveedor(int id)`
- **Descripcion:** Invierte el estado habilitado.

#### `Proveedor mapearProveedor(ResultSet rs)` (privado)
- **Descripcion:** Construye un `Proveedor` desde la fila actual del `ResultSet`.

---

### `ControladorUsuarioABM`

`aplicacion.controladores.ControladorUsuarioABM`

CRUD de usuarios con validacion de mail, rol y password.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

#### `ControladorUsuarioABM()`
- **Descripcion:** Constructor vacio.

#### `ControladorUsuarioABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de ABM de usuarios.

#### `List<Usuario> obtenerUsuariosPorHabilitado(int habilitado)`
- **Descripcion:** Obtiene usuarios filtrados por estado.
- **SQL:**
  ```sql
  SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado
  FROM usuarios WHERE habilitado = ?
  ```

#### `Usuario buscarUsuario(String dni, int habilitado)`
- **Descripcion:** Busca un usuario por DNI.
- **Parametros:**
  - `dni` — Documento a buscar.
  - `habilitado` — Filtro de estado.

#### `int agregarUsuario(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password, String rol)`
- **Descripcion:** Inserta un nuevo usuario. Incluye password y rol.
- **Validaciones:**
  1. `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail, password})`: si `false` → retorna `-1`.
  2. Mail formato, mail unico.
- **SQL:**
  ```sql
  INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password)
  VALUES(?, ?, ?, ?, ?, ?, ?, ?)
  ```

#### `boolean modificarUsuario(int id, String nombre, String apellido, String dni, String telefono, String direccion, String mail, String rol, String password)`
- **Descripcion:** Actualiza un usuario existente. Incluye password y rol.
- **Validaciones:**
  1. `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail})`: si `false` → retorna `false`.
  2. Mail formato, mail unico excluyendo propio ID.

#### `boolean alternarHabilitadoUsuario(int id)`
- **Descripcion:** Invierte el estado habilitado. Si el usuario es administrador
  y esta habilitado, verifica que no sea el unico admin habilitado antes de
  deshabilitarlo.
- **Algoritmo:**
  1. `SELECT rol, habilitado FROM usuarios WHERE id = ?`
  2. Si es `"Administrador"` y `habilitado = 1`:
     - `ValidadorCantidadAdmin.permitirDeshabilitar(id)`: si retorna `false`,
       corta sin hacer UPDATE.
  3. `UPDATE usuarios SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?`
  4. `JOptionPane("Estado cambiado exitosamente")`.

#### `Usuario mapearUsuario(ResultSet rs)` (privado)
- **Descripcion:** Construye un `Usuario` desde la fila actual del `ResultSet`.

---

### `ControladorDepositoABM`

`aplicacion.controladores.ControladorDepositoABM`

CRUD de productos (gestion de deposito/stock).

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

#### `ControladorDepositoABM()`
- **Descripcion:** Constructor vacio.

#### `ControladorDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Crea y muestra la vista de ABM de productos.

#### `List<Producto> obtenerProductosPorHabilitado(int habilitado)`
- **Descripcion:** Obtiene productos filtrados por estado, incluyendo nombre del proveedor.
- **SQL:**
  ```sql
  SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor,
         pr.nombre AS nombreProveedor, p.habilitado
  FROM productos p
  LEFT JOIN proveedores pr ON p.id_proveedor = pr.id
  WHERE p.habilitado = ?
  ```

#### `List<Producto> buscarProductos(String texto, int habilitado)`
- **Descripcion:** Busca productos por ID (si el texto es numerico) o por descripcion (LIKE).
- **Parametros:**
  - `texto` — ID numerico o texto de descripcion.
  - `habilitado` — Filtro de estado.
- **Retorna:** `List<Producto>` — productos encontrados (puede estar vacia).
- **Algoritmo:**
  1. Si `texto` es numerico (`Integer.parseInt` exitoso): busca por `p.id = ?`.
  2. Si no: busca por `p.descripcion LIKE ?` (con `%texto%`).
  3. Si lista vacia: muestra `JOptionPane("No se encontraron productos")`.

#### `Producto seleccionarProducto(String texto, int habilitado)`
- **Descripcion:** Busca productos y si hay multiples, permite seleccionar uno.
- **Parametros:**
  - `texto` — Criterio de busqueda.
  - `habilitado` — Filtro de estado.
- **Retorna:** `Producto` si se selecciona uno, o `null` si no hay resultados.
- **Algoritmo:**
  1. Llama a `buscarProductos(texto, habilitado)`.
  2. Si 0 resultados: retorna `null`.
  3. Si 1 resultado: retorna el unico producto.
  4. Si multiples: muestra `JOptionPane.showInputDialog` con un array de descripciones para seleccionar. Retorna el producto seleccionado o `null` si cancela.

#### `int agregarProducto(String descripcion, int precio, int stock, int idProveedor)`
- **Descripcion:** Inserta un producto. Proveedor obligatorio.
- **Retorna:** `int` — ID generado.
- **Validaciones:**
  1. `ValidadorCampos.validarRequeridos({descripcion, proveedor})`: si `false` → retorna `-1`.
- **SQL:**
  ```sql
  INSERT INTO productos(descripcion, precio, stock, id_proveedor) VALUES(?, ?, ?, ?)
  ```
  Con `RETURN_GENERATED_KEYS`.

#### `boolean modificarProducto(int id, String descripcion, int precio, int stock, int idProveedor)`
- **Descripcion:** Actualiza un producto. Proveedor obligatorio.
- **Validaciones:**
  1. `ValidadorCampos.validarRequeridos({descripcion, proveedor})`: si `false` → retorna `false`.
- **SQL:**
  ```sql
  UPDATE productos SET descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id=?
  ```
- **Parametros:**
  - `idProveedor` — Si es `<= 0`, setea `id_proveedor = NULL`.
- **SQL:**
  ```sql
  UPDATE productos SET descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id=?
  -- o con NULL:
  UPDATE productos SET descripcion=?, precio=?, stock=?, id_proveedor=NULL WHERE id=?
  ```

#### `boolean alternarHabilitadoProducto(int id)`
- **Descripcion:** Invierte el estado habilitado.

#### `Producto mapearProducto(ResultSet rs)` (privado)
- **Descripcion:** Construye un `Producto` desde la fila actual, incluyendo `idProveedor` y `nombreProveedor`.

---

### `ControladorFactura`

`aplicacion.controladores.ControladorFactura`

Consulta de facturas y detalles.

**Campos:**
- `private Conexion c = new Conexion()` — Conexion a BD.

#### `ControladorFactura()`
- **Descripcion:** Constructor vacio.

#### `List<Factura> obtenerFacturasPorCliente(int idCliente)`
- **Descripcion:** Obtiene las facturas de un cliente, ordenadas por fecha descendente.
- **Parametros:**
  - `idCliente` — FK del cliente.
- **Retorna:** `List<Factura>` — facturas del cliente (parciales, solo datos basicos).
- **SQL:**
  ```sql
  SELECT id, numero_factura, fecha_emision, total_compra,
         nombre_vendedor, apellido_vendedor
  FROM facturas WHERE id_cliente = ?
  ORDER BY fecha_emision DESC, id DESC
  ```

#### `Factura obtenerFacturaCompleta(int idFactura)`
- **Descripcion:** Obtiene una factura con todos sus datos incluyendo DNI y direccion del cliente.
- **Parametros:**
  - `idFactura` — ID de la factura.
- **Retorna:** `Factura` completa o `null` si no existe.
- **SQL:**
  ```sql
  SELECT f.id, f.id_cliente, f.id_vendedor, f.numero_factura, f.fecha_emision,
         f.nombre_cliente, f.apellido_cliente, f.nombre_vendedor, f.apellido_vendedor,
         f.subtotal, f.descuento_porcentaje, f.valor_descontado, f.total_compra,
         c.dni, c.direccion
  FROM facturas f
  JOIN clientes c ON f.id_cliente = c.id
  WHERE f.id = ?
  ```

#### `List<DetalleFactura> obtenerDetallesPorFactura(int idFactura)`
- **Descripcion:** Obtiene los detalles (lineas) de una factura.
- **Parametros:**
  - `idFactura` — ID de la factura.
- **Retorna:** `List<DetalleFactura>` — lineas de detalle.
- **SQL:**
  ```sql
  SELECT p.id, df.cantidad, df.precio_unitario, df.descuento, df.subtotal, p.descripcion
  FROM detalles_de_facturas df
  JOIN productos p ON df.id_producto = p.id
  WHERE df.id_factura = ?
  ```

---

## 5. Paquete `aplicacion.vistas`

Interfaces graficas Swing. Cada vista expone un `JPanel` publico y
contiene los componentes Swing como campos privados. Los listeners
de botones se definen en el constructor.

---

### `VentanaPrincipal`

`aplicacion.vistas.VentanaPrincipal`

`extends JFrame`

Ventana principal del sistema. Contiene y alterna los paneles de cada
vista.

#### `VentanaPrincipal()`
- **Descripcion:** Configura la ventana principal.
- **Algoritmo:**
  1. `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`.
  2. `setSize(800, 600)`.

#### `void mostrarVista(JPanel vista)`
- **Descripcion:** Reemplaza el contenido de la ventana con un nuevo panel.
- **Parametros:**
  - `vista` — Panel a mostrar.
- **Algoritmo:**
  1. `getContentPane().removeAll()`.
  2. `getContentPane().add(vista)`.
  3. `revalidate()`, `repaint()`.
  4. `setVisible(true)`.

---

### `VistaLogin`

`aplicacion.vistas.VistaLogin`

Panel de inicio de sesion.

**Componentes publicos:**
- `JPanel panelLogin` — Contenedor principal.

**Componentes privados:**
- `JTextField tfMail` — Ingreso de email.
- `JPasswordField tfPassword` — Ingreso de contrasena.
- `JButton btnIngresar` — Boton de inicio de sesion.
- `JButton btnCrearCuenta` — Boton de registro.

#### `VistaLogin(VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Construye la interfaz y configura los listeners.
- **Parametros:**
  - `ventanaPrincipal` — JFrame contenedor para navegacion.
- **Listener `btnIngresar`:**
  1. Obtiene `tfMail.getText()` y `tfPassword.getPassword()` (convertido a String).
  2. Si alguno vacio: `JOptionPane("Ingrese ambos datos")`, retorna.
  3. Crea `ControladorLogin ctrl = new ControladorLogin()`.
  4. Llama `ctrl.validar(mail, password)`:
     - `true`: `ctrl.iniciarSesion(ventanaPrincipal)`.
     - `false`: `JOptionPane("Credenciales incorrectas")`.
- **Listener `btnCrearCuenta`:**
  1. `new ControladorRegistro(ventanaPrincipal)`.

---

### `VistaRegistro`

`aplicacion.vistas.VistaRegistro`

Formulario de registro de nuevo usuario.

**Componentes publicos:**
- `JPanel panelRegistro` — Contenedor principal.

**Componentes privados:**
- `JTextField tfNombre`, `tfApellido`, `tfDni`, `tfTelefono`, `tfDireccion`, `tfMail`.
- `JPasswordField tfPassword`.
- `JButton btnCrearUsuario`, `btnVolver`.

**Filtros aplicados en constructor:**
- `tfDni`, `tfTelefono` → `FiltroNumerico`.
- `tfNombre`, `tfApellido` → `FiltroTexto`.
- `tfDireccion` → `FiltroAlfanumerico`.

#### `VistaRegistro(VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Construye el formulario y configura los listeners.
- **Parametros:**
  - `ventanaPrincipal` — JFrame contenedor.
- **Listener `btnCrearUsuario`:**
  1. Obtiene todos los campos.
  2. Valida requeridos con `ValidadorCampos.validarRequeridos({nombre, apellido, dni, mail, password})`.
     Si faltan → `JOptionPane` con lista de campos, corta.
  3. `new ControladorRegistro().registrar(nombre, apellido, dni, telefono, direccion, mail, password)`.
  4. Si `true`: `JOptionPane("Inicie sesion")`, vuelve al login via `new ControladorLogin(ventanaPrincipal)`.
- **Listener `btnVolver`:**
  1. `new ControladorLogin(ventanaPrincipal)`.

---

### `VistaAdmin`

`aplicacion.vistas.VistaAdmin`

Panel principal del Administrador.

**Componentes publicos:**
- `JPanel panelAdmin` — Contenedor principal.

**Componentes privados:**
- `JButton btnCerrarSesion`, `btnFacturar`, `btnAdministrarUsuarios`, `btnAdministrarClientes`, `btnAdministrarDeposito`, `btnAdministrarProveedores`.

#### `VistaAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Construye el panel y configura los listeners.
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
- **Listeners (cada boton):**
  - `btnAdministrarUsuarios` → `new ControladorUsuarioABM(usuario, ventanaPrincipal)`.
  - `btnAdministrarClientes` → `new ControladorClienteABM(usuario, ventanaPrincipal)`.
  - `btnFacturar` → `new ControladorCajero(usuario, ventanaPrincipal)`.
  - `btnAdministrarDeposito` → `new ControladorDepositoABM(usuario, ventanaPrincipal)`.
  - `btnAdministrarProveedores` → `new ControladorProveedorABM(usuario, ventanaPrincipal)`.
  - `btnCerrarSesion` → `new ControladorLogin(ventanaPrincipal)`.

---

### `VistaClienteABM`

`aplicacion.vistas.VistaClienteABM`

ABM de clientes con tabla, busqueda y formulario dinamico.

**Componentes publicos:**
- `JPanel panelClienteABM` — Contenedor principal.

**Componentes privados:**
- `JTextField tfDni` — Busqueda por DNI (FiltroNumerico).
- `JComboBox cbxFiltroHabilitado` — "Habilitados" / "Deshabilitados".
- `JButton btnBuscar`, `btnAgregar`, `btnModificar`, `btnDeshabilitar`, `btnVolver`, `btnMostrarFacturas`.
- `JTable tblClientes` — Modelo `mdlClientes`, columnas `colsClientes`.

#### `VistaClienteABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Construye la interfaz, carga datos iniciales y configura listeners.
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
- **Algoritmo de inicializacion:**
  1. `configurarTabla()`: asigna columnas al modelo de tabla, oculta columna "Habilitado".
  2. Pobla `cbxFiltroHabilitado` con "Habilitados" / "Deshabilitados".
  3. Carga `ControladorClienteABM().obtenerClientesPorHabilitado(1)` en la tabla.
- **Listener `cbxFiltroHabilitado`:**
  1. Segun seleccion: llama a `obtenerClientesPorHabilitado(1)` o `obtenerClientesPorHabilitado(0)`.
  2. Repobla tabla.
- **Listener `btnBuscar`:**
  1. Obtiene `tfDni.getText()`.
  2. Si vacio: recarga segun filtro.
  3. Si no: `ControladorClienteABM().buscarCliente(dni, habilitado)`.
- **Listener `btnAgregar`:**
  1. `VistaFormulario.mostrarDialogo("Nuevo Cliente", campos)` con: Nombre, Apellido, DNI, Telefono, Direccion, Mail.
  2. Si `Map` resultado != null: `ControladorClienteABM().agregarCliente(...)`.
  3. Si retorno > -1: repobla tabla.
- **Listener `btnModificar`:**
  1. Valida fila seleccionada. Si no: `JOptionPane("Seleccione un cliente de la tabla")`.
  2. Abre dialogo con valores actuales.
  3. `ControladorClienteABM().modificarCliente(...)`.
- **Listener `btnDeshabilitar`:**
  1. Valida fila seleccionada.
  2. Confirma con `JOptionPane.showConfirmDialog("Esta seguro de deshabilitar/habilitar...")`.
  3. `ControladorClienteABM().alternarHabilitadoCliente(id)`.
  4. Repobla tabla.
- **Listener `btnMostrarFacturas`:**
  1. Valida fila seleccionada.
  2. `new VistaFactura(usuario, ventanaPrincipal, idCliente, nombre, apellido)`.
- **Listener `btnVolver`:**
  1. `new ControladorAdmin(usuario, ventanaPrincipal)` o segun rol.

#### `void configurarTabla()`
- **Descripcion:** Define las columnas del modelo de tabla.

#### `void poblarTabla(List<Cliente> clientes)`
- **Descripcion:** Limpia la tabla y la llena con la lista de clientes.

---

### `VistaProveedorABM`

`aplicacion.vistas.VistaProveedorABM`

ABM de proveedores. Estructura identica a `VistaClienteABM`.

**Componentes publicos:**
- `JPanel panelProveedorABM`.

**Componentes privados:**
- `JTextField tfIdProveedor` — Busqueda por ID (FiltroNumerico).
- `JComboBox cbxFiltroHabilitado`.
- `JButton btnBuscar`, `btnAgregar`, `btnModificar`, `btnDeshabilitar`, `btnVolver`.
- `JTable tblProveedores`.

#### `VistaProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Similar a `VistaClienteABM` pero para proveedores.
- **Listener `btnAgregar`:** Formulario con Nombre, Telefono, Direccion, Mail.
- **Listener `btnBuscar`:** Busca por ID numerico.

#### `void configurarTabla()`, `void poblarTabla(List<Proveedor>)`
- Misma estructura que `VistaClienteABM`.

---

### `VistaUsuarioABM`

`aplicacion.vistas.VistaUsuarioABM`

ABM de usuarios, incluye rol y contrasena.

**Componentes publicos:**
- `JPanel panelUsuarioABM`.

**Componentes privados:**
- `JTextField tfDni`.
- `JComboBox cbxFiltroHabilitado`.
- `JButton btnBuscar`, `btnAgregar`, `btnModificar`, `btnDeshabilitar`, `btnVolver`.
- `JTable tblUsuarios` — Columnas: ID, Nombre, Apellido, Dni, Telefono, Direccion, Mail, Rol, Password, Habilitado (oculta).

#### `VistaUsuarioABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Listener `btnAgregar`:**
  1. `VistaFormulario.mostrarDialogo("Nuevo Usuario", Campo("Nombre"), Campo("Apellido"), Campo("DNI"), Campo("Telefono"), Campo("Direccion"), Campo("Mail"), Campo("Contrasena", true), Campo("Rol", new String[]{"Administrador", "Cajero", "Deposito"}))`.
- **Listener `btnModificar`:**
  1. Abre dialogo con valores actuales, incluyendo password (JPasswordField) y rol (JComboBox).

#### `void configurarTabla()`, `void poblarTabla(List<Usuario>)`

---

### `VistaDepositoABM`

`aplicacion.vistas.VistaDepositoABM`

ABM de productos.

**Componentes publicos:**
- `JPanel panelDepositoABM`.

**Componentes privados:**
- `JTextField tfIdProducto` — Busqueda por ID (FiltroNumerico).
- `JComboBox cbxFiltroHabilitado`.
- `JButton btnBuscar`, `btnAgregar`, `btnModificar`, `btnDeshabilitar`, `btnVolver`.
- `JTable tblProductos`.
- `JTextField tfProveedor` — Filtro texto para busqueda por proveedor.

#### `VistaDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Listener `btnAgregar`:**
  1. Carga proveedores: `ControladorProveedorABM().obtenerProveedoresPorHabilitado(1)`.
  2. Crea arreglo de `Campo` con: Descripcion (requerido), Precio, Stock, Proveedor (JComboBox, requerido).
  3. Si dialogo ok: `ControladorDepositoABM().agregarProducto(descripcion, precio, stock, idProveedor)`.
- **Listener `btnModificar`:** Similar con valores actuales.
- **Listener `btnBuscar`:** Busca por texto (ID o descripcion).

#### `void configurarTabla()`, `void poblarTabla(List<Producto>)`

---

### `VistaFormulario`

`aplicacion.vistas.VistaFormulario`

Dialogo generico para formularios de entrada de datos. Utiliza
`JOptionPane.showConfirmDialog` con un panel construido dinamicamente.

**Clase interna:** `Campo`

**Campos:**
- `String etiqueta` — Texto del label.
- `boolean esPassword` — Si es campo de contrasena.
- `String valorInicial` — Valor por defecto.
- `String[] opciones` — Para JComboBox.
- `boolean requerido` — Si el campo es obligatorio (muestra asterisco rojo).

#### `Campo(String etiqueta)`
- **Descripcion:** Crea un campo de texto simple, no requerido.
- **Parametros:**
  - `etiqueta` — Texto del label.

#### `Campo(String etiqueta, boolean esPassword)`
- **Descripcion:** Crea un campo de contrasena, no requerido.
- **Nota:** `esPassword = true` indica que es password. No es campo requerido.

#### `Campo(String etiqueta, boolean esPassword, boolean requerido)`
- **Descripcion:** Crea un campo con configuracion explicita de password y requerido.
- **Parametros:**
  - `etiqueta` — Texto del label.
  - `esPassword` — `true` para JPasswordField.
  - `requerido` — `true` si es obligatorio (etiqueta con asterisco rojo).

#### `Campo(String etiqueta, String valorInicial)`
- **Descripcion:** Campo de texto con valor por defecto.

#### `Campo(String etiqueta, boolean esPassword, String valorInicial)`
- **Descripcion:** Campo de contrasena con valor por defecto.

#### `Campo(String etiqueta, String[] opciones)`
- **Descripcion:** Crea un JComboBox con las opciones dadas.
- **Parametros:**
  - `opciones` — Array de strings para el combo.

#### `Campo(String etiqueta, String[] opciones, String valorInicial)`
- **Descripcion:** JComboBox con valor pre-seleccionado.

#### `static Map<String, String> mostrarDialogo(String titulo, Campo... campos)`
- **Descripcion:** Muestra un dialogo modal con los campos especificados y retorna un mapa de valores.
  Si hay campos requeridos, valida antes de retornar.
- **Parametros:**
  - `titulo` — Titulo del dialogo.
  - `campos` — Lista de campos a mostrar.
- **Retorna:** `Map<String, String>` — mapa etiqueta → valor, o `null` si el usuario cancela.
- **Algoritmo:**
  1. Crea `JPanel` con `GridBagLayout`.
  2. Por cada `Campo`:
     - Si `requerido`: etiqueta con formato `"<html>Campo *<font color=red>*</font></html>"`.
     - Si no: `JLabel(campo.etiqueta)`.
     - Si `opciones != null`: crea `JComboBox`.
     - Si `esPassword`: crea `JPasswordField(15)`.
     - Si no: crea `JTextField(15)` con valor inicial.
     - Aplica `DocumentFilter` segun la etiqueta:
       - DNI, Telefono, Precio, Stock, Cantidad, "Descuento %" → `FiltroNumerico`.
       - Nombre, Apellido → `FiltroTexto`.
       - Direccion, Descripcion → `FiltroAlfanumerico`.
       - Mail → sin filtro.
  3. Si hay campos password: agrega checkbox "Mostrar contrasena" que alterna `echoChar`.
  4. `JOptionPane.showConfirmDialog(null, panel, titulo, OK_CANCEL_OPTION)`.
  5. Si OK:
     - Itera campos, construye pares `{etiqueta, valor}`.
     - Si algun campo requerido esta vacio → `JOptionPane("Complete todos los campos obligatorios")`, retorna `null`.
     - Si todo ok: retorna `LinkedHashMap`.
  6. Si cancel: retorna `null`.

---

### `VistaCajero`

`aplicacion.vistas.VistaCajero`

Interfaz de caja para realizar ventas (POS).

**Componentes publicos:**
- `JPanel panelCajero`.

**Componentes privados (cliente):**
- `JTextField tfBuscarCliente` (FiltroNumerico).
- `JTextField tfIdCliente`, `tfNombreCliente`, `tfApellidoCliente`, `tfDniCliente`, `tfTelefonoCliente`, `tfDireccionCliente`, `tfMailCliente`.
- `JButton btnBuscarCliente`, `btnModificarCliente`, `btnNuevoCliente`.

**Componentes privados (producto):**
- `JTextField tfBuscarProducto`.
- `JTextField tfIdProducto`, `tfDescripcionProducto`, `tfPrecioProducto` (FiltroNumerico), `tfStockProducto` (FiltroNumerico), `tfProveedorProducto` (read-only).
- `JTextField tfCantidadProducto` (FiltroNumerico), `tfDescuentoProducto` (FiltroNumerico).
- `JButton btnBuscarProducto`, `btnNuevoProducto`, `btnModificarProducto`, `btnAgregarAlCarro`.

**Componentes privados (carrito):**
- `JTable tblCarrito` — Modelo `mdlCarrito`, columnas: ID, Descripcion, Precio Unit., Cantidad, Descuento, Subtotal.
- `JButton btnModificarArticulo`, `btnEliminarArticulo`.
- `JButton btnCalcularTotal`, `btnFinalizarCompra`, `btnCancelarCompra`.
- `JTextField tfSubtotal` (read-only), `tfDescuento` (FiltroNumerico), `tfTotal` (read-only), `tfValorDescontado` (read-only).

**Componentes privados (vendedor):**
- `JTextField tfNombreCajero`, `tfApellidoCajero`, `tfIdCajero` — datos del vendedor (read-only).

#### `VistaCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal)`
- **Descripcion:** Construye la interfaz de caja completa con todos los listeners.
- **Parametros:**
  - `usuario` — Vendedor autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
- **Inicializacion:**
  1. `tfIdCajero.setText(usuario.getId())`, `tfNombreCajero.setText(usuario.getNombre())`, etc.
  2. `configurarTablaCarrito()` — inicia `mdlCarrito` con columnas.
- **Listener `btnBuscarCliente`:**
  1. Obtiene texto de `tfBuscarCliente`.
  2. `ControladorClienteABM().buscarCliente(dni, 1)`.
  3. Si encontrado: llena campos de cliente.
  4. Si no: `limpiarCamposCliente()`.
- **Listener `btnModificarCliente`:**
  1. Valida que haya un cliente cargado.
  2. `VistaFormulario.mostrarDialogo("Modificar Cliente", ...)` con valores actuales.
  3. `ControladorClienteABM().modificarCliente(...)`.
  4. Si ok: recarga datos del cliente.
- **Listener `btnNuevoCliente`:**
  1. `VistaFormulario.mostrarDialogo("Nuevo Cliente", ...)`.
  2. `ControladorClienteABM().agregarCliente(...)`.
  3. Si ok: recarga datos.
- **Listener `btnBuscarProducto`:**
  1. `ControladorDepositoABM().seleccionarProducto(texto, 1)`.
  2. Si encontrado: llena campos de producto incluyendo `tfProveedorProducto.setText(proveedor.getNombre())`.
- **Listener `btnNuevoProducto`:**
  1. Carga proveedores: `ControladorProveedorABM().obtenerProveedoresPorHabilitado(1)`.
  2. `VistaFormulario.mostrarDialogo("Nuevo Producto", Campo("Descripcion", false, true), Campo("Precio"), Campo("Stock"), Campo("Proveedor", proveedores))`.
  3. Si dialogo ok: `ControladorDepositoABM().agregarProducto(descripcion, precio, stock, idProveedor)`.
- **Listener `btnModificarProducto`:**
  1. Similar a btnNuevoProducto pero con modificacion: `ControladorDepositoABM().modificarProducto(id, descripcion, precio, stock, idProveedor)`.
- **Listener `btnAgregarAlCarro`:**
  1. Valida cantidad > 0.
  2. Si no: `JOptionPane("La cantidad debe ser mayor a 0")`.
  3. Calcula subtotal del item: `precio * cantidad * (100 - descuento) / 100`.
  4. Agrega fila al `mdlCarrito`.
  5. `limpiarCamposProducto()`.
- **Listener `btnModificarArticulo`:**
  1. Valida fila seleccionada.
  2. Muestra dialogo para nueva cantidad y descuento.
  3. Actualiza fila en el modelo.
- **Listener `btnEliminarArticulo`:**
  1. Valida fila seleccionada.
  2. Confirma eliminacion.
  3. Elimina fila del `mdlCarrito`.
- **Listener `btnCalcularTotal`:**
  1. Suma columna "Subtotal" de todas las filas.
  2. Si descuento fuera de rango (0-100): `JOptionPane("El descuento debe ser entre 0 y 100")`.
  3. Calcula: `tfSubtotal`, `tfValorDescontado`, `tfTotal`.
- **Listener `btnFinalizarCompra`:**
  1. Valida cliente seleccionado: `JOptionPane("Debe seleccionar un cliente")`.
  2. Valida carrito no vacio: `JOptionPane("El carrito esta vacio")`.
  3. Valida total calculado: `JOptionPane("Debe calcular el total primero")`.
  4. Confirma con usuario.
  5. Construye `List<Object[]>` con datos del carrito.
  6. `ControladorCajero().finalizarCompra(...)`.
  7. Si ok: `limpiarCarrito()`.
- **Listener `btnCancelarCompra`:**
  1. Confirma cancelacion.
  2. `limpiarCarrito()`.
- **Listener `btnVolver`:**
  1. Segun rol: vuelve a admin o login.

#### `void limpiarCamposCliente()` (privado)
- **Descripcion:** Limpia todos los campos de datos del cliente.

#### `void configurarTablaCarrito()` (privado)
- **Descripcion:** Inicializa `mdlCarrito` con los identificadores de columna.

#### `void limpiarCamposProducto()` (privado)
- **Descripcion:** Limpia campos de producto (incluyendo `tfProveedorProducto`), cantidad y descuento.

#### `void actualizarTotales()` (privado)
- **Descripcion:** Suma columna 5 (subtotal) del carrito en `tfSubtotal`, resetea descuento/total/descontado.

#### `void limpiarCarrito()` (privado)
- **Descripcion:** Limpia todas las filas del carrito, campos de cliente y producto, totales.

---

### `VistaFactura`

`aplicacion.vistas.VistaFactura`

Listado de facturas de un cliente.

**Componentes publicos:**
- `JPanel panelFactura`.

**Componentes privados:**
- `JTable tblFacturas` — Modelo `mdlFacturas`, columnas: N, Fecha, Total ($), Vendedor.
- `JButton btnVerDetalle`, `btnVolver`.
- `List<Factura> facturas` — Lista de facturas cargadas.

#### `VistaFactura(Usuario usuario, VentanaPrincipal ventanaPrincipal, int idCliente, String nombreCliente, String apellidoCliente)`
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
  - `idCliente` — ID del cliente.
  - `nombreCliente`, `apellidoCliente` — Nombre del cliente (para titulo).
- **Inicializacion:**
  1. `cargarFacturas()`.
- **Listener `btnVerDetalle`:**
  1. Valida fila seleccionada.
  2. Obtiene factura seleccionada de `facturas` list.
  3. `new VistaDetallesFactura(usuario, ventanaPrincipal, idFactura, idCliente, nombreCliente, apellidoCliente)`.
- **Listener `btnVolver`:**
  1. Vuelve a `ControladorClienteABM` o segun contexto.

#### `void cargarFacturas()` (privado)
- **Descripcion:** Carga las facturas del cliente via `ControladorFactura().obtenerFacturasPorCliente(idCliente)` y las muestra en la tabla.

---

### `VistaDetallesFactura`

`aplicacion.vistas.VistaDetallesFactura`

Detalle completo de una factura.

**Componentes publicos:**
- `JPanel panelDetallesFactura`.

**Componentes privados (read-only):**
- `JTextField tfNumeroFactura`, `tfFechaFactura`.
- `JTextField tfNombreCliente`, `tfApellidoCliente`, `tfDniCliente`, `tfDireccionCliente`, `tfIdCliente`.
- `JTextField tfNombreVendedor`, `tfApellidoVendedor`, `tfIdVendedor`.
- `JTextField tfSubtotal`, `tfPorcentajeDescuento`, `tfValorDescontado`, `tfTotal`.
- `JTable tblDetalles` — Modelo `mdlDetalles`, columnas: ID, Producto, Cantidad, P/U, Descuento %, Subtotal.
- `JButton btnVolver`.

#### `VistaDetallesFactura(Usuario usuario, VentanaPrincipal ventanaPrincipal, int idFactura, int idCliente, String nombreCliente, String apellidoCliente)`
- **Parametros:**
  - `usuario` — Usuario autenticado.
  - `ventanaPrincipal` — JFrame contenedor.
  - `idFactura` — ID de la factura a mostrar.
  - `idCliente` — ID del cliente.
  - `nombreCliente`, `apellidoCliente` — Nombre del cliente.
- **Inicializacion:**
  1. `cargarFactura()`.
  2. `cargarDetalles()`.

#### `void cargarFactura()` (privado)
- **Descripcion:** Carga los datos de la factura via `ControladorFactura().obtenerFacturaCompleta(idFactura)` y llena todos los campos de texto.

#### `void cargarDetalles()` (privado)
- **Descripcion:** Carga los detalles via `ControladorFactura().obtenerDetallesPorFactura(idFactura)` y puebla la tabla.

---

## 6. `Main.java` (default package)

Punto de entrada del sistema.

### `void main()` (implicito)
- **Descripcion:** Metodo de entrada principal.
- **Algoritmo:**
  1. Crea `VentanaPrincipal ventana = new VentanaPrincipal()`.
  2. `SwingUtilities.invokeLater(() -> { new ControladorLogin(ventana); })` — asegura que la creacion de la GUI ocurra en el Event Dispatch Thread.
- **Nota:** No es `public static void main(String[])`, pero es el metodo ejecutado por IntelliJ al presionar F5 en el proyecto configurado.
