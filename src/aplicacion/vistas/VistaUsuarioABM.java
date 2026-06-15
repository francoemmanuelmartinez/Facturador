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
    private JButton botonBuscar;
    private JTable tablaUsuarios;
    public JPanel panelUsuarioABM;
    private JTextField dniTF;
    private JButton eliminarButton;
    private JButton modificarButton;
    private JButton agregarButton;
    private JComboBox comboBoxFiltroHabilitado;
    private JButton volverButton;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String[] columnas = {"Nombre", "Apellido", "Dni", "Telefono", "Direccion", "Mail", "Rol", "Password", "Habilitado"};

    public VistaUsuarioABM(Usuario usuario,VentanaPrincipal ventanaPrincipal) {

        setModeloTabla();

        comboBoxFiltroHabilitado.addItem("Habilitados");
        comboBoxFiltroHabilitado.addItem("Deshabilitados");
        comboBoxFiltroHabilitado.setSelectedIndex(0);

        ControladorUsuarioABM controlador = new ControladorUsuarioABM();
        List<Usuario> usuarios = controlador.obtenerUsuariosPorHabilitado(1);
        poblarTabla(usuarios);

        comboBoxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                poblarTabla(ctrl.obtenerUsuariosPorHabilitado(filtro));

                if (comboBoxFiltroHabilitado.getSelectedIndex() == 0) {
                    eliminarButton.setText("Deshabilitar");
                } else {
                    eliminarButton.setText("Habilitar");
                }
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                String texto = dniTF.getText().trim();
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
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Usuario",
                        new VistaFormulario.Campo("Nombre:"),
                        new VistaFormulario.Campo("Apellido:"),
                        new VistaFormulario.Campo("DNI:"),
                        new VistaFormulario.Campo("Teléfono:"),
                        new VistaFormulario.Campo("Dirección:"),
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
                            valores.get("Teléfono:"),
                            valores.get("Dirección:"),
                            valores.get("Mail:"),
                            valores.get("Contraseña:"),
                            valores.get("Rol:")
                    )) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaUsuarios.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla");
                    return;
                }

                String dniOriginal = modeloTabla.getValueAt(fila, 2).toString();

                String rolActual = modeloTabla.getValueAt(fila, 6).toString();

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Usuario",
                        new VistaFormulario.Campo("Nombre:", modeloTabla.getValueAt(fila, 0).toString()),
                        new VistaFormulario.Campo("Apellido:", modeloTabla.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("DNI:", dniOriginal),
                        new VistaFormulario.Campo("Teléfono:", modeloTabla.getValueAt(fila, 3).toString()),
                        new VistaFormulario.Campo("Dirección:", modeloTabla.getValueAt(fila, 4).toString()),
                        new VistaFormulario.Campo("Mail:", modeloTabla.getValueAt(fila, 5).toString()),
                        new VistaFormulario.Campo("Rol:", new String[]{"Administrador", "Cajero", "Deposito"}, rolActual),
                        new VistaFormulario.Campo("Contraseña:", true, modeloTabla.getValueAt(fila, 7).toString())
                );
                if (valores != null) {
                    ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                    if (ctrl.modificarUsuario(
                            dniOriginal,
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Teléfono:"),
                            valores.get("Dirección:"),
                            valores.get("Mail:"),
                            valores.get("Rol:"),
                            valores.get("Contraseña:")
                    )) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaUsuarios.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario de la tabla");
                    return;
                }

                String dni = modeloTabla.getValueAt(fila, 2).toString();
                int habilitadoActual = Integer.parseInt(modeloTabla.getValueAt(fila, 8).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de " + nuevoEstado + " al usuario con DNI " + dni + "?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Usuario" : "Habilitar Usuario",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorUsuarioABM ctrl = new ControladorUsuarioABM();
                    if (ctrl.toggleHabilitado(dni)) {
                        poblarTabla(ctrl.obtenerUsuariosPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
        });
    }

    public void setModeloTabla() {
        modeloTabla.setColumnIdentifiers(columnas);
        tablaUsuarios.setModel(modeloTabla);
    }

    public void poblarTabla(List<Usuario> usuarios) {

        modeloTabla.setRowCount(0);

        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{
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



