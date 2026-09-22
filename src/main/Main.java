package main;

import vista.VentanaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación SpeedFast.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}