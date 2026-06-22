package aplicacion.vistas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VistaFormulario {

    public static class Campo {
        private String etiqueta;
        private boolean esPassword;
        private String[] opciones;
        private String valorInicial;

        public Campo(String etiqueta) {
            this(etiqueta, false, null);
        }

        public Campo(String etiqueta, boolean esPassword) {
            this(etiqueta, esPassword, null);
        }

        public Campo(String etiqueta, String valorInicial) {
            this(etiqueta, false, valorInicial);
        }

        public Campo(String etiqueta, boolean esPassword, String valorInicial) {
            this.etiqueta = etiqueta;
            this.esPassword = esPassword;
            this.valorInicial = valorInicial;
        }

        public Campo(String etiqueta, String[] opciones) {
            this.etiqueta = etiqueta;
            this.opciones = opciones;
        }

        public Campo(String etiqueta, String[] opciones, String valorInicial) {
            this.etiqueta = etiqueta;
            this.opciones = opciones;
            this.valorInicial = valorInicial;
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
            panel.add(new JLabel(c.etiqueta), gbc);

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
        return resultado;
    }
}
