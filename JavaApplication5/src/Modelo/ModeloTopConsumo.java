/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloTopConsumo {
 // Nombre del usuario
    private String nombre;
    // Cantidad de consumo en kWh
    private double consumo;

    // Constructor para inicializar los datos del modelo.
    public ModeloTopConsumo(String nombre, double consumo) {
        this.nombre = nombre;
        this.consumo = consumo;
    }

//Sobrescribimos el método toString() para mostrar el objeto como texto con un formato especial.
//@Override indica que estamos reemplazando el toString() que viene de la clase Object.
    @Override
    public String toString() {
        // Mostramos el nombre y el consumo alineados en columnas con 2 decimales
        return String.format("%-30s | %10.2f", nombre, consumo);
    }
}