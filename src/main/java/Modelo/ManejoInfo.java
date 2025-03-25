package Modelo;

import java.util.ArrayList;
import java.util.List;

import Controlador.Configuraciones;
import Controlador.Timer;
import Controlador.Partida;

/**
 * Clase en la que se guarda la informacion leida de varias fuentes.
 * @author Esteban Cortes
 */
public class ManejoInfo {
    //--------------------------------------------------------------------------
    //Configuraciones
    private static Configuraciones configuracion = new Configuraciones();
    
    /**
     * Getter del atributo configuracion
     * @return Atributo configuracion.
     */
    public static Configuraciones getConfiguracion()
    {
        return configuracion;
    }
    
    /**
     * Pone una dificultad en especifico dependiendo del indice.
     * @param indice Indice por el cual se elegira la dificultad.
     */
    public static void agregarDificultadConfiguracion(int indice)
    {
        configuracion.setDificultadPorIndice(indice);
    }
    
    /**
     * Pone un reloj en especifico dependiendo del indice.
     * @param indice Indice por el cual se elegira la dificultad.
     */
    public static void agregarRelojConfiguracion(int indice)
    {
        configuracion.setRelojPorIndice(indice);
    }
    
    /**
     * Pone la posicion del panel dependiendo del indice.
     * @param indice Indice por el cual se elegira la posicion del panel.
     */
    public static void agregarPosicionDePanelConfiguracion(int indice)
    {
        configuracion.setPosicionDelPanelPorIndice(indice);
    }
    
    /**
     * Pone o no sonido al final del juego dependiendo del indice.
     * @param indice Indice por el cual se elegira si hay sonido final o no.
     */
    public static void agregarSonidoFinalConfiguracion(int indice)
    {
        configuracion.setSonidoFinalPorIndice(indice);
    }
    //--------------------------------------------------------------------------
    //Timer
    private static Timer timer = new Timer();
    
    /**
     * Getter del atributo timer.
     * @return El atributo timer.
     */
    public static Timer getTimer()
    {
        return timer;
    }
    
    /**
     * Agrega una cantidad de horas al timer.
     * @param horas La cantidad de horas a insertar.
     */
    public static void agregarHorasTimer(int horas)
    {
        timer.setHoras(horas);
    }
    
    /**
     * Agrega una cantidad de minutos al timer.
     * @param minutos La cantidad de minutos a insertar.
     */
    public static void agregarMinutosTimer(int minutos)
    {
        timer.setMinutos(minutos);
    }
    
    /**
     * Agrega una cantidad de segundos al timer.
     * @param segundos La cantidad de segundos a insertar.
     */
    public static void agregarSegundosTimer(int segundos)
    {
        timer.setSegundos(segundos);
    }
    
    /**
     * Quita las horas del timer asignando su valor a -1.
     */
    public static void quitarHorasTimer()
    {
        timer.setHoras(-1);
    }
    
    /**
     * Quita los minutos del timer asignando su valor a -1.
     */
    public static void quitarMinutosTimer()
    {
        timer.setMinutos(-1);
    }
    
    /**
     * Quita los segundos del timer asignando su valor a -1.
     */
    public static void quitarSegundosTimer()
    {
        timer.setSegundos(-1);
    }
    
    //--------------------------------------------------------------------------
    //Partida
    private static List<Partida> listaPartidas = new ArrayList<>();
    
    /**
     * Getter del atributo listaPartidas.
     * @return La lista de partidas registradas.
     */
    public static List<Partida> getListaPartidas()
    {
        return listaPartidas;
    }
    
    /**
     * Se agrega una partida a la lista de partidas.
     * @param partida La partida a insertar.
     */
    public static void agregarPartida(Partida partida)
    {
        listaPartidas.add(partida);
    }
}
