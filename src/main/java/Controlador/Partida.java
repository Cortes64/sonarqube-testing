package Controlador;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase en la que se guardan las partidas registradas
 * @author Esteban Cortes
 */
public class Partida {
    private String dificultad;
    private List<Celdas> celdas = new ArrayList<>();
    
    /**
     * Setter del atributo dificultad
     * @param nDificultad El nuevo valor del atributo dificultad
     */
    public void setDificultad(String nDificultad)
    {
        dificultad = nDificultad;
    }
    
    /**
     * Getter del atributo dificultad
     * @return el atributo dificultad
     */
    public String getDificultad()
    {
        return dificultad;
    }
    
    /**
     * Getter del atributo celdas
     * @return el atributo celdas
     */
    public List<Celdas> getCeldas()
    {
        return celdas;
    }
    
    /**
     * Agrega una celda a la lista de celdas
     * @param celda La celda a insertar
     */
    public void agregarCelda(Celdas celda)
    {
        celdas.add(celda);
    }
    
    /**
     * El atributo ToString
     * @return la informacion de la partida
     */
    @Override
    public String toString()
    {
        int contador = 1;
        String cell = "";
        for (Celdas celda : celdas)
        {
            cell += "\nCelda " + contador + ":";
            cell += celda;
            contador++;
        }
        return "\nDificultad: " + getDificultad() + cell;
    }
}
