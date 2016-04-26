package salvaAlCientifico;
//System.out.println(line)
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.Color.black;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.Timer;

public class SalvaAlCientifico extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener, ActionListener {

    ////////////////  Variables de Control sobre el Juego /////////////////////
    static final int ANCHO = 1100;                  // Ancho del applet
    static final int ALTO = 720;                    // Alto del applet
    private int score;                              // Score del jugador
    private int ventana = 1;
    private int vidas = 4;
    private int coordManosX;
    private int coordManosY;
    private int indicePregunta = 0;    // 0 - 19
    private int indiceMenu = 1;         // 1 - 3
    private int contadorTiempo;
    private int distanciaCaidaMano = 0;
    private int tiempoPorPregunta = 20;
    private boolean pausa;
    private boolean menuActivo;
    private boolean gameOver;
    private boolean pregunta_timeOut;
    private boolean interrupcionJuego;
    private boolean instrucciones;
    private boolean pantallaGaleria;
    boolean respuestaEquivocada;
    private Boton boton_a;
    private Boton boton_b;
    private Boton boton_c;
    private Boton boton_d;
    private Boton boton_salirPausa;
    private Boton boton_volverPausa;
    private Boton boton_volverMenu;
    private Jugador jugadorActual;
    private Timer timer;
    private Font fontPregunta;
    private Font fontRespuesta;
    private Font fontResumen;
    private FunFacts funfacts = new FunFacts();
    private Galeria galeria = new Galeria();

    ArrayList<Integer> numbers;
    ArrayList<Pregunta> preguntas;

    //////////////////////////// Objetos ////////////////////////////////
    private Image dbImage;                          // Imagen a proyectar
    private Image image_background;                       // Fondo del applet
    private Image image_tituloPrincipal;
    private Image image_galileo;
    private Image image_marie;
    private Image image_albert;
    private Image image_isaac;
    private Image image_aux;
    private Image image_mesa;
    private Image image_mano;
    private Image image_cuadroPreguntas;
    private Image image_flecha;
    private Image image_tema1;
    private Image image_tema2;
    private Image image_tema3;
    private Image image_puff;
    private Image image_pausa;
    private Image image_salirPausa;
    private Image image_volverPausa;
    private Image image_ganaste;
    private Image image_perdiste;
    private Image image_resumen;
    private Image image_volverMenu;
    private Image image_instruccionenter;
    private Image image_instruccionrespuesta;
    private Image image_instruccionsustancia;
    private Image image_instruccionpausa;
    private Image image_fondoInstrucciones;
    private Image image_funfact;
    private Image image_galeria;
    private Image image_regresarMenu;
    private Image image_navegar;
    private Graphics dbg;                           // Objeto grafico
    private long tiempoActual;                      // Tiempo Actual

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public SalvaAlCientifico() {
        // Metodo que permite asignar todas las imagenes a variables Image
        asignacionImagenes();
        setBackground(black);                              // Color de fondo del applet
        setSize(ANCHO, ALTO);                               // Dimensiones del applet 
        reinicia();

        //Adds
        addKeyListener(this);   //Implementa Key Listener    
        addMouseListener(this); //Implementa Mouse Listener
        addMouseMotionListener(this);   //implementa Mouse Motion Listener

        //**Start
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();

    }

    /**
     * Metodo <I>reinicia</I>
     * En este metodo se ejecutan todos los metodos usables durante el juego Se
     * utiliza para dar inicio cada vez que se requiera a todo el juego
     */
    public void reinicia() {                        // Metodo que inicia el juego
        ////////////////////   Caracteristicas del Applet  ////////////////////
        ventana = 1;
        vidas = 4;
        indicePregunta = 0;    // 0 - 19
        indiceMenu = 1;         // 1 - 3
        pausa = false;
        menuActivo = true;
        gameOver = false;
        jugadorActual = new Jugador();
        reiniciaContadorTiempo();
        interrupcionJuego = false;
        coordManosY = 177;
        instrucciones = false;
        image_funfact = funfacts.getFunFact();
        timerJuego();
    }

