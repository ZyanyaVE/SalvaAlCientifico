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
import java.awt.Toolkit;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;

public class SalvaAlCientifico extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    ////////////////  Variables de Control sobre el Juego /////////////////////
    static final int ANCHO = 1100;                  // Ancho del applet
    static final int ALTO = 720;                    // Alto del applet
    private int score;                              // Score del jugador
    private int ventana = 1;
    private int vidas = 4;
    private int coordManos;
    private int indicePregunta = 0;    // 0 - 19
    private int indiceMenu = 1;         // 1 - 3
    private boolean pausa;
    private boolean menuActivo;
    private boolean gameOver;
    private boolean pregunta_timeOut;
    private Boton boton_a;
    private Boton boton_b;
    private Boton boton_c;
    private Boton boton_d; 
    private Boton boton_salirPausa;
    private Boton boton_volverPausa;
    private Boton boton_volverMenu;
    ArrayList<Integer> numbers;
    ArrayList<Pregunta> preguntas;
    private Jugador jugadorActual;

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
    private Graphics dbg;                           // Objeto grafico
    private long tiempoActual;                      // Tiempo Actual
    private Timer tiempo;

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
        indicePregunta = 10;    // 0 - 19
        indiceMenu = 1;         // 1 - 3
        pausa = false;
        menuActivo = true;
        gameOver = false;
        jugadorActual = new Jugador();
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

            g.drawImage(image_background, 0, 0, ANCHO, ALTO, this);
            if (!gameOver) {
                g.drawImage(image_tituloPrincipal, 15, 40, this);
            }
            // Ventana de Menu
            if (pausa) {
                pantallaPausa(g);
                //System.out.println("pausa");
            } else if (gameOver) {
                //System.out.println("gameOver");
                pantallaGameOver(g);
                ventana = 0;
            } else if (ventana == 1) {  // VENTANA DE MENU
                //System.out.println("menu");
                pantallaMenu(g);
            } else if (ventana == 2) {  // VENTANA DE JUEGO
                //System.out.println("juego");
                pantallaJuego(g);
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
                    if (indiceMenu == 3) {
                        indiceMenu = 1;
                    } else {
                        indiceMenu++;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (indiceMenu == 1) {
                        indiceMenu = 3;
                    } else {
                        indiceMenu--;
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
                    break;
            }
            //System.out.println(indiceMenu);
            // CHEATS DE VENTANA DE JUEGO Y BOTON DE PAUSA 
        } else if (ventana == 2) {
            if (k == '1') {
                vidas = 1;
            }
            if (k == '2') {
                vidas = 2;
            }
            if (k == '3') {
                vidas = 3;
            }
            if (k == '4') {
                vidas = 4;
            }
            if (k == 'p') {
                pausa = true;
                System.out.println(pausa);
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
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
        String resp;
        int indice = 0;
        int offsetX = 0, offsetY = 0;

        // Especificaciones para los fonts que se utilzaran y los tamanos
        String pregunta = preguntas.get(indicePregunta).getPregunta();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 38));

        // Imprime preguntas en el cuadro, cuidando que no salga de los limites
        // en x
        int x = 645, y = 120;
        for (int i = 0; i < pregunta.length(); i++) {
            //System.out.println(indice);

            aux = "" + pregunta.charAt(indice);
            g.drawString(aux, x + offsetX, y + offsetY);
            offsetX += 18;
            // si no es la primera letra o mayor a 26 o sus multiplos 
            if (i != 0 && i % 23 == 0) {
                offsetX = 0;
                //i = 0;
                offsetY += 35;
            }
            indice++;
        }

        // Especificaciones para los fonts que se utilzaran y los tamanos
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 30));

        resp = preguntas.get(indicePregunta).getRespuesta(0).getRespuesta();
        g.drawString(resp, boton_a.getCoordX() + 60, boton_a.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(1).getRespuesta();
        g.drawString(resp, boton_b.getCoordX() + 60, boton_b.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(2).getRespuesta();
        g.drawString(resp, boton_c.getCoordX() + 60, boton_c.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(3).getRespuesta();
        g.drawString(resp, boton_d.getCoordX() + 60, boton_d.getCoordY() + 35);
    }

    public void imprimeTiempo(Graphics g){
        //g.drawString(tiempoActual, ALTO, ALTO);
    }
    
    public void timer(){
        
    }
    public void pantallaJuego(Graphics g) {
        g.drawImage(image_mesa, 5, 683, this);
        imprimePregunta(g);
        imprimeTiempo(g);
        
        // Dependiendo de las vidas que le queden al usuario son los
        // científicos que se van a mostrar en pantalla, asignando tambien
        // una nueva coordenada en X a la mano
        switch (vidas) {
            case 4:
                coordManos = 20;
                g.drawImage(image_albert, 20, 480, this);
                g.drawImage(image_marie, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 3:
                coordManos = 180;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_marie, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 2:
                coordManos = 330;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_puff, 180, 480, this);
                g.drawImage(image_isaac, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 1:
                coordManos = 510;
                g.drawImage(image_puff, 20, 480, this);
                g.drawImage(image_puff, 180, 480, this);
                g.drawImage(image_puff, 330, 480, this);
                g.drawImage(image_galileo, 510, 480, this);
                break;
            case 0:
                gameOver = true;
        }
        g.drawImage(image_mano, coordManos - 8, 250, this);
        g.drawImage(image_cuadroPreguntas, 625, 40, this);
        g.drawImage(boton_a.getImagen(), boton_a.getCoordX(), boton_a.getCoordY(), this);
        g.drawImage(boton_b.getImagen(), boton_b.getCoordX(), boton_b.getCoordY(), this);
        g.drawImage(boton_c.getImagen(), boton_c.getCoordX(), boton_c.getCoordY(), this);
        g.drawImage(boton_d.getImagen(), boton_d.getCoordX(), boton_d.getCoordY(), this);
        

    }

    public void pantallaMenu(Graphics g) {

        g.drawImage(image_tema1, 230, 250, this);
        g.drawImage(image_tema2, 330, 400, this);
        g.drawImage(image_tema3, 430, 550, this);

        if (indiceMenu == 1) {
            g.drawImage(image_flecha, 120, 250, this);
        } else if (indiceMenu == 2) {
            g.drawImage(image_flecha, 220, 400, this);
        } else if (indiceMenu == 3) {
            g.drawImage(image_flecha, 310, 550, this);
        }

    }

    public void pantallaPausa(Graphics g) {

        // PAUSA AL RELOJ
        g.drawImage(image_pausa, ANCHO / 2 - 250, ALTO / 2 - 50, this);
        g.drawImage(boton_salirPausa.getImagen(), boton_salirPausa.getCoordX(),
                boton_salirPausa.getCoordY(), this);
        g.drawImage(boton_volverPausa.getImagen(), boton_volverPausa.getCoordX(),
                boton_volverPausa.getCoordY(), this);
    }

    public void pantallaGameOver(Graphics g) {
        //if (ganado)
        g.drawImage(image_ganaste, 15, 40, this);
        // else (perdido)
        g.drawImage(image_perdiste, 15, 40, this);
        
        
        g.drawImage(image_resumen, 640, 200, this);
        g.drawImage(boton_volverMenu.getImagen(), boton_volverMenu.getCoordX(),
                boton_volverMenu.getCoordY(), this);
    }

    public void clicksPantallaJuego(int x, int y) {
        //////////////////////   SELECCION DE UNA OPCION    ////////////////////
        // Opcion A
        if (x > boton_a.getCoordX() && x < boton_a.getCoordX() + 55
                && y > boton_a.getCoordY() && y < boton_a.getCoordY() + 55) {
            // Si opcion A era la respuesta incorrecta O SI SE ACABA EL TIEMPO
            if (!preguntas.get(indicePregunta).getRespuesta(0).isCorrecta()) { 
                vidas--;
                
                // Si no hubo timeout quiere decir que fue resp. incorrecta
                if (!pregunta_timeOut){
                   jugadorActual.respuestaEquivocada();
                } else {
                    jugadorActual.timeOut();
                }
            }
                nextPregunta();
        }
        // Opcion B
        if (x > boton_b.getCoordX() && x < boton_b.getCoordX() + 55
                && y > boton_b.getCoordY() && y < boton_b.getCoordY() + 55) {
            // Si opcion B era la respuesta incorrecta O SI SE ACABA EL TIEMPO 
            if (!preguntas.get(indicePregunta).getRespuesta(1).isCorrecta()) {
                
                vidas--;
                
                // Si no hubo timeout quiere decir que fue resp. incorrecta
                if (!pregunta_timeOut){
                   jugadorActual.respuestaEquivocada();
                } else {
                    jugadorActual.timeOut();
                }
            }
                nextPregunta();
        }
        // Opcion C
        if (x > boton_c.getCoordX() && x < boton_c.getCoordX() + 55
                && y > boton_c.getCoordY() && y < boton_c.getCoordY() + 55) {
            // Si opcion C era la respuesta incorrecta O SI SE ACABA EL TIEMPO
            if (!preguntas.get(indicePregunta).getRespuesta(2).isCorrecta()) {
                
                vidas--;
                
                // Si no hubo timeout quiere decir que fue resp. incorrecta
                if (!pregunta_timeOut){
                   jugadorActual.respuestaEquivocada();
                } else {
                    jugadorActual.timeOut();
                }
            }
                nextPregunta();
        }
        // Opcion D
        if (x > boton_d.getCoordX() && x < boton_d.getCoordX() + 55
                && y > boton_d.getCoordY() && y < boton_d.getCoordY() + 55) {
            // Si opcion D era la respuesta incorrecta O SI SE ACABA EL TIEMPO
            if (!preguntas.get(indicePregunta).getRespuesta(3).isCorrecta()) {
                
                vidas--;
                
                // Si no hubo timeout quiere decir que fue resp. incorrecta
                if (!pregunta_timeOut){
                   jugadorActual.respuestaEquivocada();
                } else {
                    jugadorActual.timeOut();
                }
            }
                nextPregunta();
        }
    }

    public void clicksPantallaPausa(int x, int y) {
        // Click a volver al juego
        if (x > boton_volverPausa.getCoordX() && x < boton_volverPausa.getCoordX() + 242
                && y > boton_volverPausa.getCoordY() && y < boton_volverPausa.getCoordY() + 148) {

            // VOLVER A CORRER EL RELOJ
            pausa = false;

        }

        if (x > boton_salirPausa.getCoordX() && x < boton_salirPausa.getCoordX() + 242
                && y > boton_salirPausa.getCoordY() && y < boton_salirPausa.getCoordY() + 148) {

            // VOLVER A CORRER EL RELOJ
            pausa = false;
            gameOver = true;
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
            //gameOver = true;
        } else {
            indicePregunta++;
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
        bURL = this.getClass().getResource("images/flecha.png");
        image_flecha = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/tema1.png");
        image_tema1 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/tema2.png");
        image_tema2 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/tema3.png");
        image_tema3 = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/puff.png");
        image_puff = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/pausa.png");
        image_pausa = Toolkit.getDefaultToolkit().getImage(bURL);
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
}


/*
 "src/salvaAlCientifico/docs/Preguntas.txt"
 */
