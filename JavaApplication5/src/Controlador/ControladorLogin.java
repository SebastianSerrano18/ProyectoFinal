/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ModeloErrores;
import Modelo.ModeloLogin;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ControladorLogin {
   
    
    //Metodo de validacion de datos
    public boolean validarCredenciales(ModeloLogin modeloLogin, int intento) {
    boolean credencialesValidas = false;
        //Validación de datos
        //Intento de lectura del archivo txt
        try (BufferedReader br = new BufferedReader(new FileReader("src/usuarios.txt"))) {
            //Variable para el almacenamiento temporal de las credenciales
            String linea;
            //Bucle para la lectura de lineas
            while ((linea = br.readLine()) != null) {
                //Array para el almacenamiento de las credenciales
                String[] partes = linea.split(",");
                //Evaluacion de la cantidad de partes de las credenciales 
                if (partes.length == 2) {
                    //Variables que almacenan las credenciales
                    String usuario = partes[0].trim();
                    String clave = partes[1].trim();
                    //Evalución de las credenciales ingresadas con las almacenadas
                    if (usuario.equals(modeloLogin.getUsuario()) && clave.equals(modeloLogin.getClave())) {
                             credencialesValidas = true;
                      break; // ¡Credencial encontrada!
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error en la lectura del archivo txt.");
    }

    // Si no se encontró credencial válida
    if (!credencialesValidas) {
        System.out.println("Usuario o clave incorrectos. Intento número: " + intento + ".");
        if (intento == 3) {
            System.out.println("----------------------");
            System.out.println("Intentos excedidos");
            System.out.println("Sesión suspendida!!");
            System.out.println("----------------------");
        }
    }

    return credencialesValidas;
}
}