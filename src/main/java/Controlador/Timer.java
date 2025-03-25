package Controlador;

/**
 * Clase en la que se guardara la hora maxima del timer.
 * @author Esteban Cortes
 */
public class Timer {
    private int horas;
    private int minutos;
    private int segundos;
    
    /**
     * Constructor que inicializa a -1 todos los atirbtuos
     */
    public Timer()
    {
        horas = -1;
        minutos = -1;
        segundos = -1;
    }
    
    /**
     * Setter del atributo horas
     * @param horas nuevo valor del atributo horas
     */
    public void setHoras(int horas)
    {
        this.horas = horas;
    }
    
    /**
     * Setter del atributo minutos
     * @param minutos nuevo valor del atributo minutos
     */
    public void setMinutos(int minutos)
    {
        this.minutos = minutos;
    }
    
    /**
     * Setter del atributo segundos
     * @param segundos nuevo valor del atributo segundos
     */
    public void setSegundos(int segundos)
    {
        this.segundos = segundos;
    }
    
    /**
     * Getter del atributo horas
     * @return el atributo horas
     */
    public int getHoras()
    {
        return horas;
    }
    
    /**
     * Getter del atributo minutos
     * @return el atributo minutos
     */
    public int getMinutos()
    {
        return minutos;
    }
    
    /**
     * Getter del atributo segundos
     * @return el atributo segundos
     */
    public int getSegundos()
    {
        return segundos;
    }
}
