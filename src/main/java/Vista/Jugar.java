package Vista;

import Modelo.ManejoInfo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Controlador.Celdas;
import Controlador.Partida;

/**
 * Panel en el que el usuario juega a KenKen
 * @author Esteban Cortes
 */
public class Jugar extends javax.swing.JFrame {
    
    private Timer timer1;
    private Timer timer2;
    private javax.swing.JToggleButton[][] listaBotones;
    private char[][] listaResultados;
    
    private int numeroPrincipal;
    private String operador;
    private Partida partida;
    
    private List<String> pilaDeshacerJugada = new ArrayList<>();
    private List<String> pilaRehacerJugada = new ArrayList<>();

    /**
     * Crea el frame con sus caracteristicas para que se pueda jugar
     */
    public Jugar() {
        super("Juego");
        initComponents();
        
        listaResultados = new char[6][6];
        
        timer1 = new Timer(1000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int horasCrono = Integer.parseInt(PaneHorasCrono.getText());
                    int minutosCrono = Integer.parseInt(PaneMinutosCrono.getText());
                    int segundosCrono = Integer.parseInt(PaneSegundosCrono.getText());
                    segundosCrono += 1;
                    
                    if (segundosCrono == 60)
                    {
                        segundosCrono = 0;
                        minutosCrono += 1;
                        if (minutosCrono == 60)
                        {
                            horasCrono += 1;
                            minutosCrono = 0;
                        }
                    }
                    
                    String horasCronoString = String.valueOf(horasCrono);
                    String minutosCronoString = String.valueOf(minutosCrono);
                    String segundosCronoString = String.valueOf(segundosCrono);
                    
                    PaneHorasCrono.setText(horasCronoString);
                    PaneMinutosCrono.setText(minutosCronoString);
                    PaneSegundosCrono.setText(segundosCronoString);
                }
            });
        
        
        this.LabelHorasCrono.setVisible(false);
        this.LabelMinutosCrono.setVisible(false);
        this.LabelSegundosCrono.setVisible(false);
        this.PaneHorasCrono.setVisible(false);
        this.PaneMinutosCrono.setVisible(false);
        this.PaneSegundosCrono.setVisible(false);
        this.PanelCrono.setVisible(false);
        this.textoCrono.setVisible(false);
        this.PanelKenKen.setVisible(false);
        
        //Botones
        this.BotonBorrador.setVisible(false);
        this.BotonNum1.setVisible(false);
        this.BotonNum2.setVisible(false);
        this.BotonNum3.setVisible(false);
        this.BotonNum4.setVisible(false);
        this.BotonNum5.setVisible(false);
        this.BotonNum6.setVisible(false);
        
        this.botonBorrador.setVisible(false);
        this.botonNum1.setVisible(false);
        this.botonNum2.setVisible(false);
        this.botonNum3.setVisible(false);
        this.botonNum4.setVisible(false);
        this.botonNum5.setVisible(false);
        this.botonNum6.setVisible(false);
        
        Partida partida = elegirPartida();
        this.partida = partida;
        agregarBotonesALista();
        this.botonDeshacerJugada.setEnabled(false);
        this.botonRehacerJugada.setEnabled(false);
        this.botonValidarJuego.setEnabled(false);
        
        
        //Recorre cada boton.
        for (int i = 0; i < listaBotones.length; i++)
        {
            for (int j = 0; j < listaBotones[i].length; j++)
            {
                listaResultados[i][j] = ' ';
                listaBotones[i][j].setBackground(Color.white);
                Celdas esCelda = new Celdas();
                //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
                for (Celdas celda : partida.getCeldas())
                {
                    if (ubicacionEnLista(i+1, j+1, celda))
                    {
                        esCelda = celda;
                        break;
                    } 
                    int[] listaCoordenadas = {i+1, j+1};
                }
                if (esCelda.getUbicaciones().get(0)[0] == i+1 && esCelda.getUbicaciones().get(0)[1] == j+1)
                    {
                        numeroPrincipal = esCelda.getNumeroResultado();
                        operador = esCelda.getOperando();
                        if (operador == "c")
                        {
                            listaBotones[i][j].setText("<html><center>" + numeroPrincipal + "</html>");
                        }
                        else
                        {
                            listaBotones[i][j].setText("<html><center>" + numeroPrincipal + operador + "</html>");
                        }
                    }
                
                //Ya se tiene la celda, ahora se modifican los bordes en funcion de la celda
                
                int arriba = 2;
                int izquierda = 2;
                int abajo = 2;
                int derecha = 2;
                
                if (ubicacionEnLista(i, j+1, esCelda))
                {
                    //Si comparte celda arriba
                    arriba = 0;
                } if (ubicacionEnLista(i+1, j, esCelda))
                {
                    //Si comparte celda a la izquierda
                    izquierda = 0;
                } if (ubicacionEnLista(i+2, j+1, esCelda))
                {
                    //Si comparte celda abajo
                    abajo = 0;
                } if (ubicacionEnLista(i+1, j+2, esCelda))
                {
                    //Si comparte celda a la derecha
                    derecha = 0;
                }
                
                listaBotones[i][j].setBorder(BorderFactory.createMatteBorder(arriba, izquierda, abajo, derecha, Color.BLACK));
            }
        }
    }
    
    /**
     * Busca si las coordenadas (x, y) estan en la celda
     * @param x la fila
     * @param y la columna
     * @param celda la celda en la cual se busca las coordenadas.
     * @return retorna true si se encontro, false sino.
     */
    private boolean ubicacionEnLista(int x, int y, Celdas celda)
    {
        for (int [] ubicacion : celda.getUbicaciones())
        {
            int x2 = ubicacion[0];
            int y2 = ubicacion[1];
            
            if (x2 == x && y2 == y)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Agrega los botones a la listaBotones como matriz
     */
    private void agregarBotonesALista()
    {
        listaBotones = new javax.swing.JToggleButton[6][6];
        
        listaBotones[0][0] = kenken11;
        listaBotones[0][1] = kenken12;
        listaBotones[0][2] = kenken13;
        listaBotones[0][3] = kenken14;
        listaBotones[0][4] = kenken15;
        listaBotones[0][5] = kenken16;
        
        listaBotones[1][0] = kenken21;
        listaBotones[1][1] = kenken22;
        listaBotones[1][2] = kenken23;
        listaBotones[1][3] = kenken24;
        listaBotones[1][4] = kenken25;
        listaBotones[1][5] = kenken26;
        
        listaBotones[2][0] = kenken31;
        listaBotones[2][1] = kenken32;
        listaBotones[2][2] = kenken33;
        listaBotones[2][3] = kenken34;
        listaBotones[2][4] = kenken35;
        listaBotones[2][5] = kenken36;
        
        listaBotones[3][0] = kenken41;
        listaBotones[3][1] = kenken42;
        listaBotones[3][2] = kenken43;
        listaBotones[3][3] = kenken44;
        listaBotones[3][4] = kenken45;
        listaBotones[3][5] = kenken46;
        
        listaBotones[4][0] = kenken51;
        listaBotones[4][1] = kenken52;
        listaBotones[4][2] = kenken53;
        listaBotones[4][3] = kenken54;
        listaBotones[4][4] = kenken55;
        listaBotones[4][5] = kenken56;
        
        listaBotones[5][0] = kenken61;
        listaBotones[5][1] = kenken62;
        listaBotones[5][2] = kenken63;
        listaBotones[5][3] = kenken64;
        listaBotones[5][4] = kenken65;
        listaBotones[5][5] = kenken66;
    }
    
    /**
     * Inicializa el timer2, solo cuando la configuracion lo permita.
     */
    private void inicializarTimer2()
    {
        timer2 = new Timer(1000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int horasCrono = Integer.parseInt(PaneHorasCrono.getText());
                    int minutosCrono = Integer.parseInt(PaneMinutosCrono.getText());
                    int segundosCrono = Integer.parseInt(PaneSegundosCrono.getText());
                    segundosCrono -= 1;
                    
                    if (segundosCrono == -1)
                    {
                        segundosCrono = 59;
                        minutosCrono -= 1;
                        if (minutosCrono == -1)
                        {
                            horasCrono -= 1;
                            minutosCrono = 59;
                        }
                    }
                    
                    if (horasCrono == 0 && minutosCrono == 0 && segundosCrono == 0)
                    {
                        verificarTimer();
                        timer2.stop();
                        return;
                    }
                    
                    String horasCronoString = String.valueOf(horasCrono);
                    String minutosCronoString = String.valueOf(minutosCrono);
                    String segundosCronoString = String.valueOf(segundosCrono);
                    
                    PaneHorasCrono.setText(horasCronoString);
                    PaneMinutosCrono.setText(minutosCronoString);
                    PaneSegundosCrono.setText(segundosCronoString);
                }
            });
    }
    
    /**
     * Verifica si el usuario quiere salir o no del juego cuando se termino el timepo del timer
     */
    private void verificarTimer()
    {
        
        int verificacion = JOptionPane.showConfirmDialog(null, "TIEMPO EXPIRADO. ¿DESEA CONTINUAR EL MISMO JUEGO?");
        
        if (verificacion == 1)
        {
            Menu menu = new Menu();
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
            this.setVisible(false);
            this.dispose();
            
        } else
        {
            PaneHorasCrono.setText(ManejoInfo.getTimer().getHoras()+"");
            PaneMinutosCrono.setText(ManejoInfo.getTimer().getMinutos()+"");
            PaneSegundosCrono.setText(ManejoInfo.getTimer().getSegundos()+"");
            timer1.start();
        }
    }
    
    /**
     * Elige la partida que se jugara al azar (dependiendo de la dificultad).
     * @return La partida que se jugara.
     */
    private Partida elegirPartida()
    {
        if (ManejoInfo.getConfiguracion().getDificultad()[0] == true)
        {
            List<Partida> partidasFaciles = new ArrayList<>();
            for (Partida partida : ManejoInfo.getListaPartidas())
            {
                if (partida.getDificultad().equals("facil"))
                {
                    partidasFaciles.add(partida);
                }
            }
            Random rand = new Random();
            Partida partida = partidasFaciles.get(rand.nextInt(partidasFaciles.size()));
            
            return partida;
            
        } else if (ManejoInfo.getConfiguracion().getDificultad()[1] == true)
        {
            List<Partida> partidasMedio = new ArrayList<>();
            for (Partida partida : ManejoInfo.getListaPartidas())
            {
                if (partida.getDificultad().equals("intermedio"))
                {
                    partidasMedio.add(partida);
                }
            }
            Random rand = new Random();
            Partida partida = partidasMedio.get(rand.nextInt(partidasMedio.size()));
            
            return partida;
            
        } else
        {
            List<Partida> partidasDificiles = new ArrayList<>();
            for (Partida partida : ManejoInfo.getListaPartidas())
            {
                if (partida.getDificultad().equals("dificil"))
                {
                    partidasDificiles.add(partida);
                }
            }
            Random rand = new Random();
            Partida partida = partidasDificiles.get(rand.nextInt(partidasDificiles.size()));
            
            return partida;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        grupoKenKen = new javax.swing.ButtonGroup();
        grupoNumeros = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        botonIniciarJuego = new javax.swing.JButton();
        botonDeshacerJugada = new javax.swing.JButton();
        botonTerminarJuego = new javax.swing.JButton();
        botonOtroJuego = new javax.swing.JButton();
        botonRehacerJugada = new javax.swing.JButton();
        botonValidarJuego = new javax.swing.JButton();
        botonReiniciarJuego = new javax.swing.JButton();
        PanelCrono = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PaneHorasCrono = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        PaneMinutosCrono = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        PaneSegundosCrono = new javax.swing.JTextPane();
        LabelHorasCrono = new javax.swing.JLabel();
        LabelMinutosCrono = new javax.swing.JLabel();
        LabelSegundosCrono = new javax.swing.JLabel();
        textoCrono = new java.awt.Label();
        PanelKenKen = new javax.swing.JPanel();
        kenken11 = new javax.swing.JToggleButton();
        kenken12 = new javax.swing.JToggleButton();
        kenken13 = new javax.swing.JToggleButton();
        kenken14 = new javax.swing.JToggleButton();
        kenken15 = new javax.swing.JToggleButton();
        kenken16 = new javax.swing.JToggleButton();
        kenken21 = new javax.swing.JToggleButton();
        kenken22 = new javax.swing.JToggleButton();
        kenken23 = new javax.swing.JToggleButton();
        kenken24 = new javax.swing.JToggleButton();
        kenken25 = new javax.swing.JToggleButton();
        kenken26 = new javax.swing.JToggleButton();
        kenken31 = new javax.swing.JToggleButton();
        kenken32 = new javax.swing.JToggleButton();
        kenken33 = new javax.swing.JToggleButton();
        kenken34 = new javax.swing.JToggleButton();
        kenken35 = new javax.swing.JToggleButton();
        kenken36 = new javax.swing.JToggleButton();
        kenken41 = new javax.swing.JToggleButton();
        kenken42 = new javax.swing.JToggleButton();
        kenken43 = new javax.swing.JToggleButton();
        kenken44 = new javax.swing.JToggleButton();
        kenken45 = new javax.swing.JToggleButton();
        kenken46 = new javax.swing.JToggleButton();
        kenken51 = new javax.swing.JToggleButton();
        kenken52 = new javax.swing.JToggleButton();
        kenken53 = new javax.swing.JToggleButton();
        kenken54 = new javax.swing.JToggleButton();
        kenken55 = new javax.swing.JToggleButton();
        kenken56 = new javax.swing.JToggleButton();
        kenken61 = new javax.swing.JToggleButton();
        kenken62 = new javax.swing.JToggleButton();
        kenken63 = new javax.swing.JToggleButton();
        kenken64 = new javax.swing.JToggleButton();
        kenken65 = new javax.swing.JToggleButton();
        kenken66 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        BotonNum1 = new javax.swing.JToggleButton();
        BotonNum2 = new javax.swing.JToggleButton();
        BotonNum3 = new javax.swing.JToggleButton();
        BotonNum4 = new javax.swing.JToggleButton();
        BotonNum5 = new javax.swing.JToggleButton();
        BotonNum6 = new javax.swing.JToggleButton();
        BotonBorrador = new javax.swing.JToggleButton();
        botonNum1 = new javax.swing.JToggleButton();
        botonNum4 = new javax.swing.JToggleButton();
        botonNum2 = new javax.swing.JToggleButton();
        botonNum3 = new javax.swing.JToggleButton();
        botonNum5 = new javax.swing.JToggleButton();
        botonNum6 = new javax.swing.JToggleButton();
        botonBorrador = new javax.swing.JToggleButton();

        jPanel2.setBackground(new java.awt.Color(51, 204, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 204, 0));

        botonIniciarJuego.setBackground(new java.awt.Color(255, 0, 0));
        botonIniciarJuego.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonIniciarJuego.setForeground(new java.awt.Color(255, 255, 255));
        botonIniciarJuego.setText("<html><center>Iniciar Juego</html>");
        botonIniciarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarJuegoActionPerformed(evt);
            }
        });

        botonDeshacerJugada.setBackground(new java.awt.Color(255, 0, 0));
        botonDeshacerJugada.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonDeshacerJugada.setForeground(new java.awt.Color(255, 255, 255));
        botonDeshacerJugada.setText("<html><center>Deshacer Jugada</html>");
        botonDeshacerJugada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDeshacerJugadaActionPerformed(evt);
            }
        });

        botonTerminarJuego.setBackground(new java.awt.Color(255, 0, 0));
        botonTerminarJuego.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonTerminarJuego.setForeground(new java.awt.Color(255, 255, 255));
        botonTerminarJuego.setText("<html><center>Terminar Juego</html>");
        botonTerminarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTerminarJuegoActionPerformed(evt);
            }
        });

        botonOtroJuego.setBackground(new java.awt.Color(255, 0, 0));
        botonOtroJuego.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonOtroJuego.setForeground(new java.awt.Color(255, 255, 255));
        botonOtroJuego.setText("<html><center>Otro Juego</html>");
        botonOtroJuego.setEnabled(false);
        botonOtroJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOtroJuegoActionPerformed(evt);
            }
        });

        botonRehacerJugada.setBackground(new java.awt.Color(255, 0, 0));
        botonRehacerJugada.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonRehacerJugada.setForeground(new java.awt.Color(255, 255, 255));
        botonRehacerJugada.setText("<html><center>Rehacer Jugada</html>");
        botonRehacerJugada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRehacerJugadaActionPerformed(evt);
            }
        });

        botonValidarJuego.setBackground(new java.awt.Color(255, 0, 0));
        botonValidarJuego.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonValidarJuego.setForeground(new java.awt.Color(255, 255, 255));
        botonValidarJuego.setText("<html><center>Validar Juego</html>");
        botonValidarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonValidarJuegoActionPerformed(evt);
            }
        });

        botonReiniciarJuego.setBackground(new java.awt.Color(255, 0, 0));
        botonReiniciarJuego.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        botonReiniciarJuego.setForeground(new java.awt.Color(255, 255, 255));
        botonReiniciarJuego.setText("<html><center>Reiniciar Juego</html>");
        botonReiniciarJuego.setEnabled(false);
        botonReiniciarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarJuegoActionPerformed(evt);
            }
        });

        PaneHorasCrono.setEditable(false);
        PaneHorasCrono.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(PaneHorasCrono);

        PaneMinutosCrono.setEditable(false);
        PaneMinutosCrono.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 24)); // NOI18N
        jScrollPane4.setViewportView(PaneMinutosCrono);

        PaneSegundosCrono.setEditable(false);
        PaneSegundosCrono.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 24)); // NOI18N
        jScrollPane3.setViewportView(PaneSegundosCrono);

        LabelHorasCrono.setText("Horas");

        LabelMinutosCrono.setText("Minutos");

        LabelSegundosCrono.setText("Segundos");

        javax.swing.GroupLayout PanelCronoLayout = new javax.swing.GroupLayout(PanelCrono);
        PanelCrono.setLayout(PanelCronoLayout);
        PanelCronoLayout.setHorizontalGroup(
            PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCronoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelCronoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(LabelHorasCrono)))
                .addGap(18, 18, 18)
                .addGroup(PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCronoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelCronoLayout.createSequentialGroup()
                        .addComponent(LabelMinutosCrono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelSegundosCrono)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelCronoLayout.setVerticalGroup(
            PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCronoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelHorasCrono)
                    .addComponent(LabelMinutosCrono)
                    .addComponent(LabelSegundosCrono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelCronoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addContainerGap())
        );

        textoCrono.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 12)); // NOI18N
        textoCrono.setForeground(new java.awt.Color(255, 255, 255));
        textoCrono.setText("Cronometro");

        grupoKenKen.add(kenken11);
        kenken11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken11ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken12);
        kenken12.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken12ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken13);
        kenken13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken13ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken14);
        kenken14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken14ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken15);
        kenken15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken15ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken16);
        kenken16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken16ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken21);
        kenken21.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken21ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken22);
        kenken22.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken22ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken23);
        kenken23.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken23ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken24);
        kenken24.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken24ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken25);
        kenken25.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken25ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken26);
        kenken26.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken26ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken31);
        kenken31.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken31ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken32);
        kenken32.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken32ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken33);
        kenken33.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken33ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken34);
        kenken34.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken34ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken35);
        kenken35.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken35ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken36);
        kenken36.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken36ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken41);
        kenken41.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken41ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken42);
        kenken42.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken42ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken43);
        kenken43.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken43ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken44);
        kenken44.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken44ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken45);
        kenken45.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken45ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken46);
        kenken46.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken46ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken51);
        kenken51.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken51ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken52);
        kenken52.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken52ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken53);
        kenken53.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken53ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken54);
        kenken54.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken54ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken55);
        kenken55.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken55ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken56);
        kenken56.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken56ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken61);
        kenken61.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken61ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken62);
        kenken62.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken62ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken63);
        kenken63.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken63ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken64);
        kenken64.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken64ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken65);
        kenken65.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken65ActionPerformed(evt);
            }
        });

        grupoKenKen.add(kenken66);
        kenken66.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kenken66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kenken66ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelKenKenLayout = new javax.swing.GroupLayout(PanelKenKen);
        PanelKenKen.setLayout(PanelKenKenLayout);
        PanelKenKenLayout.setHorizontalGroup(
            PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKenKenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(kenken61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken12, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken13, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken14, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken15, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken16, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelKenKenLayout.setVerticalGroup(
            PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKenKenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kenken11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kenken12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kenken13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken21, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken31, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken41, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken51, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(PanelKenKenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kenken61, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(kenken62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kenken66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("UD Digi Kyokasho N-B", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KENKEN");

        grupoNumeros.add(BotonNum1);
        BotonNum1.setText("1");
        BotonNum1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum1ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonNum2);
        BotonNum2.setText("2");
        BotonNum2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum2ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonNum3);
        BotonNum3.setText("3");
        BotonNum3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum3ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonNum4);
        BotonNum4.setText("4");
        BotonNum4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum4ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonNum5);
        BotonNum5.setText("5");
        BotonNum5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum5ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonNum6);
        BotonNum6.setText("6");
        BotonNum6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNum6ActionPerformed(evt);
            }
        });

        grupoNumeros.add(BotonBorrador);
        BotonBorrador.setText("Borrar");
        BotonBorrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBorradorActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum1);
        botonNum1.setText("1");
        botonNum1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum1ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum4);
        botonNum4.setText("4");
        botonNum4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum4ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum2);
        botonNum2.setText("2");
        botonNum2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum2ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum3);
        botonNum3.setText("3");
        botonNum3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum3ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum5);
        botonNum5.setText("5");
        botonNum5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum5ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonNum6);
        botonNum6.setText("6");
        botonNum6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNum6ActionPerformed(evt);
            }
        });

        grupoNumeros.add(botonBorrador);
        botonBorrador.setText("Borrar");
        botonBorrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(botonValidarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonRehacerJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonReiniciarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(botonIniciarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonDeshacerJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonTerminarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonOtroJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(224, 224, 224)
                .addComponent(textoCrono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botonNum2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonNum5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonNum3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonNum6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonNum1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonNum4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(botonBorrador, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelKenKen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BotonNum3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BotonNum6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(BotonBorrador, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BotonNum2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BotonNum5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BotonNum1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BotonNum4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(PanelCrono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(96, 96, 96))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(PanelKenKen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoCrono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PanelCrono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BotonNum1)
                                    .addComponent(BotonNum4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BotonNum2)
                                    .addComponent(BotonNum5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BotonNum3)
                                    .addComponent(BotonNum6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonBorrador)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonIniciarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonDeshacerJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonTerminarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonOtroJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonRehacerJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonValidarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonReiniciarJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonNum1)
                            .addComponent(botonNum4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonNum2)
                            .addComponent(botonNum5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonNum6)
                            .addComponent(botonNum3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBorrador)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Hace un push a la pila deshacerJugada
     * @param cual atributo que se inserta a la lista deshacerJugada
     */
    private void pushDeshacer(String cual)
    {
        pilaDeshacerJugada.add(0, cual);
    }
    
    /**
     * Hace un pop a la pila deshacerJugada
     * @return el elemento que salio de la pila.
     */
    private String popDeshacer()
    {
        String top = pilaDeshacerJugada.get(0);
        pilaDeshacerJugada.remove(0);
        return top;
    }
    
    /**
     * Busca el top de la pila para retornarlo.
     * @return el top de la pila
     */
    private String topDeshacer(){
        String top = pilaDeshacerJugada.get(0);
        return top;
    }
    
    /**
     * Hace un push a la pila rehacerJugada
     * @param cual el elemento a insertar
     */
    private void pushRehacer(String cual)
    {
        pilaRehacerJugada.add(0, cual);
    }
    
    /**
     * Hace un pop a la pila rehacerJugada
     * @return el elemento sacado de la pila.
     */
    private String popRehacer()
    {
        String top = pilaRehacerJugada.get(0);
        pilaRehacerJugada.remove(0);
        return top;
    }
    
    private void botonDeshacerJugadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDeshacerJugadaActionPerformed
        String top = popDeshacer();
        pushRehacer(top);
        
        botonRehacerJugada.setEnabled(true);
        
        String operacion = top.substring(0, 5);
        String coordenadas = top.substring(6, 8);
        
        
        if (operacion.equals("PONER"))
        {
            if (this.pilaDeshacerJugada.size() > 1){
                String topAnterior = topDeshacer();
                
                String operacionAnterior = topAnterior.substring(0, 5);
                String coordenadasAnteriores = topAnterior.substring(6, 8);
                
                if (operacionAnterior.equals("PONER") && coordenadasAnteriores.equals(coordenadas)){
                    String x = coordenadas.substring(0, 1);
                    String y = coordenadas.substring(1);
            
                    int x1 = Integer.parseInt(x);
                    int y1 = Integer.parseInt(y);
                    
                    String numero = topAnterior.substring(9);
                    char charNumero = numero.charAt(0);
                    
                    listaBotones[x1][y1].setSelected(true);
                    activarNumeroBotonPilas(charNumero, x1, y1);
                    return;
                }
            }
            
            String x = coordenadas.substring(0, 1);
            String y = coordenadas.substring(1);
            
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(y);
            
            listaBotones[x1][y1].setSelected(true);
            activarNumeroBotonPilas(' ', x1, y1);
        } else
        {
            String x = coordenadas.substring(0, 1);
            String y = coordenadas.substring(1);
            
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(y);
            
            listaBotones[x1][y1].setSelected(true);
            
            String numero = top.substring(9);
            char charNumero = numero.charAt(0);
            activarNumeroBotonPilas(charNumero, x1, y1);
        }
        if (this.pilaDeshacerJugada.isEmpty())
        {
            botonDeshacerJugada.setEnabled(false);
        }
    }//GEN-LAST:event_botonDeshacerJugadaActionPerformed

    private void botonOtroJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOtroJuegoActionPerformed
        if (timer1.isRunning()){timer1.stop();}
        if (timer2 != null){
            if (timer2.isRunning()){timer2.stop();}
        }
        int verificacion = JOptionPane.showConfirmDialog(null, "Seguro que quieres reiniciar la partida?");
        
        if (verificacion == 0)
        {
            boolean otraPartida = false;
            Partida mismaPartida = partida;
            while (!otraPartida){
                this.partida = this.elegirPartida();
                if (partida != mismaPartida){otraPartida = true;}
            }
            
            this.PaneHorasCrono.setText("0");
            this.PaneMinutosCrono.setText("0");
            this.PaneSegundosCrono.setText("0");
            if (ManejoInfo.getConfiguracion().getReloj()[0]){
                timer1.restart();
            } else if (ManejoInfo.getConfiguracion().getReloj()[1]){
                timer2.restart();
            }
            for (int i = 0; i < listaBotones.length; i++)
            {
                for (int j = 0; j < listaBotones[i].length; j++)
                {
                    listaResultados[i][j] = ' ';
                    listaBotones[i][j].setText("");
                    Celdas esCelda = new Celdas();
                    //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
                    for (Celdas celda : partida.getCeldas())
                    {
                        if (ubicacionEnLista(i+1, j+1, celda))
                        {
                            esCelda = celda;
                            break;
                        } 
                        int[] listaCoordenadas = {i+1, j+1};
                    }
                    if (esCelda.getUbicaciones().get(0)[0] == i+1 && esCelda.getUbicaciones().get(0)[1] == j+1)
                        {
                            numeroPrincipal = esCelda.getNumeroResultado();
                            operador = esCelda.getOperando();
                            if (operador == "c")
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + "</html>");
                            }
                            else
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + operador + "</html>");
                            }
                        }

                    //Ya se tiene la celda, ahora se modifican los bordes en funcion de la celda

                    int arriba = 2;
                    int izquierda = 2;
                    int abajo = 2;
                    int derecha = 2;

                    if (ubicacionEnLista(i, j+1, esCelda))
                    {
                        //Si comparte celda arriba
                        arriba = 0;
                    } if (ubicacionEnLista(i+1, j, esCelda))
                    {
                        //Si comparte celda a la izquierda
                        izquierda = 0;
                    } if (ubicacionEnLista(i+2, j+1, esCelda))
                    {
                        //Si comparte celda abajo
                        abajo = 0;
                    } if (ubicacionEnLista(i+1, j+2, esCelda))
                    {
                        //Si comparte celda a la derecha
                        derecha = 0;
                    }

                    listaBotones[i][j].setBorder(BorderFactory.createMatteBorder(arriba, izquierda, abajo, derecha, Color.BLACK));
                }
            }
        }
    }//GEN-LAST:event_botonOtroJuegoActionPerformed

    private void botonIniciarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarJuegoActionPerformed
        botonIniciarJuego.setEnabled(false);
        botonReiniciarJuego.setEnabled(true);
        botonOtroJuego.setEnabled(true);
        botonValidarJuego.setEnabled(true);
        PanelKenKen.setVisible(true);
        
        if (ManejoInfo.getConfiguracion().getPosicionDelPanel()[1]){
            this.BotonBorrador.setVisible(true);
            this.BotonNum1.setVisible(true);
            this.BotonNum2.setVisible(true);
            this.BotonNum3.setVisible(true);
            this.BotonNum4.setVisible(true);
            this.BotonNum5.setVisible(true);
            this.BotonNum6.setVisible(true);
        } else {
            this.botonBorrador.setVisible(true);
            this.botonNum1.setVisible(true);
            this.botonNum2.setVisible(true);
            this.botonNum3.setVisible(true);
            this.botonNum4.setVisible(true);
            this.botonNum5.setVisible(true);
            this.botonNum6.setVisible(true);
        }
        
        if (ManejoInfo.getConfiguracion().getReloj()[0] == true)
        {
            this.LabelHorasCrono.setVisible(true);
            this.LabelMinutosCrono.setVisible(true);
            this.LabelSegundosCrono.setVisible(true);
            this.PaneHorasCrono.setVisible(true);
            this.PaneMinutosCrono.setVisible(true);
            this.PaneSegundosCrono.setVisible(true);
            this.PanelCrono.setVisible(true);
            this.textoCrono.setVisible(true);
            
            
            textoCrono.setText("Cronometro");
            PaneHorasCrono.setText("0");
            PaneMinutosCrono.setText("0");
            PaneSegundosCrono.setText("0");
            
            timer1.start();
        } else if (ManejoInfo.getConfiguracion().getReloj()[1] == true)
        {
            this.LabelHorasCrono.setVisible(true);
            this.LabelMinutosCrono.setVisible(true);
            this.LabelSegundosCrono.setVisible(true);
            this.PaneHorasCrono.setVisible(true);
            this.PaneMinutosCrono.setVisible(true);
            this.PaneSegundosCrono.setVisible(true);
            this.PanelCrono.setVisible(true);
            this.textoCrono.setVisible(true);
            
            
            textoCrono.setText("Timer");
            PaneHorasCrono.setText(ManejoInfo.getTimer().getHoras()+"");
            PaneMinutosCrono.setText(ManejoInfo.getTimer().getMinutos()+"");
            PaneSegundosCrono.setText(ManejoInfo.getTimer().getSegundos()+"");
            
            inicializarTimer2();
            timer2.start();
        }
    }//GEN-LAST:event_botonIniciarJuegoActionPerformed

    
    private void botonTerminarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTerminarJuegoActionPerformed
        // TODO add your handling code here:
        
        boolean cronometro = false;
        boolean timer = false;
        if (timer1.isRunning())
        {
            cronometro = true;
            timer1.stop();
        } else if (timer2 != null)
        {
            if (timer2.isRunning()){
                timer = true;
                timer2.stop();
            }
        }
        
        
        int verificacion = JOptionPane.showConfirmDialog(null, "Seguro que quieres terminar la partida?");
        
        if (verificacion == 0)
        {
            Menu menu = new Menu();
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
            this.setVisible(false);
            this.dispose();
            
        } else 
        {
            if (cronometro == true)
            {
                timer1.start();
            } else if (timer == true){
                timer2.start();
            }
        }
    }//GEN-LAST:event_botonTerminarJuegoActionPerformed

    private void activarBotones()
    {
        BotonBorrador.setSelected(false);
        BotonNum1.setSelected(false);
        BotonNum2.setSelected(false);
        BotonNum3.setSelected(false);
        BotonNum4.setSelected(false);
        BotonNum5.setSelected(false);
        BotonNum6.setSelected(false);
        
        botonBorrador.setSelected(false);
        botonNum1.setSelected(false);
        botonNum2.setSelected(false);
        botonNum3.setSelected(false);
        botonNum4.setSelected(false);
        botonNum5.setSelected(false);
        botonNum6.setSelected(false);
        
        for (JToggleButton[] fila : listaBotones) {
            for (JToggleButton boton : fila) {
                boton.setBackground(Color.white);
            }
        }
    }
    
    private void kenken33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken33ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken33ActionPerformed

    private void kenken54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken54ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken54ActionPerformed

    private void kenken11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken11ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken11ActionPerformed

    private void kenken12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken12ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken12ActionPerformed

    private void kenken13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken13ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken13ActionPerformed

    private void kenken14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken14ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken14ActionPerformed

    private void kenken15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken15ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken15ActionPerformed

    private void kenken16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken16ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken16ActionPerformed

    private void kenken21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken21ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken21ActionPerformed

    private void kenken22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken22ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken22ActionPerformed

    private void kenken23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken23ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken23ActionPerformed

    private void kenken24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken24ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken24ActionPerformed

    private void kenken25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken25ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken25ActionPerformed

    private void kenken26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken26ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken26ActionPerformed

    private void kenken31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken31ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken31ActionPerformed

    private void kenken32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken32ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken32ActionPerformed

    private void kenken34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken34ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken34ActionPerformed

    private void kenken35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken35ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken35ActionPerformed

    private void kenken36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken36ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken36ActionPerformed

    private void kenken41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken41ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken41ActionPerformed

    private void kenken42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken42ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken42ActionPerformed

    private void kenken43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken43ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken43ActionPerformed

    private void kenken44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken44ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken44ActionPerformed

    private void kenken45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken45ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken45ActionPerformed

    private void kenken46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken46ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken46ActionPerformed

    private void kenken51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken51ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken51ActionPerformed

    private void kenken52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken52ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken52ActionPerformed

    private void kenken53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken53ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken53ActionPerformed

    private void kenken55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken55ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken55ActionPerformed

    private void kenken56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken56ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken56ActionPerformed

    private void kenken61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken61ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken61ActionPerformed

    private void kenken62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken62ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken62ActionPerformed

    private void kenken63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken63ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken63ActionPerformed

    private void kenken64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken64ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken64ActionPerformed

    private void kenken65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken65ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken65ActionPerformed

    private void kenken66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kenken66ActionPerformed
        activarBotones();
    }//GEN-LAST:event_kenken66ActionPerformed

    /**
     * Verifica si un boton fue seleccionado
     * @return true si fue seleccionado, false si no
     */
    private boolean esSeleccionado(){
        boolean esSeleccionado = false;
        for (javax.swing.JToggleButton[] fila : listaBotones){
            for (javax.swing.JToggleButton boton : fila){
                if (boton.isSelected()){esSeleccionado = true;break;}
            }
            if (esSeleccionado){break;}
        }
        return esSeleccionado;
    }
    
    /**
     * Pone un valor a una casilla del juego y lo agrega a listaResultados
     * @param numeroBoton el elemento a insertar.
     */
    private void activarNumeroBoton(char numeroBoton)
    {
        if (!esSeleccionado()){
            JOptionPane.showMessageDialog(null, "No se puede insertar un numero si no se ha elegido la casilla");
        }
        
        botonRehacerJugada.setEnabled(false);
        this.pilaRehacerJugada.clear();
        
        int x = 0;
        int y = 0;
        //Se consiguen las coordenadas
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                listaBotones[i][j].setBackground(Color.white);
                if (this.listaBotones[i][j].isSelected())
                {
                    x = i;
                    y = j;
                    
                    Celdas esCelda = new Celdas();
                    //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
                    for (Celdas celda : partida.getCeldas())
                    {
                        if (ubicacionEnLista(i+1, j+1, celda))
                        {
                            esCelda = celda;
                            break;
                        } 
                        int[] listaCoordenadas = {i+1, j+1};
                    }
                    if (esCelda.getUbicaciones().get(0)[0] == i+1 && esCelda.getUbicaciones().get(0)[1] == j+1)
                    {
                        numeroPrincipal = esCelda.getNumeroResultado();
                        operador = esCelda.getOperando();
                        if (operador == "c")
                        {
                            if (numeroBoton == ' ')
                            {
                                String accion = "QUITA_" + i + j + "_" +listaResultados[i][j];
                                this.pushDeshacer(accion);
                                this.botonDeshacerJugada.setEnabled(true);
                                
                            } else {
                                String accion = "PONER_" + i + j + "_" + numeroBoton;
                                this.pushDeshacer(accion);
                                this.botonDeshacerJugada.setEnabled(true);
                            }
                            listaResultados[i][j] = numeroBoton;
                            listaBotones[i][j].setText("<html><center>" + numeroPrincipal
                                    + "<p>"+ numeroBoton +"</p>" +"</html>");
                            }
                         else
                        {
                            if (numeroBoton == ' ')
                            {
                                String accion = "QUITA_" + i + j + "_" + listaResultados[i][j];
                                this.pushDeshacer(accion);
                                this.botonDeshacerJugada.setEnabled(true);
                            } else {
                                String accion = "PONER_" + i + j + "_" + numeroBoton;
                                this.pushDeshacer(accion);
                                this.botonDeshacerJugada.setEnabled(true);
                            }
                            listaResultados[i][j] = numeroBoton;
                            listaBotones[i][j].setText("<html><center>" + numeroPrincipal + operador 
                                    + "<p>"+ numeroBoton +"</p>" +"</html>");
                        }
                    } else
                    {
                        if (numeroBoton == ' ')
                        {
                            String accion = "QUITA_" + i + j + "_" +listaResultados[i][j];
                            this.pushDeshacer(accion);
                            this.botonDeshacerJugada.setEnabled(true);
                        } else {
                            String accion = "PONER_" + i + j + "_" + numeroBoton;
                            this.pushDeshacer(accion);
                            this.botonDeshacerJugada.setEnabled(true);
                        }
                        this.listaResultados[i][j] = numeroBoton;
                        listaBotones[i][j].setText("<html><center><br><p>"+ numeroBoton +"</p></html>");
                    }
                }
            }
            if (x != 0 || y != 0) {break;}
        }
    }
    
    /**
     * Pone un valor en una casilla seleccionada, esta es exclusiva para los botones deshacer y rehacer jugada.
     * @param numeroBoton elemento a insertar
     * @param x fila donde se ubica el boton
     * @param y columna donde se ubica el boton
     */
    private void activarNumeroBotonPilas(char numeroBoton, int x, int y)
    {
        //Tenemos las coordenadas
        
        for (javax.swing.JToggleButton[] fila : listaBotones){
            for (javax.swing.JToggleButton boton : fila){
                boton.setBackground(Color.white);
            }
        }
               
            Celdas esCelda = new Celdas();
            //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
            for (Celdas celda : partida.getCeldas())
            {
                if (ubicacionEnLista(x+1, y+1, celda))
                {
                    esCelda = celda;
                    break;
                } 
                int[] listaCoordenadas = {x+1, y+1};
            }
            if (esCelda.getUbicaciones().get(0)[0] == x+1 && esCelda.getUbicaciones().get(0)[1] == y+1)
            {
                numeroPrincipal = esCelda.getNumeroResultado();
                operador = esCelda.getOperando();
                if (operador == "c")
                {
                    listaResultados[x][y] = numeroBoton;
                    listaBotones[x][y].setText("<html><center>" + numeroPrincipal
                            + "<p>"+ numeroBoton +"</p>" +"</html>");
                    }
                else
                {
                    listaResultados[x][y] = numeroBoton;
                    listaBotones[x][y].setText("<html><center>" + numeroPrincipal + operador 
                            + "<p>"+ numeroBoton +"</p>" +"</html>");
                }
            } else
            {
                listaResultados[x][y] = numeroBoton;
                listaBotones[x][y].setText("<html><center><br><p>"+ numeroBoton +"</p></html>");
            }
        
    }
    
    private void BotonNum2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum2ActionPerformed
        activarNumeroBoton('2');
    }//GEN-LAST:event_BotonNum2ActionPerformed

    private void BotonNum3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum3ActionPerformed
        activarNumeroBoton('3');
    }//GEN-LAST:event_BotonNum3ActionPerformed

    private void BotonNum4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum4ActionPerformed
        activarNumeroBoton('4');
    }//GEN-LAST:event_BotonNum4ActionPerformed

    private void BotonNum5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum5ActionPerformed
        activarNumeroBoton('5');
    }//GEN-LAST:event_BotonNum5ActionPerformed

    private void BotonNum6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum6ActionPerformed
        activarNumeroBoton('6');
    }//GEN-LAST:event_BotonNum6ActionPerformed

    private void BotonBorradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBorradorActionPerformed
        activarNumeroBoton(' ');
    }//GEN-LAST:event_BotonBorradorActionPerformed

    private void botonRehacerJugadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRehacerJugadaActionPerformed
        String top = popRehacer();
        pushDeshacer(top);
        
        
        if (!this.botonDeshacerJugada.isEnabled())
        {
            botonDeshacerJugada.setEnabled(true);
        }
        
        
        String operacion = top.substring(0, 5);
        String coordenadas = top.substring(6, 8);
        if (operacion.equals("QUITA"))
        {
            String x = coordenadas.substring(0, 1);
            String y = coordenadas.substring(1);
            
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(y);
            
            listaBotones[x1][y1].setSelected(true);
            activarNumeroBotonPilas(' ', x1, y1);
        } else
        {
            String x = coordenadas.substring(0, 1);
            String y = coordenadas.substring(1);
            
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(y);
            
            listaBotones[x1][y1].setSelected(true);
            
            String numero = top.substring(9);
            char charNumero = numero.charAt(0);
            activarNumeroBotonPilas(charNumero, x1, y1);
        }
        if (this.pilaRehacerJugada.isEmpty())
        {
            botonRehacerJugada.setEnabled(false);
        }
    }//GEN-LAST:event_botonRehacerJugadaActionPerformed

    private void botonReiniciarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarJuegoActionPerformed
        if (timer1.isRunning()){timer1.stop();}
        if (timer2 != null){
            if (timer2.isRunning()){timer2.stop();}
        }
        int verificacion = JOptionPane.showConfirmDialog(null, "Seguro que quieres reiniciar la partida?");
        
        if (verificacion == 0)
        {
            this.PaneHorasCrono.setText("0");
            this.PaneMinutosCrono.setText("0");
            this.PaneSegundosCrono.setText("0");
            if (ManejoInfo.getConfiguracion().getReloj()[0]){
                timer1.restart();
            } else if (ManejoInfo.getConfiguracion().getReloj()[1]){
                timer2.restart();
            }
            for (int i = 0; i < listaBotones.length; i++)
            {
                for (int j = 0; j < listaBotones[i].length; j++)
                {
                    listaResultados[i][j] = ' ';
                    listaBotones[i][j].setText("");
                    Celdas esCelda = new Celdas();
                    //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
                    for (Celdas celda : partida.getCeldas())
                    {
                        if (ubicacionEnLista(i+1, j+1, celda))
                        {
                            esCelda = celda;
                            break;
                        } 
                        int[] listaCoordenadas = {i+1, j+1};
                    }
                    if (esCelda.getUbicaciones().get(0)[0] == i+1 && esCelda.getUbicaciones().get(0)[1] == j+1)
                        {
                            numeroPrincipal = esCelda.getNumeroResultado();
                            operador = esCelda.getOperando();
                            if (operador == "c")
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + "</html>");
                            }
                            else
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + operador + "</html>");
                            }
                        }

                    //Ya se tiene la celda, ahora se modifican los bordes en funcion de la celda

                    int arriba = 2;
                    int izquierda = 2;
                    int abajo = 2;
                    int derecha = 2;

                    if (ubicacionEnLista(i, j+1, esCelda))
                    {
                        //Si comparte celda arriba
                        arriba = 0;
                    } if (ubicacionEnLista(i+1, j, esCelda))
                    {
                        //Si comparte celda a la izquierda
                        izquierda = 0;
                    } if (ubicacionEnLista(i+2, j+1, esCelda))
                    {
                        //Si comparte celda abajo
                        abajo = 0;
                    } if (ubicacionEnLista(i+1, j+2, esCelda))
                    {
                        //Si comparte celda a la derecha
                        derecha = 0;
                    }

                    listaBotones[i][j].setBorder(BorderFactory.createMatteBorder(arriba, izquierda, abajo, derecha, Color.BLACK));
                }
            }
        }
    }//GEN-LAST:event_botonReiniciarJuegoActionPerformed

    /**
     * Se valida si en la fila donde se ubica el boton, otro numero es igual
     * @param x fila donde se ubica el boton
     * @param y columna donde se ubica el boton
     * @return true si no se encontro otro numero igual, false si se encontro.
     */
    private boolean validarFila(int x, int y){
        for (int i = 0; i < listaBotones.length; i++){
            if (listaBotones[x][i] == listaBotones[x][y]){continue;}
            if (listaResultados[x][i] == listaResultados[x][y]){return false;}
        }
        return true;
    }
    
    /**
     * Se valida si en la columna donde se ubica el boton, otro numero es igual.
     * @param x fila donde se ubica el boton
     * @param y columna donde se ubica el boton
     * @return true si no se encontro otro numero igual, false si se encontro.
     */
    private boolean validarColumna(int x, int y){
        for (int j = 0; j < listaBotones.length; j++){
            if (listaBotones[j][y] == listaBotones[x][y]){continue;}
            if (listaResultados[j][y] == listaResultados[x][y]){return false;}
        }
        return true;
    }
    
    /**
     * Se busca la celda de ciertas coordenadas
     * @param x ubicacion de fila
     * @param y ubicacion de columna
     * @return La celda
     */
    private Celdas cualCelda(int x, int y){
        Celdas esCelda = new Celdas();
        for (Celdas celda : partida.getCeldas()){
            if (ubicacionEnLista(x+1, y+1, celda)){
                esCelda = celda;
                break;
            }
        }
        return esCelda;
    }
    
    /**
     * Calcula la suma de una celda
     * @param celda celda a calcular
     * @return la suma de la celdas.
     */
    private int calcularCeldaSuma(Celdas celda){
        int suma = 0;
        for (int[] coordenadas : celda.getUbicaciones()){
            int x = coordenadas[0];
            int y = coordenadas[1];
            
            char numeroChar = listaResultados[x-1][y-1];
            if (numeroChar == ' '){numeroChar = '0';}
            
            int numero = Character.getNumericValue(numeroChar);
            suma += numero;
        }
        return suma;
    }
    
    /**
     * Calcula la multiplicacion de una celda
     * @param celda celda a calcular
     * @return la multiplicacion de la celda
     */
    private int calcularCeldaMult(Celdas celda){
        int multiplicacion = 1;
        for (int[] coordenadas : celda.getUbicaciones()){
            int x = coordenadas[0];
            int y = coordenadas[1];
            
            char numeroChar = listaResultados[x-1][y-1];
            if (numeroChar == ' '){numeroChar = '1';}
            
            int numero = Character.getNumericValue(numeroChar);
            multiplicacion *= numero;
        }
        return multiplicacion;
    }
    
    /**
     * Calcula la division de una celda. La primera celda registrada se calcula como nominador, la segunda como denominador
     * @param celda la celda a calcular
     * @return una division posible de la celda.
     */
    private int calcularCeldaDiv1(Celdas celda){
        double nominador;
        double denominador;
        double resultado;
        
        int x1 = celda.getUbicaciones().get(0)[0];
        int y1 = celda.getUbicaciones().get(0)[1];
        
        int x2 = celda.getUbicaciones().get(1)[0];
        int y2 = celda.getUbicaciones().get(1)[1];
        
        nominador = Character.getNumericValue(listaResultados[x1-1][y1-1]);
        denominador = Character.getNumericValue(listaResultados[x2-1][y2-1]);
        
        resultado = (double) (nominador / denominador);
        
        if (resultado % 1 == 0){return (int)resultado;}
        else {return -1;}
    }
    
    /**
     * Calcula la division de una celda. La primera celda registrada se calcula como denominador, la segunda como nominador
     * @param celda la celda a calcular
     * @return una division posible de la celda.
     */
    private int calcularCeldaDiv2(Celdas celda){
        double nominador;
        double denominador;
        double resultado;
        
        int x1 = celda.getUbicaciones().get(0)[0];
        int y1 = celda.getUbicaciones().get(0)[1];
        
        int x2 = celda.getUbicaciones().get(1)[0];
        int y2 = celda.getUbicaciones().get(1)[1];
        
        nominador = Character.getNumericValue(listaResultados[x2-1][y2-1]);
        denominador = Character.getNumericValue(listaResultados[x1-1][y1-1]);
        
        resultado = (double) (nominador / denominador);
        if (resultado % 1 == 0){return (int)resultado;}
        else {return -1;}
    }
    
    /**
     * Se verifica que la division de la celda es igual al resultado que la celda impuso.
     * @param celda la celda a verificar
     * @return retorna true si la division dio el resultado deseado, false sino.
     */
    private boolean validarDivision(Celdas celda){
        int resultado1 = calcularCeldaDiv1(celda);
        int resultado2 = calcularCeldaDiv2(celda);
        
        return resultado1 == celda.getNumeroResultado() || resultado2 == celda.getNumeroResultado();
    }
    
    /**
     * Se verifica que la resta de la celda es igual al resultado que la celda impuso.
     * @param celda la celda a verificar
     * @return retorna true si la resta dio el resultado deseado, false sino.
     */
    private boolean validarResta(Celdas celda){
        int resultado1 = calcularResta1(celda);
        int resultado2 = calcularResta2(celda);

        return resultado1 == celda.getNumeroResultado() || resultado2 == celda.getNumeroResultado();
    }
    
    /**
     * Calcula un posible resultado de la division.
     * @param celda la celda a calcular
     * @return la resta
     */
    private int calcularResta1(Celdas celda){
        int x1 = celda.getUbicaciones().get(0)[0];
        int y1 = celda.getUbicaciones().get(0)[1];
        
        int x2 = celda.getUbicaciones().get(1)[0];
        int y2 = celda.getUbicaciones().get(1)[1];
        
        int num1 = Character.getNumericValue(listaResultados[x1 - 1][y1 - 1]);
        int num2 = Character.getNumericValue(listaResultados[x2 - 1][y2 - 1]);
        
        int resultado = num1 - num2;
        return resultado;
    }
    
    /**
     * Calcula un posible resultado de la division.
     * @param celda la celda a calcular
     * @return la resta
     */
    private int calcularResta2(Celdas celda){
        int x1 = celda.getUbicaciones().get(0)[0];
        int y1 = celda.getUbicaciones().get(0)[1];
        
        int x2 = celda.getUbicaciones().get(1)[0];
        int y2 = celda.getUbicaciones().get(1)[1];
        
        int num1 = Character.getNumericValue(listaResultados[x2 - 1][y2 - 1]);
        int num2 = Character.getNumericValue(listaResultados[x1 - 1][y1 - 1]);
        
        int resultado = num1 - num2;
        return resultado;
    }
    
    /**
     * Valida si la suma de los elementos que tiene no exceden el numero impuesto por la celda.
     * @param celda la celda a calcular
     * @return true si la suma es inferior al numero impuesto, false sino.
     */
    private boolean validarSumaConVacio(Celdas celda){
        boolean esValido = true;
        if (calcularCeldaSuma(celda) >= celda.getNumeroResultado()){esValido = false;}
        
        return esValido;
    }
    
    /**
     * Valida si la suma de los elementos que tiene son iguales al numero impuesto por la celda.
     * @param celda la celda a calcular
     * @return true si la suma es igual al numero impuesto, false sino.
     */
    private boolean validarSumaSinVacio(Celdas celda){
        boolean esValido = true;
        if (calcularCeldaSuma(celda) != celda.getNumeroResultado()){esValido = false;}
        return esValido;
    }
    
    /**
     * Valida si la multiplicacion de los elementos que tiene no excede al numero impuesto por la celda
     * @param celda la celda a calcular
     * @return true si la multiplicacion es inferior o igual al numero impuesto, false sino.
     */
    private boolean validarMultConVacio(Celdas celda){
        boolean esValido = true;
        if (calcularCeldaMult(celda) > celda.getNumeroResultado()){esValido = false;}
        
        return esValido;
    }
    
    /**
     * Valida si la multiplicacion de los elementos que tiene es igual al numero impuesto por la celda
     * @param celda la celda a calcular
     * @return true si la multiplicacion es igual al numero impuesto, false sino.
     */
    private boolean validarMultSinVacio(Celdas celda){
        boolean esValido = true;
        if (calcularCeldaMult(celda) != celda.getNumeroResultado()){esValido = false;}
        
        return esValido;
    }
    
    /**
     * Verifica si en la celda hay como minimo una casilla vacia
     * @param celda la celda a verificar
     * @return true si hay una celda vacia, false sino.
     */
    private boolean validarVacio(Celdas celda){
        boolean hayVacio = false;
        for (int[] coordenada : celda.getUbicaciones()){
            int x = coordenada[0];
            int y = coordenada[1];
            if (listaResultados[x-1][y-1] == ' '){
                hayVacio = true;
                break;
            }
        }
        return hayVacio;
    }
    
    /**
     * Verifica si todos los botones son de color verde
     * @return true si todos los botones son verdes, false sino.
     */
    private boolean validarBotonesVerde(){
        boolean esVerde = true;
        
        for (javax.swing.JToggleButton[] fila : listaBotones){
            for (javax.swing.JToggleButton boton : fila){
                if (!boton.getBackground().equals(Color.green)){esVerde = false;}
            }
        }
        return esVerde;
    }
    
    /**
     * Verifica si como minimo un boton es de color rojo
     * @return true si hay al menos un boton rojo, false sino.
     */
    private boolean validarBotonesRojo(){
        boolean esRojo = false;
        
        for (javax.swing.JToggleButton[] fila : listaBotones){
            for (javax.swing.JToggleButton boton : fila){
                if (boton.getBackground().equals(Color.red)){esRojo = true;}
            }
        }
        return esRojo;
    }
    
    /**
     * Esconde los botones de la partida y el cronometro
     */
    private void esconderBotones(){
        this.PanelKenKen.setVisible(false);
        
        this.BotonBorrador.setVisible(false);
        this.BotonNum1.setVisible(false);
        this.BotonNum2.setVisible(false);
        this.BotonNum3.setVisible(false);
        this.BotonNum4.setVisible(false);
        this.BotonNum5.setVisible(false);
        this.BotonNum6.setVisible(false);
        
        this.botonBorrador.setVisible(false);
        this.botonNum1.setVisible(false);
        this.botonNum2.setVisible(false);
        this.botonNum3.setVisible(false);
        this.botonNum4.setVisible(false);
        this.botonNum5.setVisible(false);
        this.botonNum6.setVisible(false);
        
        this.textoCrono.setVisible(false);
        this.PanelCrono.setVisible(false);
    }
    
    private void botonValidarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonValidarJuegoActionPerformed
        // TODO add your handling code here:
        
        
        for (int i = 0; i < listaBotones.length; i++){
            for (int j = 0; j < listaBotones[i].length; j++){
                if (this.listaResultados[i][j] == ' '){continue;}
                if (!validarFila(i, j)){listaBotones[i][j].setBackground(Color.red);continue;}
                if (!validarColumna(i, j)){listaBotones[i][j].setBackground(Color.red);continue;}
                
                
                Celdas celda = cualCelda(i, j);
                if (celda.getOperando().equals("c")){
                    
                    int resultadoActual = Character.getNumericValue(listaResultados[i][j]);
                    if (resultadoActual != celda.getNumeroResultado()){listaBotones[i][j].setBackground(Color.red);continue;}
                }
                if (validarVacio(celda)){
                    if (celda.getOperando().equals("+")){
                        if (!validarSumaConVacio(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    } else if (celda.getOperando().equals("x")){
                        if (!validarMultConVacio(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    } 
                } else{
                    if (celda.getOperando().equals("+")){
                        if (!validarSumaSinVacio(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    } else if (celda.getOperando().equals("x")){
                        if (!validarMultSinVacio(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    } else if (celda.getOperando().equals("-")){
                        if (!validarResta(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    } else if (celda.getOperando().equals("/")){
                        if (!validarDivision(celda)){listaBotones[i][j].setBackground(Color.red);continue;}
                    }
                }
                listaBotones[i][j].setBackground(Color.green);
            }
        }
        boolean juegoTerminado = validarBotonesVerde();
        boolean juegoConErrores = validarBotonesRojo();
        
        if (juegoTerminado){
            //Sonido del juego
            if (ManejoInfo.getConfiguracion().getSonidoFinal()[0]){
                try {
                    String ruta = "sonidos/marioBros.wav";
                    File archivoSonido = new File(ruta);
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(archivoSonido);

                    Clip sonido = AudioSystem.getClip();

                    sonido.open(audioIn);

                    sonido.start();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "HAS COMPLETADO EL JUEGO", "FELICIDADES", JOptionPane.INFORMATION_MESSAGE);
            esconderBotones();
            partida = elegirPartida();
            
            this.PaneHorasCrono.setText("0");
            this.PaneMinutosCrono.setText("0");
            this.PaneSegundosCrono.setText("0");
            for (int i = 0; i < listaBotones.length; i++)
            {
                for (int j = 0; j < listaBotones[i].length; j++)
                {
                    listaResultados[i][j] = ' ';
                    listaBotones[i][j].setText("");
                    Celdas esCelda = new Celdas();
                    //Recorre cada celda de cada partida para ver la ubicacion de la celda en la que esta ubicada el boton.
                    for (Celdas celda : partida.getCeldas())
                    {
                        if (ubicacionEnLista(i+1, j+1, celda))
                        {
                            esCelda = celda;
                            break;
                        } 
                        int[] listaCoordenadas = {i+1, j+1};
                    }
                    if (esCelda.getUbicaciones().get(0)[0] == i+1 && esCelda.getUbicaciones().get(0)[1] == j+1)
                        {
                            numeroPrincipal = esCelda.getNumeroResultado();
                            operador = esCelda.getOperando();
                            if (operador == "c")
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + "</html>");
                            }
                            else
                            {
                                listaBotones[i][j].setText("<html><center>" + numeroPrincipal + operador + "</html>");
                            }
                        }

                    //Ya se tiene la celda, ahora se modifican los bordes en funcion de la celda

                    int arriba = 2;
                    int izquierda = 2;
                    int abajo = 2;
                    int derecha = 2;

                    if (ubicacionEnLista(i, j+1, esCelda))
                    {
                        //Si comparte celda arriba
                        arriba = 0;
                    } if (ubicacionEnLista(i+1, j, esCelda))
                    {
                        //Si comparte celda a la izquierda
                        izquierda = 0;
                    } if (ubicacionEnLista(i+2, j+1, esCelda))
                    {
                        //Si comparte celda abajo
                        abajo = 0;
                    } if (ubicacionEnLista(i+1, j+2, esCelda))
                    {
                        //Si comparte celda a la derecha
                        derecha = 0;
                    }

                    listaBotones[i][j].setBorder(BorderFactory.createMatteBorder(arriba, izquierda, abajo, derecha, Color.BLACK));
                }
            
            if (timer1.isRunning()){timer1.stop();}
            if (timer2 != null){timer2.stop();}
            this.botonIniciarJuego.setEnabled(true);
            this.botonDeshacerJugada.setEnabled(false);
            this.botonRehacerJugada.setEnabled(false);
            this.botonValidarJuego.setEnabled(false);
            this.botonOtroJuego.setEnabled(false);
            this.botonReiniciarJuego.setEnabled(false);
            }
            
            for (javax.swing.JToggleButton[] fila : listaBotones){
                for (javax.swing.JToggleButton boton: fila){
                    boton.setBackground(Color.white);
                }
            }
            
        } else if (juegoConErrores){
            JOptionPane.showMessageDialog(null, "TIENES ERRORES EN LA PARTIDA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        

        
    }//GEN-LAST:event_botonValidarJuegoActionPerformed

    private void botonNum1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum1ActionPerformed
        activarNumeroBoton('1');
    }//GEN-LAST:event_botonNum1ActionPerformed

    private void botonNum4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum4ActionPerformed
        activarNumeroBoton('4');
    }//GEN-LAST:event_botonNum4ActionPerformed

    private void botonNum2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum2ActionPerformed
        activarNumeroBoton('2');
    }//GEN-LAST:event_botonNum2ActionPerformed

    private void botonNum3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum3ActionPerformed
        activarNumeroBoton('3');
    }//GEN-LAST:event_botonNum3ActionPerformed

    private void botonNum5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum5ActionPerformed
        activarNumeroBoton('5');
    }//GEN-LAST:event_botonNum5ActionPerformed

    private void botonNum6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNum6ActionPerformed
        activarNumeroBoton('6');
    }//GEN-LAST:event_botonNum6ActionPerformed

    private void botonBorradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorradorActionPerformed
        activarNumeroBoton(' ');
    }//GEN-LAST:event_botonBorradorActionPerformed

    private void BotonNum1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNum1ActionPerformed
        activarNumeroBoton('1');
    }//GEN-LAST:event_BotonNum1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Jugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jugar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BotonBorrador;
    private javax.swing.JToggleButton BotonNum1;
    private javax.swing.JToggleButton BotonNum2;
    private javax.swing.JToggleButton BotonNum3;
    private javax.swing.JToggleButton BotonNum4;
    private javax.swing.JToggleButton BotonNum5;
    private javax.swing.JToggleButton BotonNum6;
    private javax.swing.JLabel LabelHorasCrono;
    private javax.swing.JLabel LabelMinutosCrono;
    private javax.swing.JLabel LabelSegundosCrono;
    private javax.swing.JTextPane PaneHorasCrono;
    private javax.swing.JTextPane PaneMinutosCrono;
    private javax.swing.JTextPane PaneSegundosCrono;
    private javax.swing.JPanel PanelCrono;
    private javax.swing.JPanel PanelKenKen;
    private javax.swing.JToggleButton botonBorrador;
    private javax.swing.JButton botonDeshacerJugada;
    private javax.swing.JButton botonIniciarJuego;
    private javax.swing.JToggleButton botonNum1;
    private javax.swing.JToggleButton botonNum2;
    private javax.swing.JToggleButton botonNum3;
    private javax.swing.JToggleButton botonNum4;
    private javax.swing.JToggleButton botonNum5;
    private javax.swing.JToggleButton botonNum6;
    private javax.swing.JButton botonOtroJuego;
    private javax.swing.JButton botonRehacerJugada;
    private javax.swing.JButton botonReiniciarJuego;
    private javax.swing.JButton botonTerminarJuego;
    private javax.swing.JButton botonValidarJuego;
    private javax.swing.ButtonGroup grupoKenKen;
    private javax.swing.ButtonGroup grupoNumeros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToggleButton kenken11;
    private javax.swing.JToggleButton kenken12;
    private javax.swing.JToggleButton kenken13;
    private javax.swing.JToggleButton kenken14;
    private javax.swing.JToggleButton kenken15;
    private javax.swing.JToggleButton kenken16;
    private javax.swing.JToggleButton kenken21;
    private javax.swing.JToggleButton kenken22;
    private javax.swing.JToggleButton kenken23;
    private javax.swing.JToggleButton kenken24;
    private javax.swing.JToggleButton kenken25;
    private javax.swing.JToggleButton kenken26;
    private javax.swing.JToggleButton kenken31;
    private javax.swing.JToggleButton kenken32;
    private javax.swing.JToggleButton kenken33;
    private javax.swing.JToggleButton kenken34;
    private javax.swing.JToggleButton kenken35;
    private javax.swing.JToggleButton kenken36;
    private javax.swing.JToggleButton kenken41;
    private javax.swing.JToggleButton kenken42;
    private javax.swing.JToggleButton kenken43;
    private javax.swing.JToggleButton kenken44;
    private javax.swing.JToggleButton kenken45;
    private javax.swing.JToggleButton kenken46;
    private javax.swing.JToggleButton kenken51;
    private javax.swing.JToggleButton kenken52;
    private javax.swing.JToggleButton kenken53;
    private javax.swing.JToggleButton kenken54;
    private javax.swing.JToggleButton kenken55;
    private javax.swing.JToggleButton kenken56;
    private javax.swing.JToggleButton kenken61;
    private javax.swing.JToggleButton kenken62;
    private javax.swing.JToggleButton kenken63;
    private javax.swing.JToggleButton kenken64;
    private javax.swing.JToggleButton kenken65;
    private javax.swing.JToggleButton kenken66;
    private java.awt.Label textoCrono;
    // End of variables declaration//GEN-END:variables
}
