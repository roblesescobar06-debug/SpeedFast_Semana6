package vista;

import modelo.ControladorDeEnvios;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema SpeedFast.
 * Contiene el controlador común que comparten todas las ventanas.
 */
public class VentanaPrincipal extends JFrame {

    private ControladorDeEnvios controlador;

    public VentanaPrincipal() {
        controlador = new ControladorDeEnvios();

        setTitle("SpeedFast - Sistema de gestión de entregas");
        setSize(460, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ----- Encabezado -----
        JPanel panelEncabezado = new JPanel(new GridLayout(2, 1));
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JLabel lblTitulo = new JLabel("SpeedFast", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel lblSubtitulo = new JLabel("Gestión de pedidos y entregas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(90, 90, 90));

        panelEncabezado.add(lblTitulo);
        panelEncabezado.add(lblSubtitulo);

        // ----- Botones del menú -----
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 12));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        JButton btnRegistrar = crearBoton("Registrar pedido");
        JButton btnListar = crearBoton("Listar pedidos");
        JButton btnAsignar = crearBoton("Asignar repartidor / Iniciar entrega");
        JButton btnSalir = crearBoton("Salir");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnSalir);

        // ----- Navegación entre ventanas -----
        btnRegistrar.addActionListener(e -> new VentanaRegistroPedido(controlador).setVisible(true));
        btnListar.addActionListener(e -> new VentanaListaPedidos(controlador).setVisible(true));
        btnAsignar.addActionListener(e -> new VentanaAsignarRepartidor(controlador).setVisible(true));
        btnSalir.addActionListener(e -> confirmarSalida());

        // ----- Pie -----
        JLabel lblPie = new JLabel("Desarrollo Orientado a Objetos II - Semana 6", SwingConstants.CENTER);
        lblPie.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblPie.setForeground(new Color(120, 120, 120));
        lblPie.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));

        add(panelEncabezado, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(lblPie, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        return boton;
    }

    private void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas salir del sistema?",
                "Salir",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}