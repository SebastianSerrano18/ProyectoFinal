/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloConsumoKWh {
     // Atributo que almacena el nombre del distrito
    private String distrito;

    // Atributo que almacena el promedio de consumo eléctrico en kWh para ese distrito
    private double promedioConsumo;

    // Constructor que inicializa los atributos con valores dados
    public ModeloConsumoKWh(String distrito, double promedioConsumo) {
        this.distrito = distrito;
        this.promedioConsumo = promedioConsumo;
    }

    //Metodo que sobrescribe el método toString() para personalizar cómo se muestra el objeto
    @Override
    public String toString() {
        // Devuelve una cadena con el distrito y su promedio, bien formateado en columnas
        return String.format("%-30s | %10.2f", distrito, promedioConsumo);
      
    }
}