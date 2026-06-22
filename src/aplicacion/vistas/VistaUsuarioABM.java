package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorUsuarioABM;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VistaUsuarioABM {
    private JLabel labelUsuario;
    private JButton btnBuscar;
    private JTable tblUsuarios;
    public JPanel panelUsuarioABM;
    private JTextField tfDni;
    private JButton btnEliminar;
    private JButton btnModificar;
    private JButton btnAgregar;
    private JComboBox cbxFiltroHabilitado;
    private JButton btnVolver;
    private DefaultTableModel mdlUsuarios = new DefaultTableModel();
    private String[] colsUsuarios = {"ID", "Nombre", "Apellido", "Dni", "Telefono", "Direccion", "Mail", "Rol", "Password", "Habilitado"};

    public VistaUsuarioABM(Usuario usuario,VentanaPrincipal ventanaPrincipal) {

        configurarTabla();

        tblUsuarios.removeColumn(tblUsuarios.getColumnModel().getColumn(9));

        cbxFiltroHabilitado.addItem("Habilitados");
        cbxFiltroHabilitado.addItem("Deshabilitados");
        cbxFiltroHabilitado.setSelectedIndex(0);

        ControladorUsuarioABM controlador = new ControladorUsuarioABM();
        List<Usuario> usuarios = controlador.obtenerUsuariosPorHabilitado(1);
        poblarTabla(usuarios);

        cbxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                poblarTabla(ctrl.obtenerUsuariosPorHabilitado(filtro));

                if (cbxFiltroHabilitado.getSelectedIndex() == 0) {
                    btnEliminar.setText("Deshabilitar");
                } else {
                    btnEliminar.setText("Habilitar");
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                String texto = tfDni.getText().trim();
                if (texto.isEmpty()) {
                    poblarTabla(ctrl.obtenerUsuariosPorHabilitado(filtro));
                } else {
                    Usuario usuario = ctrl.buscarUsuario(texto, filtro);
                    if (usuario != null) {
                        poblarTabla(Collections.singletonList(usuario));
                    }
                }
            }
        });
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Usuario",
                        new VistaFormulario.Campo("Nombre:"),
                        new VistaFormulario.Campo("Apellido:"),
                        new VistaFormulario.Campo("DNI:"),
                        new VistaFormulario.Campo("Telefono:"),
                        new VistaFormulario.Campo("Direccion:"),
                        new VistaFormulario.Campo("Mail:"),
                        new VistaFormulario.Campo("Contraseña:", true),
                        new VistaFormulario.Campo("Rol:", new String[]{"Administrador", "Cajero", "Deposito"})
                );
                if (valores != null) {
                    ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                    if (ctrl.agregarUsuario(
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:"),
                            valores.get("Contraseña:"),
                            valores.get("Rol:")
                    )) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblUsuarios.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla");
                    return;
                }

                String dniOriginal = mdlUsuarios.getValueAt(fila, 3).toString();

                String rolActual = mdlUsuarios.getValueAt(fila, 7).toString();

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Usuario",
                        new VistaFormulario.Campo("Nombre:", mdlUsuarios.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Apellido:", mdlUsuarios.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("DNI:", dniOriginal),
                        new VistaFormulario.Campo("Telefono:", mdlUsuarios.getValueAt(fila, 4).toString()),
                        new VistaFormulario.Campo("Direccion:", mdlUsuarios.getValueAt(fila, 5).toString()),
                        new VistaFormulario.Campo("Mail:", mdlUsuarios.getValueAt(fila, 6).toString()),
                        new VistaFormulario.Campo("Rol:", new String[]{"Administrador", "Cajero", "Deposito"}, rolActual),
                        new VistaFormulario.Campo("Contraseña:", true, mdlUsuarios.getValueAt(fila, 8).toString())
                );
                if (valores != null) {
                    ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                    if (ctrl.modificarUsuario(
                            dniOriginal,
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:"),
                            valores.get("Rol:"),
                            valores.get("Contraseña:")
                    )) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblUsuarios.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla");
                    return;
                }

                String dni = mdlUsuarios.getValueAt(fila, 3).toString();
                int habilitadoActual = Integer.parseInt(mdlUsuarios.getValueAt(fila, 9).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Esta seguro de " + nuevoEstado + " al usuario con DNI " + dni + "?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Usuario" : "Habilitar Usuario",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                    if (ctrl.toggleHabilitado(dni)) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
        });
    }

    public void configurarTabla() {
        mdlUsuarios.setColumnIdentifiers(colsUsuarios);
        tblUsuarios.setModel(mdlUsuarios);
    }

    public void poblarTabla(List<Usuario> usuarios) {

        mdlUsuarios.setRowCount(0);

        for (Usuario u : usuarios) {
            mdlUsuarios.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getDni(),
                    u.getTelefono(),
                    u.getDireccion(),
                    u.getMail(),
                    u.getRol(),
                    u.getPassword(),
                    u.getHabilitado()
            });
        }
    }

}



