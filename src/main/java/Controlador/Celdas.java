package Controlador;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase en la que se almacenaran las celdas del juego
 * @author Esteban Cortes
 */
public class Celdas {
    private int numeroResultado;
    private String operando;
    private List<int[]> ubicaciones = new ArrayList<>();
    
    /**
     * Constructor de la clase Celdas, inicializa los atributos con los parametros.
     * @param nNumeroResultado nuevo valor del atrubuto numeroResultado
     * @param nOperando nuevo valor del atributo operando.
     */
    public Celdas(int nNumeroResultado, String nOperando)
    {
        numeroResultado = nNumeroResultado;
        operando = nOperando;
    }
    
    /**
     * Constructor que no inicializa las variables.
     */
    public Celdas(){}
    
    /**
     * Setter del atributo numeroResultado
     * @param nNumeroResultado nuevo valor de numeroResultado
     */
    public void setNumeroResultado(int nNumeroResultado)
    {
        numeroResultado = nNumeroResultado;
    }
    
    /**
     * Setter del atributo operando
     * @param nOperando nuevo valor de operando
     */
    public void setOperando(String nOperando)
    {
        operando = nOperando;
    }
    
    /**
     * Getter del atributo numeroResultado
     * @return el atributo numeroResultado
     */
    public int getNumeroResultado()
    {
        return numeroResultado;
    }
    
    /**
     * Getter del atributo operando
     * @return el atributo operando
     */
    public String getOperando()
    {
        return operando;
    }
    
    /**
     * Getter del atributo ubicaciones
     * @return el atributo ubicaciones
     */
    public List<int[]> getUbicaciones()
    {
        return ubicaciones;
    }
    
    /**
     * Agrega una ubicacion a la lista de ubicaciones.
     * @param x la fila de la matriz
     * @param y la columna de la matriz
     */
    public void agregarUbicacion(int x, int y)
    {
        int [] coordenadas = {x, y};
        ubicaciones.add(coordenadas);
    }
    
    /**
     * Metodo toString
     * @return un String con todos los valores de la clase.
     */
    @Override
    public String toString()
    {
        String coor = "";
        for (int [] coordenadas : ubicaciones)
        {
            coor += "\ncoordenadas: (" + coordenadas[0] + ", " + coordenadas[1] + ")";
        }
        return "\nResultado: " + getNumeroResultado() +
               "\nOperador: " + getOperando() + 
               coor;
    }
}
