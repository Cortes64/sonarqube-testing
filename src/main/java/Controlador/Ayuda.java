package Controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Clase para abrir el manual de usuario.
 * @author Esteban Cortes
 */
public class Ayuda {
    /**
     * Metodo por el cual se abre el manual de usuario.
     */
    public static void abrirManualDeUsuario(){
        try {
            String ruta = "manualDeUsuario/programa2_manual_de_usuario.pdf";
            File file = new File(ruta);
            if (file.exists()){
                if (Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(file);
                }
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
