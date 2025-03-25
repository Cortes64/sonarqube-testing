package Controlador;

/**
 * Clase la cual guarda una serie de configuraciones provenientes del panel configurar.
 * @author Esteban Cortes
 */
public class Configuraciones 
{
    //Atributos
    private boolean[] dificultad;
    private boolean[] reloj;
    private boolean[] posicionDelPanel;
    private boolean[] sonidoFinal;
    
    //Constructor
    /**
     * Constructor que inicializa por default las listas en un valor especifico.
     */
    public Configuraciones()
    {
        dificultad = new boolean[3];
        reloj = new boolean[3];
        posicionDelPanel = new boolean[2];
        sonidoFinal = new boolean[2];
        
        dificultad[0] = true;
        dificultad[1] = false;
        dificultad[2] = false;
        
        reloj[0] = true;
        reloj[1] = false;
        reloj[2] = false;
        
        posicionDelPanel[0] = true;
        posicionDelPanel[1] = false;
        
        sonidoFinal[0] = true;
        sonidoFinal[1] = false;
    }
    
    //Setters
    /**
     * Setter del atributo dificultad.
     * @param dificultad La dificultad a insertar.
     */
    public void setDificultad(boolean[] dificultad)
    {
        this.dificultad = dificultad;
    }
    
    /**
     * Setter del atributo reloj.
     * @param reloj El reloj a insertar.
     */
    public void setReloj(boolean[] reloj)
    {
        this.reloj = reloj;
    }
    
    /**
     * Setter del atributo posicionDelPanel.
     * @param posicionDelPanel La posicion del panel a insertar.
     */
    public void setPosicionDelPanel(boolean[] posicionDelPanel)
    {
        this.posicionDelPanel = posicionDelPanel;
    }
    
    /**
     * Setter del atributo sonidoFinal.
     * @param sonidoFinal El sonido final a insertar.
     */
    public void setSonidoFinal(boolean[] sonidoFinal)
    {
        this.sonidoFinal = sonidoFinal;
    }
    
    //Getters
    /**
     * Getter del atributo dificultad
     * @return El atributo dificultad.
     */
    public boolean[] getDificultad()
    {
        return dificultad;
    }
    
    /**
     * Getter del atributo reloj.
     * @return El atributo reloj.
     */
    public boolean[] getReloj()
    {
        return reloj;
    }
    
    /**
     * Getter del atributo posicionDelPanel.
     * @return El atributo posicionDelPanel.
     */
    public boolean[] getPosicionDelPanel()
    {
        return posicionDelPanel;
    }
    
    /**
     * Getter del atributo sonidoFinal.
     * @return El atributo sonidoFinal.
     */
    public boolean[] getSonidoFinal()
    {
        return sonidoFinal;
    }
    
    //Setters por indice
    /**
     * Pone una opcion en true y el resto en false.
     * @param indice el indice que se quiere en true.
     */
    public void setDificultadPorIndice(int indice)
    {
        for (int i = 0; i < 3; i++)
        {
            if (i == indice)
            {
                dificultad[i] = true;
                continue;
            }
            dificultad[i] = false;
        }
    }
    
    /**
     * Pone una opcion en true y el resto en false.
     * @param indice el indice que se quiere en true.
     */
    public void setRelojPorIndice(int indice)
    {
        for (int i = 0; i < 3; i++)
        {
            if (i == indice)
            {
                reloj[i] = true;
                continue;
            }
            reloj[i] = false;
        }
    }
    
    /**
     * Pone una opcion en true y el resto en false.
     * @param indice el indice que se quiere en true.
     */
    public void setPosicionDelPanelPorIndice(int indice)
    {
        for (int i = 0; i < 2; i++)
        {
            if (i == indice)
            {
                posicionDelPanel[i] = true;
                continue;
            }
            posicionDelPanel[i] = false;
        }
    }
    
    /**
     * Pone una opcion en true y el resto en false.
     * @param indice el indice que se quiere en true.
     */
    public void setSonidoFinalPorIndice(int indice)
    {
        for (int i = 0; i < 2; i++)
        {
            if (i == indice)
            {
                sonidoFinal[i] = true;
                continue;
            }
            sonidoFinal[i] = false;
        }
    }
    
    /**
     * Despliega la lista Dificultad.
     */
    public void desplegarDificultad()
    {
        for (int i = 0; i < 3; i++)
        {
            System.out.println(dificultad[i]);
        }
    }
    
    /**
     * Despliega la lista posicionDelPanel.
     */
    public void desplegarPosicionDelPanel()
    {
        for (int i = 0; i < 2; i++)
        {
            System.out.println(posicionDelPanel[i]);
        }
    }
    
    /**
     * Despliega la lista reloj.
     */
    public void desplegarReloj()
    {
        for (int i = 0; i < 3; i++)
        {
            System.out.println(reloj[i]);
        }
    }
    
    /**
     * Despliega la lista sonidoFinal.
     */
    public void desplegarSonidoFinal()
    {
        for (int i = 0; i < 2; i++)
        {
            System.out.println(sonidoFinal[i]);
        }
    }
}
