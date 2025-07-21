/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.ModeloConsumoSoles;
import Modelo.ModeloErrores;

import java.io.*;
/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ControladorConsumoSoles {
  // Archivo donde se guardará el reporte final
    private final String archivoSalida = "consumoSoles.txt";
    // Archivo donde se registrarán los errores
    private final String archivoLog = "src/auditoria.log";

    //Este método lee un archivo de datos y calcula el promedio de consumo (S/) por distrito.
    public void procesarArchivo(String rutaArchivo, boolean exportar) {
        // Título del reporte
        String resultado = String.format("%-30s | %s\n", "Distrito", "Promedio (S/)");
        resultado += "-----------------------------------------------\n";

        try (BufferedReader br1 = new BufferedReader(new FileReader(rutaArchivo))) {
            br1.readLine(); // Saltamos la primera línea porque es la cabecera
            String linea;
            String distritosProcesados = ""; // Aquí se guardan los distritos que ya se calcularon

            // Leer cada línea del archivo
            while ((linea = br1.readLine()) != null) {
                String[] partes = linea.split(";"); // Separar los datos por ";"
                if (partes.length > 22) { // Asegurar que haya suficientes columnas
                    String distrito = partes[12].trim(); // Tomar el nombre del distrito

                    // Evitar procesar un distrito más de una vez
                    if (!distritosProcesados.contains("[" + distrito + "]")) {
                        distritosProcesados += "[" + distrito + "]"; // Marcamos que ya está procesado

                        double suma = 0;    // Suma total de soles para el distrito
                        int contador = 0;   // Contador de registros del distrito

                        // Volvemos a abrir el archivo para sumar los soles del mismo distrito
                        try (BufferedReader br2 = new BufferedReader(new FileReader(rutaArchivo))) {
                            br2.readLine(); // Saltar cabecera de nuevo
                            String l2;
                            while ((l2 = br2.readLine()) != null) {
                                String[] p2 = l2.split(";");
                                if (p2.length > 22 && p2[12].trim().equals(distrito)) {
                                    double soles = Double.parseDouble(p2[21].replace(",", ".").trim());
                                    suma += soles;
                                    contador++;
                                }
                            }
                        }

                        // Calcular el promedio
                        double promedio = (contador > 0) ? suma / contador : 0;
                        ModeloConsumoSoles modelo = new ModeloConsumoSoles(distrito, promedio);
                        resultado += modelo.toString() + "\n"; // Agregar el resultado al reporte
                    }
                }
            }

            // Mostrar el resultado o exportarlo
            if (exportar) {
                try (FileWriter fw = new FileWriter(archivoSalida)) {
                    fw.write(resultado);
                }
                System.out.println("Reporte exportado a " + archivoSalida);
            } else {
                System.out.println(resultado);
            }

        } catch (Exception e) {
            // Si ocurre un error, se guarda en el log
            registrarError("ControladorConsumoSoles", e.getMessage());
            System.out.println("Ocurrió un error al procesar el archivo.");
        }
    }

    //Este método registra errores en el archivo de log.
    private void registrarError(String clase, String mensaje) {
        try (FileWriter fw = new FileWriter(archivoLog, true)) {
            fw.write(new ModeloErrores(clase, mensaje).toString() + "\n");
        } catch (IOException ex) {
            System.err.println("Error al registrar auditoría: " + ex.getMessage());
        }
    }
}