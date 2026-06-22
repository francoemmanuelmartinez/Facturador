# Diagramas del sistema (ASCII)

Diagramas de flujo y arquitectura en ASCII plano. Visibles en cualquier
editor de texto, terminal o navegador sin extensiones.

---

## 1. Arquitectura de paquetes

```
src/
|
+-- Main.java
|
+-- aplicacion/
    |
    +-- controladores/          Logica de negocio + SQL
    |   |
    |   +-- ControladorLogin.java
    |   +-- ControladorRegistro.java
    |   +-- ControladorAdmin.java
    |   +-- ControladorCajero.java
    |   +-- ControladorClienteABM.java
    |   +-- ControladorProveedorABM.java
    |   +-- ControladorUsuarioABM.java
    |   +-- ControladorDepositoABM.java
    |   +-- ControladorFactura.java
    |
    +-- vistas/                 Interfaces graficas (Swing)
    |   |
    |   +-- VentanaPrincipal.java      JFrame contenedor
    |   +-- VistaLogin.java            Login
    |   +-- VistaRegistro.java         Registro
    |   +-- VistaAdmin.java            Panel Admin
    |   +-- VistaFormulario.java       Dialogo generico
    |   +-- VistaClienteABM.java       CRUD Clientes
    |   +-- VistaProveedorABM.java     CRUD Proveedores
    |   +-- VistaUsuarioABM.java       CRUD Usuarios
    |   +-- VistaDepositoABM.java      CRUD Productos
    |   +-- VistaCajero.java           POS / Ventas
    |   +-- VistaFactura.java          Lista de facturas
    |   +-- VistaDetallesFactura.java  Detalle de factura
    |
    +-- modelos/                POJOs (datos)
    |   |
    |   +-- Usuario.java
    |   +-- Cliente.java
    |   +-- Proveedor.java
    |   +-- Producto.java
    |   +-- Factura.java
    |   +-- DetalleFactura.java
    |
    +-- servicios/              Infraestructura
    |   |
    |   +-- Conexion.java       MySQL connection
    |   +-- Autenticacion.java  Credenciales
    |
    +-- filtros/                Validacion en tiempo real
        |
        +-- FiltroNumerico.java
        +-- FiltroTexto.java
        +-- FiltroAlfanumerico.java
        +-- ValidadorMail.java
        +-- ValidadorCampos.java
        +-- ValidadorCantidadAdmin.java
```

---

## 2. Flujo de inicio de sesion

```
                      +-----------+
                      | Main.java |
                      +-----------+
                           |
                           v
                  +-----------------+
                  | VentanaPrincipal|
                  +-----------------+
                           |
                           v
                  +-----------------+
                  | ControladorLogin|
                  +-----------------+
                           |
                           v
                    +-----------+
                    | VistaLogin|
                    +-----------+
                    /           \
                   /             \
          +-----------+     +-----------+
          | btn       |     | btnCrear  |
          | Ingresar  |     | Cuenta    |
          +-----------+     +-----------+
               |                 |
               v                 v
     +---------+---------+  +-----------+
     | validar(mail,pass)|  | Controla- |
     +---------+---------+  | dorRegis- |
               |            | tro       |
          +----+----+       +-----------+
          |         |
        true      false
          |         |
          v         v
   +------+-----+  +--------+
   | iniciar    |  | "Creden|
   | Sesion()   |  | ciales |
   +------+-----+  | incor- |
          |        | rectas"|
          |        +--------+
     +----+----+
     |         |
   Admin    Cajero
     |         |
     v         v
 +--------+ +--------+
 |Vista   | |Vista   |
 |Admin   | |Cajero  |
 +--------+ +--------+
     |         |
   Depo     Ninguno
     |         |
     v         v
 +--------+ +--------+
 |Vista   | | "Sin   |
 |DepoABM | | rol"   |
 +--------+ +--------+
```

---

## 3. Navegacion entre pantallas

