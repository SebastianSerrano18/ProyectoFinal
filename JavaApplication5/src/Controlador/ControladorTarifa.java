/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.ModeloTarifa;
import Modelo.ModeloErrores;

import java.io.*;
/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ControladorTarifa {


    // Archivo donde se guarda el resultado final
    private final String archivoSalida = "tarifas.txt";
    // Archivo donde se registran los errores
    private final String archivoLog = "src/auditoria.log";

    //Método que procesa el archivo para contar cuántos usuarios tiene cada tarifa.

    public void procesarArchivo(String rutaArchivo, boolean exportar) {
        // Encabezado del reporte
        String resultado = String.format("%-15s | %s\n", "Tarifa", "Cantidad de Usuarios");
        resultado += "---------------------------------------\n";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Saltamos la primera línea (cabecera)

            String[] tarifas = new String[100]; // Arreglo para guardar los nombres de tarifas
            int[] conteos = new int[100];       // Arreglo para contar cuántos usuarios tiene cada tarifa
            int totalTarifas = 0;               // Contador de tarifas diferentes

            String linea;
            // Leemos el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";"); // Separamos los datos por ";"
                if (partes.length > 9) { // Verificamos que tenga suficientes columnas
                    String tarifa = partes[9].trim(); // Sacamos la tarifa (columna 10)
                    boolean existe = false;           // Para saber si la tarifa ya está guardada

                    // Buscamos si la tarifa ya existe en el arreglo
                    for (int i = 0; i < totalTarifas; i++) {
                        if (tarifas[i].equals(tarifa)) {
                            conteos[i]++;  // Si ya existe, aumentamos el contador
                            existe = true;
                            break;
                        }
                    }

                    // Si es una tarifa nueva, la agregamos
                    if (!existe && totalTarifas < 100) {
                        tarifas[totalTarifas] = tarifa;
                        conteos[totalTarifas] = 1;
                        totalTarifas++;
                    }
                }
            }

            // Construimos el texto final con las tarifas y sus conteos
            for (int i = 0; i < totalTarifas; i++) {
                ModeloTarifa modelo = new ModeloTarifa(tarifas[i], conteos[i]);
                resultado += modelo.toString() + "\n";
            }

            // Exportamos el resultado o lo mostramos en consola
            if (exportar) {
                try (FileWriter fw = new FileWriter(archivoSalida)) {
                    fw.write(resultado);
                }
                System.out.println("Resultado exportado a " + archivoSalida);
            } else {
                System.out.println(resultado);
            }

        } catch (Exception e) {
            // Si ocurre un error, se registra en el log
            registrarError("ControladorTarifa", e.getMessage());
            System.out.println("Error al procesar tarifas.");
        }
    }

    //Este método registra los errores en un archivo de log.

    private void registrarError(String clase, String mensaje) {
        try (FileWriter fw = new FileWriter(archivoLog, true)) {
            fw.write(new ModeloErrores(clase, mensaje).toString() + "\n");
        } catch (IOException ex) {
            System.err.println("Error al registrar auditoría: " + ex.getMessage());
        }
    }
}   