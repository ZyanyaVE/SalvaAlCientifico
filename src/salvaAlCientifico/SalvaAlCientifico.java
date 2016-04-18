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
    private int indicePregunta = 10;    // 0 - 19
    private int indiceMenu = 1;         // 1 - 3
    private boolean pausa = false;
    private boolean menuActivo = true;
    private boolean gameOver = false;
    private Boton botona;
    private Boton botonb;
    private Boton botonc;
    private Boton botond;
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
    private Image image_salir;
    private Graphics dbg;                           // Objeto grafico
    private long tiempoActual;                      // Tiempo Actual

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public SalvaAlCientifico() {
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
        // Metodo que permite asignar todas las imagenes a variables Image
        asignacionImagenes();
        setBackground(black);                              // Color de fondo del applet
        setSize(ANCHO, ALTO);                               // Dimensiones del applet  
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
            // Paint de la imagen de fondo
            g.drawImage(image_background, 0, 0, ANCHO, ALTO, this);
            g.drawImage(image_tituloPrincipal, 15, 40, this);

            // Ventana de Menu
            if (pausa) {
                pantallaPausa(g);
                System.out.println("pausa");
            } else if (gameOver) {
                System.out.println("gameOver");
            } else if (ventana == 1) {  // VENTANA DE MENU
                System.out.println("menu");
                pantallaMenu(g);
            } else if (ventana == 2) {  // VENTANA DE JUEGO
                System.out.println("juego");
                pantallaJuego(g);
            } else if (ventana == 3) {  // VENTANA DE CREDITOS
                System.out.println("creditos");
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
            if (k == 'p'){
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

        if (ventana == 2) {
            clickOpcion(x, y);
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
        g.drawString(resp, botona.getCoordX() + 60, botona.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(1).getRespuesta();
        g.drawString(resp, botonb.getCoordX() + 60, botonb.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(2).getRespuesta();
        g.drawString(resp, botonc.getCoordX() + 60, botonc.getCoordY() + 35);
        resp = preguntas.get(indicePregunta).getRespuesta(3).getRespuesta();
        g.drawString(resp, botond.getCoordX() + 60, botond.getCoordY() + 35);
    }

    public void pantallaJuego(Graphics g) {
        g.drawImage(image_mesa, 5, 683, this);
        imprimePregunta(g);

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
        }
        g.drawImage(image_mano, coordManos - 8, 250, this);
        g.drawImage(image_cuadroPreguntas, 625, 40, this);
        g.drawImage(botona.getImagen(), botona.getCoordX(), botona.getCoordY(), this);
        g.drawImage(botonb.getImagen(), botonb.getCoordX(), botonb.getCoordY(), this);
        g.drawImage(botonc.getImagen(), botonc.getCoordX(), botonc.getCoordY(), this);
        g.drawImage(botond.getImagen(), botond.getCoordX(), botond.getCoordY(), this);

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

    public void clickOpcion(int x, int y) {
        //////////////////////   SELECCION DE UNA OPCION    ////////////////////
        // Opcion A
        if (x > botona.getCoordX() && x < botona.getCoordX() + 55
                && y > botona.getCoordY() && y < botona.getCoordY() + 55) {
            // Si opcion A era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(0).isCorrecta()) {
                vidas--;
                nextPregunta();
            }
        }
        // Opcion B
        if (x > botonb.getCoordX() && x < botonb.getCoordX() + 55
                && y > botonb.getCoordY() && y < botonb.getCoordY() + 55) {
            // Si opcion B era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(1).isCorrecta()) {
                vidas--;
                nextPregunta();
            }
        }
        // Opcion C
        if (x > botonc.getCoordX() && x < botonc.getCoordX() + 55
                && y > botonc.getCoordY() && y < botonc.getCoordY() + 55) {
            // Si opcion C era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(2).isCorrecta()) {
                vidas--;
                nextPregunta();
            }
        }
        // Opcion D
        if (x > botond.getCoordX() && x < botond.getCoordX() + 55
                && y > botond.getCoordY() && y < botond.getCoordY() + 55) {
            // Si opcion D era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(3).isCorrecta()) {
                vidas--;
                nextPregunta();
            }
        }
    }

    public void nextPregunta() {
        if (indicePregunta == 19) {
            //gameOver = true;
        } else {
            indicePregunta++;
        }
    }

    public void pantallaPausa(Graphics g) {

        // PAUSA AL RELOJ
        g.drawImage(image_pausa, ANCHO / 2 - 250, ALTO / 2 - 50, this);
        g.drawImage(image_salir, 900, 600, this);
    }

    public void asignacionImagenes() {
        //Imagen de Background
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
        bURL = this.getClass().getResource("images/botona.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botona = new Boton(image_aux, 665, 395);
        bURL = this.getClass().getResource("images/botonb.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botonb = new Boton(image_aux, 665, 465);
        bURL = this.getClass().getResource("images/botonc.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botonc = new Boton(image_aux, 665, 535);
        bURL = this.getClass().getResource("images/botond.png");
        image_aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botond = new Boton(image_aux, 665, 605);
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
        bURL = this.getClass().getResource("images/salirDelJuego.png");
        image_salir = Toolkit.getDefaultToolkit().getImage(bURL);

    }
}


/*
 "src/salvaAlCientifico/docs/Preguntas.txt"
 */