    public void reiniciaContadorTiempo() {
        contadorTiempo = 20;
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     */
    public void run() {
        //Guarda el tiempo actual del sistema
        //tiempoActual = System.currentTimeMillis();

        while (true) {

            if (!pausa) {
                actualiza();
            }
            repaint();              // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos bueno y malo.
     */
    public void actualiza() {
        // Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        // Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (image_background != null) {

            String aux = String.valueOf(tiempoActual);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier New", Font.BOLD, 38));
            g.drawString(aux, 5, 5);
            //System.out.println(tiempoActual);

            if (!instrucciones) {
                g.drawImage(image_background, 0, 0, ANCHO, ALTO, this);
                if (!gameOver) {
                    g.drawImage(image_tituloPrincipal, 15, 40, this);
                }
            }

            if (pantallaGaleria) {
                pantallaGaleria(g);
            }
            // Ventana de Menu
            if (pausa) {
                pantallaPausa(g);
                //System.out.println("pausa");
            } else if (gameOver) {
                //System.out.println("gameOver");
                pantallaGameOver(g);
                timer.stop();
                ventana = 0;
            } else if (ventana == 1) {  // VENTANA DE MENU
                //System.out.println("menu");
                pantallaMenu(g);
            } else if (ventana == 2) {  // VENTANA DE JUEGO
                //System.out.println("juego");
                if (!instrucciones) {
                    pantallaJuego(g);
                } else {
                    pantallaInstrucciones(g);
                }
            } else if (ventana == 3) {  // VENTANA DE CREDITOS
                //System.out.println("creditos");
            }
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }

    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        char k = e.getKeyChar();


        // SI SE ENCUENTRA EN LA PANTALLA DE PAUSA SE QUITA PRESIONANDO P
        if (pausa) {
            if (k == 'p') {
                pausa = false;
                return;
            }
        }
        if (ventana == 1) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (indiceMenu == 1) {
                        indiceMenu = 3;
                    } else {
                        indiceMenu--;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (indiceMenu == 3) {
                        indiceMenu = 1;
                    } else {
                        indiceMenu++;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (indiceMenu == 1) {
                        indiceMenu = 3;
                    } else {
                        indiceMenu--;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (indiceMenu == 3) {
                        indiceMenu = 1;
                    } else {
                        indiceMenu++;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    leerArchivo();
                    ventana = 2;
                    instrucciones = true;
                    break;
            }
            if (k == 'g') {
                ventana = -1;
                pantallaGaleria = true;
                return;
            }
            //System.out.println(indiceMenu);
            // CHEATS DE VENTANA DE JUEGO Y BOTON DE PAUSA 
        } else if (ventana == 2) {
            if (instrucciones) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    instrucciones = false;
                }
            }
//            if (k == '1') {
//                vidas = 1;
//            }
//            if (k == '2') {
//                vidas = 2;
//            }
//            if (k == '3') {
//                vidas = 3;
//            }
//            if (k == '4') {
//                vidas = 4;
//            }
            if (k == 'p' && !instrucciones) {
                pausa = true;
                System.out.println(pausa);
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
                if (pantallaGaleria) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    galeria.previousImage();
                    break;
                case KeyEvent.VK_RIGHT:
                    galeria.nextImage();
                    break;
                case KeyEvent.VK_ENTER:
                    ventana = 1;
                    pantallaGaleria = false;
                    galeria.resetIndex();
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Se obtienen las coordenadas del click
        int x = e.getX();
        int y = e.getY();

        if (gameOver) {
            clicksPantallaGameOver(x, y);
        }
        if (ventana == 2) {
            clicksPantallaJuego(x, y);
        }
        if (pausa) {
            clicksPantallaPausa(x, y);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void leerArchivo() {
        preguntas = new ArrayList<Pregunta>(20);
        Pregunta newPregunta;
        ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>(4);
        // The name of the file to open.
        String fileName = "src/salvaAlCientifico/docs/Preguntas" + indiceMenu + ".txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                respuestas.clear();
                newPregunta = new Pregunta(line);
                //System.out.println(line);
                line = bufferedReader.readLine();
                //System.out.println(line);
                respuestas.add(new Respuesta(line, true));
                line = bufferedReader.readLine();
                //System.out.println(line);
                respuestas.add(new Respuesta(line, false));
                line = bufferedReader.readLine();
                //System.out.println(line);
                respuestas.add(new Respuesta(line, false));
                line = bufferedReader.readLine();
                //System.out.println(line);
                respuestas.add(new Respuesta(line, false));

                long seed = System.nanoTime();
                Collections.shuffle(respuestas, new Random(seed));

                for (int i = 0; i < 4; i++) {
                    newPregunta.setRespuesta(respuestas.get(i));
                }
                preguntas.add(newPregunta);
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }

    }

    public void imprimePregunta(Graphics g) {
        String aux;
        int indice = 0;
        int offsetX = 0, offsetY = 0;

        // Especificaciones para los fonts que se utilzaran y los tamanos
        String pregunta = preguntas.get(indicePregunta).getPregunta();
        g.setColor(Color.BLACK);
        //g.setFont(new Font("Courier New", Font.BOLD, 38));
        setFontPregunta();
        g.setFont(fontPregunta);

        // Imprime preguntas en el cuadro
        int x = 645, y = 120;
        drawString(g, pregunta, x, y, 450);
    }

    public void setFontPregunta() {
        InputStream ac = this.getClass().getResourceAsStream("/fonts/SEGOEUI.TTF");
        try {
            fontPregunta = Font.createFont(Font.TRUETYPE_FONT, ac).deriveFont(44f);
        } catch (FontFormatException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimeRespuesta(Graphics g) {
        String resp;

        // Especificaciones para los fonts que se utilzaran y los tamanos
        g.setColor(Color.BLACK);
        //g.setFont(new Font("Courier New", Font.BOLD, 17));
        setFontRespuesta();
        g.setFont(fontRespuesta);

        // Imprime respuestas
        resp = preguntas.get(indicePregunta).getRespuesta(0).getRespuesta();
        drawString(g, resp, boton_a.getCoordX() + 60, boton_a.getCoordY() + 25, 365);
        resp = preguntas.get(indicePregunta).getRespuesta(1).getRespuesta();
        drawString(g, resp, boton_b.getCoordX() + 60, boton_b.getCoordY() + 25, 365);
        resp = preguntas.get(indicePregunta).getRespuesta(2).getRespuesta();
        drawString(g, resp, boton_c.getCoordX() + 60, boton_c.getCoordY() + 25, 365);
        resp = preguntas.get(indicePregunta).getRespuesta(3).getRespuesta();
        drawString(g, resp, boton_d.getCoordX() + 60, boton_d.getCoordY() + 25, 365);
    }

    public void setFontRespuesta() {
        InputStream ac = this.getClass().getResourceAsStream("/fonts/SEGOEUI.TTF");
        try {
            fontRespuesta = Font.createFont(Font.TRUETYPE_FONT, ac).deriveFont(21f);
        } catch (FontFormatException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawString(Graphics g, String s, int x, int y, int width) {
        // FontMetrics gives us information about the width,
        // height, etc. of the current Graphics object's Font.
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y;

        String[] words = s.split(" ");

        for (String word : words) {
            // Find out thw width of the word.
            int wordWidth = fm.stringWidth(word + " ");

            // If text exceeds the width, then move to next line.
            if (curX + wordWidth >= x + width) {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            // Move over to the right for next word.
            curX += wordWidth;
        }
    }

    public void imprimeTiempo(Graphics g) {
        timer.start();

        // Especificaciones para los fonts que se utilzaran y los tamanos
        if (contadorTiempo > 5) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }
        setFontPregunta();
        g.setFont(fontPregunta);
        //g.setFont(new Font("Courier New", Font.BOLD, 30));
        g.drawString(String.valueOf(contadorTiempo), 1040, 700);

    }

    public void timerJuego() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contadorTiempo--;
                coordManosY += 10;
                if (contadorTiempo <= 0) {
                    pregunta_timeOut = true;
                    timer.stop();
                    reiniciaContadorTiempo();
                }
            }
        });
    }

    public void pantallaJuego(Graphics g) {
        g.drawImage(image_mesa, 5, 683, this);
        imprimePregunta(g);
        imprimeRespuesta(g);
        imprimeTiempo(g);

        // Dependiendo de las vidas que le queden al usuario son los
        // científicos que se van a mostrar en pantalla, asignando tambien
        // una nueva coordenada en X a la mano
        switch (vidas) {
            case 4:
                coordManosX = 20;
                g.drawImage(image_albert, 20, 480, this);
                g.drawImage(image_marie, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 3:
                coordManosX = 180;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_marie, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 2:
                coordManosX = 330;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_puff, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 1:
                coordManosX = 510;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_puff, 180, 480, this);
                g.drawImage(image_puff, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 0:
                gameOver = true;
        }
        // Dibuja las manos y el cuadro que contiene las repuestas
        g.drawImage(image_mano, coordManosX - 8, coordManosY, this);
        //g.drawImage(image_cuadroPreguntas, 625, 40, this);
        // Dibuja los botones de las respuestas
        g.drawImage(boton_a.getImagen(), boton_a.getCoordX(), boton_a.getCoordY(), this);
        g.drawImage(boton_b.getImagen(), boton_b.getCoordX(), boton_b.getCoordY(), this);
        g.drawImage(boton_c.getImagen(), boton_c.getCoordX(), boton_c.getCoordY(), this);
        g.drawImage(boton_d.getImagen(), boton_d.getCoordX(), boton_d.getCoordY(), this);

        // Si se acabo el tiempo de alguna pregunta y resulta en timeout
        if (pregunta_timeOut) {
            vidas--;
            jugadorActual.timeOut();
            pregunta_timeOut = false;
            coordManosY = 177;
            nextPregunta();

        }

    }

    public void pantallaMenu(Graphics g) {

        g.drawImage(image_tema1, 230, 250, this);
        g.drawImage(image_tema2, 380, 400, this);
        g.drawImage(image_tema3, 540, 550, this);

        if (indiceMenu == 1) {
            g.drawImage(image_flecha, 120, 250, this);
        } else if (indiceMenu == 2) {
            g.drawImage(image_flecha, 260, 400, this);
        } else if (indiceMenu == 3) {
            g.drawImage(image_flecha, 420, 550, this);
        }

        g.drawImage(image_galeria, 15, 610, this);

    }

    public void pantallaPausa(Graphics g) {

        timer.stop();
        g.drawImage(image_pausa, ANCHO / 2 - 250, ALTO / 2 - 50, this);
        g.drawImage(boton_salirPausa.getImagen(), boton_salirPausa.getCoordX(),
                boton_salirPausa.getCoordY(), this);
        g.drawImage(boton_volverPausa.getImagen(), boton_volverPausa.getCoordX(),
                boton_volverPausa.getCoordY(), this);
    }

    public void pantallaGameOver(Graphics g) {
        // Si se perdió o se eligió terminar el juego antes
        if (vidas <= 0 || interrupcionJuego) {
            g.drawImage(image_perdiste, 15, 40, this);
        } else { // Se ganó el juego
            g.drawImage(image_ganaste, 15, 40, this);
        }

        g.drawImage(image_resumen, 640, 200, this);

        g.setColor(Color.BLACK);
        //g.setFont(new Font("Helvetica", Font.BOLD, 35));
        setFontResumen();
        g.setFont(fontResumen);
        g.drawString("PUNTAJE:          " + jugadorActual.getPuntaje(), 660, 310);
        g.drawString("ACIERTOS:         " + (indicePregunta + 1 - jugadorActual.getPreguntasEquivocadas()), 660, 410);
        g.drawString("ERRORES:          " + jugadorActual.getPreguntasEquivocadas(), 660, 510);
        g.drawString("TIMEOUTS:         " + jugadorActual.getTimeouts(), 660, 610);

        g.drawImage(boton_volverMenu.getImagen(), boton_volverMenu.getCoordX(),
                boton_volverMenu.getCoordY(), this);

        g.drawImage(image_funfact, 35, 190, this);
    }

    public void pantallaGaleria(Graphics g) {
        g.drawImage(galeria.getImage(), (ANCHO / 3 - 105), (ALTO / 4), this);
        
        
        g.drawImage(image_regresarMenu, 10, 550, this);
        g.drawImage(image_navegar, 850, 550, this);
    }

    public void setFontResumen() {
        InputStream ac = this.getClass().getResourceAsStream("/fonts/SEGOEUI.TTF");
        try {
            fontResumen = Font.createFont(Font.TRUETYPE_FONT, ac).deriveFont(35f);
        } catch (FontFormatException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalvaAlCientifico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pantallaInstrucciones(Graphics g) {
        g.drawImage(image_fondoInstrucciones, 0, 0, ANCHO, ALTO, this);
        g.drawImage(image_instruccionenter, 735, 250, this);
        g.drawImage(image_instruccionpausa, 735, 100, this);
        g.drawImage(image_instruccionrespuesta, boton_a.getCoordX() - 265, boton_a.getCoordY() - 145, this);
        g.drawImage(image_instruccionsustancia, 115, 177, this);
    }

    public void clicksPantallaJuego(int x, int y) {
        respuestaEquivocada = false;
        boolean clickBoton = false;

        //////////////////////   SELECCION DE UNA OPCION    ////////////////////
        // Opcion A
        if (x > boton_a.getCoordX() && x < boton_a.getCoordX() + 55
                && y > boton_a.getCoordY() && y < boton_a.getCoordY() + 55) {
            clickBoton = true;
            // Si opcion A era la respuesta incorrecta
            if (!preguntas.get(indicePregunta).getRespuesta(0).isCorrecta()) {
                respuestaEquivocada = true;
            } else {
                jugadorActual.respuestaCorrecta();
            }
        }
        // Opcion B
        if (x > boton_b.getCoordX() && x < boton_b.getCoordX() + 55
                && y > boton_b.getCoordY() && y < boton_b.getCoordY() + 55) {
            clickBoton = true;
            // Si opcion B era la respuesta incorrecta
            if (!preguntas.get(indicePregunta).getRespuesta(1).isCorrecta()) {
                respuestaEquivocada = true;
            } else {
                jugadorActual.respuestaCorrecta();
            }
        }
        // Opcion C
        if (x > boton_c.getCoordX() && x < boton_c.getCoordX() + 55
                && y > boton_c.getCoordY() && y < boton_c.getCoordY() + 55) {
            clickBoton = true;
            // Si opcion C era la respuesta incorrecta
            if (!preguntas.get(indicePregunta).getRespuesta(2).isCorrecta()) {
                respuestaEquivocada = true;
            } else {
                jugadorActual.respuestaCorrecta();
            }
        }
        // Opcion D
        if (x > boton_d.getCoordX() && x < boton_d.getCoordX() + 55
                && y > boton_d.getCoordY() && y < boton_d.getCoordY() + 55) {
            clickBoton = true;
            // Si opcion D era la respuesta incorrecta
            if (!preguntas.get(indicePregunta).getRespuesta(3).isCorrecta()) {
                respuestaEquivocada = true;
            } else {
                jugadorActual.respuestaCorrecta();
            }

        }

        // La seleccion de alguna pregunta fue equivocada
        if (clickBoton) {
            if (respuestaEquivocada) {
                vidas--;
                respuestaEquivocada = false;
                jugadorActual.respuestaEquivocada();
                coordManosY = 177;
            }
            nextPregunta();
        }

    }

    public void clicksPantallaPausa(int x, int y) {
        // Click a volver al juego
        if (x > boton_volverPausa.getCoordX() && x < boton_volverPausa.getCoordX() + 242
                && y > boton_volverPausa.getCoordY() && y < boton_volverPausa.getCoordY() + 148) {
            timer.start();
            pausa = false;
        }

        if (x > boton_salirPausa.getCoordX() && x < boton_salirPausa.getCoordX() + 242
                && y > boton_salirPausa.getCoordY() && y < boton_salirPausa.getCoordY() + 148) {
            pausa = false;
            gameOver = true;
            interrupcionJuego = true;
            ventana = 1;
        }
    }

    public void clicksPantallaGameOver(int x, int y) {
        System.out.println("click pantalla game over");
        if (x > boton_volverMenu.getCoordX() && x < boton_volverMenu.getCoordX() + 200
                && y > boton_volverMenu.getCoordY() && y < boton_volverMenu.getCoordY() + 124) {
            System.out.println("VOLVER AL MENU Y REINICIAR");
            reinicia();
        }
    }

    public void nextPregunta() {
        if (indicePregunta == 19) {
            gameOver = true;
        } else {
            indicePregunta++;

            coordManosY = 177;
            reiniciaContadorTiempo();
            timer.start();
        }
    }

    public void asignacionImagenes() {
        ////////////////////        IMAGENES         ///////////////////////
        //Pantalla Juego
        URL bURL = this.getClass().getResource("images/fondo_1.png");
        image_background = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/titulo.png");
        image_tituloPrincipal = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/galileo.png");
        image_galileo = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/isaac.png");
        image_isaac = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/marie.png");
        image_marie = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/albert.png");
        image_albert = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/rectanguloPreguntas.png");
        image_cuadroPreguntas = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/mesa.png");
        image_mesa = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/mano.png");
        image_mano = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/puff.png");
        image_puff = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/galeria.png");
        image_galeria = Toolkit.getDefaultToolkit().getImage(bURL);
        
        bURL = this.getClass().getResource("images/galeria/regresarMenu.png");
        image_regresarMenu = Toolkit.getDefaultToolkit().getImage(bURL);
        
        bURL = this.getClass().getResource("images/galeria/navegar.png");
        image_navegar = Toolkit.getDefaultToolkit().getImage(bURL);

        // Pantalla Menu
        bURL = this.getClass().getResource("images/tema1.png");
        image_tema1 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/tema2.png");
        image_tema2 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/tema3.png");
        image_tema3 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/flecha.png");
        image_flecha = Toolkit.getDefaultToolkit().getImage(bURL);

        bURL = this.getClass().getResource("images/pausa.png");
        image_pausa = Toolkit.getDefaultToolkit().getImage(bURL);

        // Pantalla Instruccion
        bURL = this.getClass().getResource("images/instrucciones_enter.png");
        image_instruccionenter = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/instrucciones_respuesta.png");
        image_instruccionrespuesta = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/instrucciones_substancia.png");
        image_instruccionsustancia = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/instrucciones_pausa.png");
        image_instruccionpausa = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/instrucciones_fondo.png");
        image_fondoInstrucciones = Toolkit.getDefaultToolkit().getImage(bURL);

        // Pantalla Game Over
        bURL = this.getClass().getResource("images/ganaste.png");
        image_ganaste = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/perdiste.png");
        image_perdiste = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/resumen.png");
        image_resumen = Toolkit.getDefaultToolkit().getImage(bURL);

        ///////////////////////     Botones     //////////////////////////
        bURL = this.getClass().getResource("images/botona.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_a = new Boton(image_aux, 665, 395);
        bURL = this.getClass().getResource("images/botonb.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_b = new Boton(image_aux, 665, 465);
        bURL = this.getClass().getResource("images/botonc.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_c = new Boton(image_aux, 665, 535);
        bURL = this.getClass().getResource("images/botond.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_d = new Boton(image_aux, 665, 605);
        ///// Pantalla Pausa 
        bURL = this.getClass().getResource("images/salirDelJuego.png");
        image_salirPausa = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_salirPausa = new Boton(image_salirPausa, 850, 560);
        bURL = this.getClass().getResource("images/volverAlJuego.png");
        image_volverPausa = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_volverPausa = new Boton(image_volverPausa, 10, 560);
        //// Pantalla Game Over //////////
        bURL = this.getClass().getResource("images/volverMenu.png");
        image_volverMenu = Toolkit.getDefaultToolkit().getImage(bURL);
        boton_volverMenu = new Boton(image_volverMenu, 10, 560);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


/*
 "src/salvaAlCientifico/docs/Preguntas.txt"
 */
