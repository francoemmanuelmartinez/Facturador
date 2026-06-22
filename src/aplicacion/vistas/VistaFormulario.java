package aplicacion.vistas;

import aplicacion.filtros.FiltroAlfanumerico;
import aplicacion.filtros.FiltroNumerico;
import aplicacion.filtros.FiltroTexto;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialogo generico para formularios de entrada de datos. Construye
 * un panel con {@link GridBagLayout} a partir de una lista de
 * {@link Campo}, aplica {@link FiltroNumerico}, {@link FiltroTexto} y
 * {@link FiltroAlfanumerico} segun la etiqueta, y retorna un mapa
 * con los valores ingresados.
 *
 * @see FiltroNumerico
 * @see FiltroTexto
 * @see FiltroAlfanumerico
 * @since 1.0
 */
public class VistaFormulario {

    public static class Campo {
        private String etiqueta;
        private boolean esPassword;
        private String[] opciones;
        private String valorInicial;
        private boolean requerido;

        public Campo(String etiqueta) {
            this(etiqueta, false, null, false);
        }

        public Campo(String etiqueta, boolean esPassword) {
            this(etiqueta, esPassword, null, false);
        }

        public Campo(String etiqueta, boolean esPassword, boolean requerido) {
            this(etiqueta, esPassword, null, requerido);
        }

        public Campo(String etiqueta, String valorInicial) {
            this(etiqueta, false, valorInicial, false);
        }

        public Campo(String etiqueta, boolean esPassword, String valorInicial) {
            this(etiqueta, esPassword, valorInicial, false);
        }

        public Campo(String etiqueta, boolean esPassword, String valorInicial, boolean requerido) {
            this.etiqueta = etiqueta;
            this.esPassword = esPassword;
            this.valorInicial = valorInicial;
            this.requerido = requerido;
        }

        public Campo(String etiqueta, String[] opciones) {
            this.etiqueta = etiqueta;
            this.opciones = opciones;
            this.requerido = true;
        }

        public Campo(String etiqueta, String[] opciones, String valorInicial) {
            this.etiqueta = etiqueta;
            this.opciones = opciones;
            this.valorInicial = valorInicial;
            this.requerido = true;
        }
    }

    public static Map<String, String> mostrarDialogo(String titulo, Campo... campos) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<JPasswordField> passwordFields = new ArrayList<>();

        for (int i = 0; i < campos.length; i++) {
            Campo c = campos[i];

            gbc.gridx = 0; gbc.gridy = i;
            gbc.gridwidth = 1; gbc.weightx = 0;
            String texto = c.requerido ? c.etiqueta + " *" : c.etiqueta;
            JLabel label = new JLabel(texto);
            if (c.requerido) label.setForeground(Color.RED);
            panel.add(label, gbc);

            gbc.gridx = 1; gbc.weightx = 1;
            if (c.opciones != null) {
                JComboBox<String> cb = new JComboBox<>(c.opciones);
                if (c.valorInicial != null) {
                    for (int j = 0; j < c.opciones.length; j++) {
                        if (c.opciones[j].equals(c.valorInicial)) {
                            cb.setSelectedIndex(j);
                            break;
                        }
                    }
                }
                panel.add(cb, gbc);
            } else if (c.esPassword) {
                JPasswordField pf = new JPasswordField(c.valorInicial);
                pf.setColumns(15);
                panel.add(pf, gbc);
                passwordFields.add(pf);
            } else {
                JTextField tf = new JTextField(c.valorInicial);
                tf.setColumns(15);
                String etq = c.etiqueta;
                if (etq.equals("DNI:") || etq.equals("Telefono:") || etq.equals("Precio:") ||
                    etq.equals("Stock:") || etq.equals("Cantidad:") || etq.equals("Descuento %:")) {
                    ((AbstractDocument) tf.getDocument()).setDocumentFilter(new FiltroNumerico());
                } else if (etq.equals("Nombre:") || etq.equals("Apellido:")) {
                    ((AbstractDocument) tf.getDocument()).setDocumentFilter(new FiltroTexto());
                } else if (etq.equals("Direccion:") || etq.equals("Descripcion:")) {
                    ((AbstractDocument) tf.getDocument()).setDocumentFilter(new FiltroAlfanumerico());
                }
                panel.add(tf, gbc);
            }
        }

        if (!passwordFields.isEmpty()) {
            JCheckBox mostrarPassword = new JCheckBox("Mostrar contraseña");
            mostrarPassword.addActionListener(ev -> {
                char echo = mostrarPassword.isSelected() ? (char) 0 : '\u2022';
                for (JPasswordField pf : passwordFields) {
                    pf.setEchoChar(echo);
                }
            });
            gbc.gridx = 0; gbc.gridy = campos.length;
            gbc.gridwidth = 2; gbc.weightx = 0;
            panel.add(mostrarPassword, gbc);
        }

        int option = JOptionPane.showConfirmDialog(null, panel, titulo, JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return null;

        Map<String, String> resultado = new LinkedHashMap<>();
        for (int i = 0; i < campos.length; i++) {
            Component comp = panel.getComponent(2 * i + 1);
            if (comp instanceof JTextField) {
                resultado.put(campos[i].etiqueta, ((JTextField) comp).getText().trim());
            } else if (comp instanceof JPasswordField) {
                resultado.put(campos[i].etiqueta, new String(((JPasswordField) comp).getPassword()).trim());
            } else if (comp instanceof JComboBox) {
                Object selected = ((JComboBox<?>) comp).getSelectedItem();
                resultado.put(campos[i].etiqueta, selected != null ? selected.toString().trim() : "");
            }
        }

        StringBuilder faltan = new StringBuilder();
        for (Campo c : campos) {
            if (c.requerido) {
                String valor = resultado.get(c.etiqueta);
                if (valor == null || valor.isEmpty()) {
                    if (faltan.length() > 0) faltan.append(", ");
                    faltan.append(c.etiqueta);
                }
            }
        }
        if (faltan.length() > 0) {
            JOptionPane.showMessageDialog(null,
                "Los siguientes campos son obligatorios: " + faltan.toString());
            return null;
        }

        return resultado;
    }
}