```
                        +-----------+
                        |  LOGIN    |
                        +-----------+
                        /           \
                       /             \
              +--------+             +---------+
              | btn    |             | btnCrear|
              |Ingresar|             | Cuenta  |
              +--------+             +---------+
                  |                      |
      +-----------+---------+            v
      |           |         |      +-----------+
      v           v         v      | REGISTRO  |
  +------+  +--------+  +------+  +-----------+
  |ADMIN |  | CAJERO |  |DEPO  |        |
  +------+  +--------+  +------+        |
  |  |   |       |          |           v
  |  |   |       |          |      +-----------+
  |  |   |       |          |      |  LOGIN    |
  |  |   |       |          |      +-----------+
  |  |   |       |          |
  |  |   |       +----+-----+
  |  |   |            |
  |  |   |            v
  |  |   |      +----------+
  |  |   |      |Vista     |
  |  |   |      |Deposito  |
  |  |   |      |ABM       |
  |  |   |      +----------+
  |  |   |
  |  |   +-----------+
  |  |               |
  |  |               v
  |  |          +--------+
  |  |          |Vista   |
  |  |          |Cajero  |
  |  |          +--------+
  |  |               |
  |  |    +----------+---------+
  |  |    |                    |
  |  |    v                    v
  |  | +--------+        +--------+
  |  | |Vista   |        |Vista   |
  |  | |Factura |        |[vuelve |
  |  | +--------+        | a rol] |
  |  |    |              +--------+
  |  |    v
  |  | +--------+
  |  | |Vista   |
  |  | |Detalles|
  |  | |Factura |
  |  | +--------+
  |  |
  |  +---+---+---+
  |      |   |   |
  |      v   v   v
  |  +------+ +------+ +------+
  |  |Vista | |Vista | |Vista |
  |  |Clien | |Prove | |Usuar |
  |  |ABM   | |ABM   | |ABM   |
  |  +------+ +------+ +------+
  |      |        |        |
  |      v        v        v
  |  +------+ +------+ +------+
  |  |Vista | |Vista | |Vista |
  |  |Fact  | |[vuelta] [vuelta]
  |  +------+
  |      |
  |      v
  |  +--------+
  |  |Vista   |
  |  |Detalles|
  |  +--------+
  |
  +-----------+-------------+
              |               |
              v               v
         [Cerrar           [Vuelta a
          Sesion]           Login]
              |
              v
         +--------+
         | LOGIN  |
         +--------+
```

---

## 4. Flujo de venta (Cajero/POS)

```
           +-------------------+
           |   VistaCajero     |
           +-------------------+
           /                   \
          /  PANEL CLIENTE      \
         /                       \
  +-------------+          +-------------+
  | btnBuscar   |          | btnNuevo    |
  | Cliente     |          | Cliente     |
  +-------------+          +-------------+
        |                        |
        v                        v
  +-----------+           +-------------+
  | Controla- |           | Controlador |
  | dorCliente|           | ClienteABM  |
  | .buscar() |           | .agregar()  |
  +-----------+           +-------------+
        |
   +----+----+
   |         |
 Encontrado No existe
   |         |
   v         v
 [Carga    [Limpia
  datos]    campos]
   |
   |        +---------------+
   +--------+ PANEL PRODUCTO |
            +---------------+
            /               \
   +-----------+      +-----------+
   | btnBuscar |      | btnNuevo  |
   | Producto  |      | Producto  |
   +-----------+      +-----------+
        |                  |
        v                  v
  +-----------+      +-------------+
  | Controla- |      | Controlador |
  | dorDepo   |      | DepositoABM |
  | .selec-   |      | .agregar()  |
  | cionar()  |      +-------------+
  +-----------+
        |
   +----+----+
   |         |
 1 result  Multiples
   |         |
   v         v
 [Carga   [Selecciona
  datos]   de lista]
   |
   +--------+
            |
            v
  +-------------------+
  | Ingresa cantidad  |
  | y descuento %     |
  +-------------------+
            |
            v
  +-------------------+
  | btnAgregarAlCarro |
  +-------------------+
            |
            v
  +-------------------+
  | Valida cantidad>0 |
  | Agrega fila a     |
  | tblCarrito        |
  +-------------------+
            |
            v
  +-------------------+
  | Repite para cada  |
  | producto          |
  +-------------------+
            |
            v
  +-------------------+
  | Ingresa descuento |
  | global %          |
  +-------------------+
            |
            v
  +-------------------+
  | btnCalcularTotal  |
  +-------------------+
            |
            v
  +-------------------+
  | Valida descuento  |
  | 0-100             |
  | Muestra subtotal, |
  | descuento, total  |
  +-------------------+
            |
            v
  +-------------------+
  | btnFinalizarCompra|
  +-------------------+
            |
            v
  +-------------------+
  | Validaciones:     |
  | - Cliente ok?     |
  | - Carrito vacio?  |
  | - Total calc?     |
  +-------------------+
            |
            v
  +-------------------+
  | Confirmacion      |
  +-------------------+
     |            |
    Si           No
     |            |
     v            v
 +--------+  [No hace nada]
 | Contr. |
 | Cajero |
 | .final-|
 | izar() |
 +--------+
     |
     v
+----+-----------------------+
| Transaction (autoCommit=no)|
| 1. INSERT factura          |
| 2. INSERT detalles (batch) |
| 3. UPDATE stock c/item     |
|    si stock < cant -> ROLL |
|    BACK + error            |
| 4. COMMIT                  |
+----------------------------+
     |
     v
+--------+
| "Compra|
| finaliz|
| ada"   |
+--------+
     |
     v
[Limpia carrito y campos]
```

