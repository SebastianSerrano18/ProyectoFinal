/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloConsumoSoles {
// Nombre del distrito
    private String distrito;
    // Promedio de consumo en soles
    private double promedioSoles;

    /**
     * Constructor para inicializar el distrito y su promedio de consumo en soles.
     * @param distrito Nombre del distrito
     * @param promedioSoles Promedio de consumo en soles
     */
    public ModeloConsumoSoles(String distrito, double promedioSoles) {
        this.distrito = distrito;
        this.promedioSoles = promedioSoles;
    }

    /**
     * Sobrescribimos el método toString() para dar una representación personalizada del objeto.
     * @Override indica que estamos reemplazando el método toString() de la clase Object,
     * mostrando el distrito y su promedio con un formato específico.
     */
    @Override
    public String toString() {
        // Formateamos el texto para alinear el distrito y mostrar el promedio con 2 decimales.
        return String.format("%-30s | %10.2f", distrito, promedioSoles);
    }
}