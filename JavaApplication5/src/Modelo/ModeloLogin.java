/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ModeloLogin {
   // Atributos que guardan el nombre de usuario y la clave
    private String usuario;
    private String clave;

    // Constructor para inicializar el usuario y la clave.
    public ModeloLogin(String usuario, String clave) {
        this.setUsuario(usuario);
        this.setClave(clave);
    }
    
    //Asigna un valor al usuario.
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    // Asigna un valor a la clave.
    public void setClave(String clave){
        this.clave = clave;
    }

    //Devuelve el nombre de usuario. 
    public String getUsuario() {
        return this.usuario;
    }

    //Devuelve la clave del usuario.
    public String getClave() {
        return this.clave;
    }
}