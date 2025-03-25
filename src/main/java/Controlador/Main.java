package Controlador;
import Vista.Menu;

import Modelo.LeerXML;

/**
 * La clase del Main, en la que se ejecutará la interfaz
 * @author Esteban Cortes
 */
public class Main {

    /**
     * El método Main, en donde se ejecuta la interfaz
     * @param args Para ejecutar la linea de comandos.
     */
    public static void main(String[] args) {
        LeerXML.guardarDatos();
        
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
    }
}