---

## 5. Flujo ABM generico (Clientes, Proveedores, Usuarios, Productos)

```
       +-------------------+
       | VistaXABM         |
       | (X=Cliente/Provee|
       |  dor/Usuario/Depo)|
       +-------------------+
        /    |    |    \    \
       /     |    |     \    \
      v      v    v      v    v
  +------+ +--+ +--+ +--+ +--+
  | Bus- | |Ag | |Mo | |De | |Vo|
  | car  | |re | |di | |sh | |l |
  |      | |gar| |fi | |ab | |ve|
  |      | |   | |car| |il | |r |
  +------+ +--+ +--+ +--+ +--+
      |      |    |    |    |
      v      |    |    |    v
  +-------+  |    |    | +-----+
  | Contr |  |    |    | | Cont|
  | .bus- |  |    |    | | .lo |
  | car() |  |    |    | | gin |
  +-------+  |    |    | +-----+
      |      |    |    |
  +---+---+  |    |    |
  |       |  |    |    |
 Encont. No  |    |    |
   |      |  |    |    |
   v      v  |    |    |
 [Muestra [Msj|    |    |
  en tabla] err|    |    |
               |    |    |
               v    |    |
          +-------+ |    |
          | Mostr | |    |
          | ar    | |    |
          | Form  | |    |
          +-------+ |    |
               |    |    |
               v    |    |
          [Campos]  |    |
               |    |    |
               v    |    |
          [OK?]     |    |
           /  \     |    |
          Si   No   |    |
           |    |   |    |
           v    |   |    |
      +--------+|   |    |
      | Contr  ||   |    |
      | .agre- ||   |    |
      | gar()  ||   |    |
      +--------+|   |    |
           |    |   |    |
      +----+----+   |    |
      |         |   |    |
    Exito    Falla  |    |
   (id>0)   (-1/f)  |    |
      |         |   |    |
      v         v   |    |
  [Repobla  [No hace|    |
   tabla]    nada]  |    |
                    |    |
                    v    |
               +--------+|
               | Contr  ||
               | .modif-||
               | icar() ||
               +--------+|
                    |    |
               +----+----+
               |         |
             Exito    Falla
               |         |
               v         v
           [Repobla  [No hace
            tabla]    nada]
                       |
                       v
                  +--------+
                  | Contr  |
                  | .alter-|
                  | nar()  |
                  +--------+
                       |
                       v
                  +--------+
                  | Confir |
                  | ma?    |
                  +--------+
                    |    |
                   Si   No
                    |    |
                    v    |
               [UPDATE   |
                habilita-|
                do]      |
                    |    |
                    v    v
               [Repobla tabla]
```

---

## 6. Flujo de factura y detalle

