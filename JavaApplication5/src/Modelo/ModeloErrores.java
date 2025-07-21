/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloErrores {
  
 // Nombre de la clase donde ocurrió el error
    private String clase;
    // Mensaje del error
    private String mensaje;
    // Fecha y hora en que ocurrió el error
    private String fechaHora;

    //Constructor que inicializa los datos del error.

    public ModeloErrores(String clase, String mensaje) {
        this.clase = clase;
        this.mensaje = mensaje;
        this.fechaHora = obtenerFechaHoraActual(); // Asigna la fecha y hora actuales
    }

    // Obtiene la fecha y hora actuales con un formato específico.
    private String obtenerFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now(); // Obtenemos la fecha y hora actuales
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ahora.format(formato); // Convertimos a texto con el formato elegido
    }

    //Sobrescribimos el método toString() para mostrar el error con fecha, clase y mensaje.
    @Override
    public String toString() {
        return String.format("[%s] [%s] %s", fechaHora, clase, mensaje);
    }
}