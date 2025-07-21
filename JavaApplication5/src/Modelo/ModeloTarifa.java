/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloTarifa {

    // Nombre de la tarifa
    private String tarifa;
    // Cantidad de usuarios que tienen esa tarifa
    private int cantidad;

    //Constructor para inicializar los valores de tarifa y cantidad.
    public ModeloTarifa(String tarifa, int cantidad) {
        this.tarifa = tarifa;
        this.cantidad = cantidad;
    }

    //Sobrescribimos el método toString() para dar un formato personalizado cuando el objeto se convierta a texto.
    //@Override significa que estamos reemplazando el método toString() de Object.
    @Override
    public String toString() {
        // Formatea el texto para alinear la tarifa y la cantidad en columnas
        return String.format("%-15s | %10d", tarifa, cantidad);
    }
}