```
     [Desde VistaClienteABM]
     btnMostrarFacturas
            |
            v
    +---------------+
    | VistaFactura  |
    +---------------+
            |
            v
    +---------------+
    | Controlador   |
    | Factura       |
    | .obtener      |
    | FacturasPor   |
    | Cliente()     |
    +---------------+
            |
            v
    +---------------+
    | SELECT        |
    | facturas      |
    | WHERE id_     |
    | cliente = ?   |
    | ORDER fecha   |
    | DESC          |
    +---------------+
            |
            v
    +---------------+
    | Tabla con:    |
    | N, Fecha,     |
    | Total, Vended |
    +---------------+
            |
            v
    btnVerDetalle
            |
            v
    +---------------+
    | VistaDetalles |
    | Factura       |
    +---------------+
       |          |
       v          v
  +--------+  +--------+
  | Contr. |  | Contr. |
  | Factur |  | Factur |
  | a.obte |  | a.obte |
  | nerFac |  | nerDet |
  | tura   |  | alles  |
  | Com-   |  | PorFac |
  | pleta()|  | tura() |
  +--------+  +--------+
       |          |
       v          v
  +--------+  +--------+
  | SELECT |  | SELECT |
  | factura|  | detalle|
  | JOIN   |  | s JOIN |
  | cliente|  | produc |
  | WHERE  |  | tos    |
  | id = ? |  | WHERE  |
  +--------+  | id_fac |
       |      | tura=? |
       v      +--------+
  [Campos          |
   llenos:    [Tabla con:
    N factura,   ID, Producto,
    fecha,       Cantidad, P/U,
    cliente,     Descuento %,
    vendedor,    Subtotal]
    subtotal,
    descuento,
    total]
```

---

## 7. Base de datos (esquema de tablas)

```
+------------------+
|    usuarios      |
+------------------+
| id           INT | PK
| nombre     VARCHAR|
| apellido   VARCHAR|
| dni        VARCHAR|
| telefono   VARCHAR|
| direccion  VARCHAR|
| mail       VARCHAR| UK
| rol        VARCHAR|
| password   VARCHAR|
| habilitado    INT |
+------------------+
        |
        | (id_vendedor)
        v
+------------------+     +----------------------+
|    facturas      |     | detalles_de_facturas  |
+------------------+     +----------------------+
| id           INT | PK  | id               INT | PK
| numero_factura   |     | id_factura       INT | FK
| id_cliente   INT | FK  | id_producto      INT | FK
| nombre_cliente   |     | cantidad         INT |
| apellido_cliente |     | precio_unitario  INT |
| id_vendedor  INT | FK  | descuento        INT |
| nombre_vendedor  |     | subtotal         INT |
| apellido_vendedor|     +----------------------+
| fecha_emision    |              |
| subtotal      INT|              | (id_producto)
| descuento_%   INT|              v
| valor_desc    INT|     +------------------+
| total_compra  INT|     |    productos      |
+------------------+     +------------------+
        |                | id           INT | PK
        | (id_cliente)   | descripcion VARCHAR|
        v                | precio        INT |
+------------------+     | stock         INT |
|    clientes      |     | id_proveedor INT | FK
+------------------+     | habilitado    INT |
| id           INT | PK  +------------------+
| nombre     VARCHAR|              |
| apellido   VARCHAR|              | (id_proveedor)
| dni        VARCHAR|              v
| telefono   VARCHAR|     +------------------+
| direccion  VARCHAR|     |   proveedores     |
| mail       VARCHAR| UK  +------------------+
| habilitado    INT |     | id           INT | PK
+------------------+     | nombre     VARCHAR|
                          | direccion  VARCHAR|
                          | telefono   VARCHAR|
                          | mail       VARCHAR| UK
                          | habilitado    INT |
                          +------------------+

Relaciones:
  productos.id_proveedor   -> proveedores.id
  facturas.id_cliente      -> clientes.id
  facturas.id_vendedor     -> usuarios.id
  detalles_de_facturas.id_factura  -> facturas.id
  detalles_de_facturas.id_producto -> productos.id
```

---

## 8. Flujo de validacion de datos

```
+-------------------+
| Entrada del       |
| usuario (teclado) |
+-------------------+
        |
        v
+-------------------+
| DocumentFilter    |
| (tiempo real)     |
+-------------------+
        |
   +----+----+
   |         |
 Caracter  Caracter
 valido    invalido
   |         |
   v         v
[Se inserta  [Se descarta
 en el       silenciosa-
 JTextField]  mente]
                |
                v
          [No aparece
           en pantalla]
                |
                v
    +---------------------+
    | ValidadorCampos     |
    | (campos requeridos) |
    +---------------------+
                |
           +----+----+
           |         |
        Todos     Faltan
        ok        campos
           |         |
           v         v
    +--------+  +--------+
    | Sigue  |  |"Los si-|
    | al     |  | guien- |
    | valida-|  | tes    |
    | dor de |  | campos |
    | mail   |  | son    |
    +--------+  | obliga-|
           |    | torios"|
           v    +--------+
    +-----------------+
    | Validacion mail  |
    | (al enviar form) |
    +-----------------+
           |
      +----+----+
      |         |
   Valido   Invalido
      |         |
      v         v
 +--------+ +--------+
 | Sigue  | |"El for-|
 | al     | | mato   |
 | contro-| | del    |
 | lador  | | mail   |
 +--------+ | no es  |
      |     | valido"|
      |     +--------+
      v
 +--------+
 | Valida |
 | unidad |
 | de mail|
 | en BD  |
 +--------+
      |
 +----+----+
 |         |
Unico   Duplicado
 |         |
 v         v
+--------+ +--------+
| INSERT | |"Este   |
| /      | | mail   |
| UPDATE | | ya ha  |
+--------+ | sido   |
    |      | regis- |
    v      | trado" |
[Exito]    +--------+
    |
    v
[JOptionPane
 exito]
```

---

## 9. Relacion Vista-Controlador-Modelo

```
+------------------+
|      VISTA        |
| (Swing JPanel)    |
+------------------+
| - JTextField      |
| - JTable          |
| - JButton         |
| - ActionListener  |
+------------------+
        |
        | Llama metodos del
        | controlador
        v
+------------------+
|   CONTROLADOR     |
+------------------+
| - Conexion        |
| + metodoNegocio() |
+------------------+
        |
        | Accede a BD
        | via JDBC
        v
+------------------+
|  BASE DE DATOS    |
|     MySQL         |
+------------------+
        |
        | Retorna datos
        | como POJOs
        v
+------------------+
|     MODELO        |
| (POJO / JavaBean) |
+------------------+
| - int id          |
| - String nombre   |
| + get/set         |
+------------------+
        |
        | Devuelve objeto(s)
        | al controlador
        v
+------------------+
|   CONTROLADOR     |
+------------------+
        |
        | Retorna resultado
        | a la vista
        v
+------------------+
|      VISTA        |
| (actualiza tabla, |
|  muestra mensaje) |
+------------------+

Ejemplo concreto - ClienteABM:

VistaClienteABM
    |  btnAgregar ->
    |  VistaFormulario.mostrarDialogo()
    |  Si OK ->
    |  controlador.agregarCliente(...)
    v
ControladorClienteABM
    |  1. ValidaMail.esValido(mail)
    |  2. Verifica unicidad en BD
    |  3. INSERT INTO clientes ...
    |  4. RETURN_GENERATED_KEYS
    |  5. JOptionPane exito
    v
    Retorna int (ID > 0) o -1
    |
    v
VistaClienteABM
    |  Si retorno > -1 ->
    |  controlador.obtenerClientesPorHabilitado()
    |  poblarTabla(lista)
    v
[Tabla actualizada]
```

---

## 10. Diagrama de estados de un registro

```
                +----------+
                | CREADO   |
                | (INSERT) |
                +----------+
                     |
                     v
              +-----------+
              | HABILITADO|
              | activo=1  |
              +-----------+
              /           \
             /             \
            v               v
    +-----------+     +-----------+
    | MODIFICADO|     | DESHABILI-|
    | (UPDATE)  |     | TADO      |
    +-----------+     | activo=0  |
            \         +-----------+
             \             /
              \           /
               v         v
              +-----------+
              | RE-HABILI-|
              | TADO      |
              | activo=1  |
              +-----------+

Estados posibles para: Cliente, Proveedor, Usuario, Producto.

Transiciones:
  INSERT      -> habilitado = 1
  alternar()  -> habilitado = CASE WHEN 1 THEN 0 ELSE 1 END
  UPDATE      -> habilitado no cambia (solo datos)
  DELETE      -> No existe (solo alternar habilitado)
```
