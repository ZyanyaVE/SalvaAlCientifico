package salvaAlCientifico;

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
    private int indicePregunta = 19;    // 0 - 19
    private boolean pausa = false;
    private boolean manuActivo = true;
    private Boton botona;
    private Boton botonb;
    private Boton botonc;
    private Boton botond;
    ArrayList<Integer> numbers;
    ArrayList<Pregunta> preguntas;

    //////////////////////////// Objetos ////////////////////////////////
    private Image dbImage;                          // Imagen a proyectar
    private Image background;                       // Fondo del applet
    private Image tituloPrincipal;
    private Image galileo;
    private Image marie;
    private Image albert;
    private Image isaac;
    private Image aux;
    private Image mesa;
    private Image mano;
    private Image cuadroPreguntas;
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
        leerArchivo();
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
        // Ventana de Menu
        if (ventana == 1) {
            if (background != null) {
                // Paint de la imagen de fondo
                g.drawImage(background, 0, 0, ANCHO, ALTO, this);
                g.drawImage(tituloPrincipal, 15, 40, this);
                g.drawImage(mesa, 5, 683, this);

                imprimePregunta(g);

                // Dependiendo de las vidas que le queden al usuario son los
                // científicos que se van a mostrar en pantalla, asignando tambien
                // una nueva coordenada en X a la mano
                switch (vidas) {
                    case 4:
                        coordManos = 20;
                        g.drawImage(albert, 20, 480, this);
                        g.drawImage(marie, 180, 480, this);
                        g.drawImage(isaac, 330, 480, this);
                        g.drawImage(galileo, 510, 480, this);
                        break;
                    case 3:
                        coordManos = 180;
                        g.drawImage(marie, 180, 480, this);
                        g.drawImage(isaac, 330, 480, this);
                        g.drawImage(galileo, 510, 480, this);
                        break;
                    case 2:
                        coordManos = 330;
                        g.drawImage(isaac, 330, 480, this);
                        g.drawImage(galileo, 510, 480, this);
                        break;
                    case 1:
                        coordManos = 510;
                        g.drawImage(galileo, 510, 480, this);
                        break;
                }
                g.drawImage(mano, coordManos - 8, 250, this);
                g.drawImage(cuadroPreguntas, 625, 40, this);
                g.drawImage(botona.getImagen(), botona.getCoordX(), botona.getCoordY(), this);
                g.drawImage(botonb.getImagen(), botonb.getCoordX(), botonb.getCoordY(), this);
                g.drawImage(botonc.getImagen(), botonc.getCoordX(), botonc.getCoordY(), this);
                g.drawImage(botond.getImagen(), botond.getCoordX(), botond.getCoordY(), this);

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            if (ventana == 2) {

            }

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

        //////////////////////   SELECCION DE UNA OPCION    ////////////////////
        // Opcion A
        if (x > botona.getCoordX() && x < botona.getCoordX() + 55
                && y > botona.getCoordY() && y < botona.getCoordY() + 55) {
            // Si opcion A era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(0).isCorrecta()){
                vidas--;
            }
        }
        // Opcion B
        if (x > botonb.getCoordX() && x < botonb.getCoordX() + 55
                && y > botonb.getCoordY() && y < botonb.getCoordY() + 55) {
            // Si opcion B era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(1).isCorrecta()){
                vidas--;
            }
        }
        // Opcion C
        if (x > botonc.getCoordX() && x < botonc.getCoordX() + 55
                && y > botonc.getCoordY() && y < botonc.getCoordY() + 55) {
            // Si opcion C era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(2).isCorrecta()){
                vidas--;
            }
        }
        // Opcion D
        if (x > botond.getCoordX() && x < botond.getCoordX() + 55
                && y > botond.getCoordY() && y < botond.getCoordY() + 55) {
            // Si opcion D era la respuesta correcta
            if (!preguntas.get(indicePregunta).getRespuesta(3).isCorrecta()){
                vidas--;
            }
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

    public void asignacionImagenes() {
        //Imagen de Background
        URL bURL = this.getClass().getResource("images/fondo_1.png");
        background = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/titulo.png");
        tituloPrincipal = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/galileo.png");
        galileo = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/isaac.png");
        isaac = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/marie.png");
        marie = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/albert.png");
        albert = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/rectanguloPreguntas.png");
        cuadroPreguntas = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/botona.png");
        aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botona = new Boton(aux, 665, 395);
        bURL = this.getClass().getResource("images/botonb.png");
        aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botonb = new Boton(aux, 665, 465);
        bURL = this.getClass().getResource("images/botonc.png");
        aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botonc = new Boton(aux, 665, 535);
        bURL = this.getClass().getResource("images/botond.png");
        aux = Toolkit.getDefaultToolkit().getImage(bURL);
        botond = new Boton(aux, 665, 605);
        bURL = this.getClass().getResource("images/mesa.png");
        mesa = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/mano.png");
        mano = Toolkit.getDefaultToolkit().getImage(bURL);
    }

    public void leerArchivo() {
        preguntas = new ArrayList<Pregunta>(20);
        Pregunta newPregunta;
        ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>(4);
        // The name of the file to open.
        String fileName = "src/salvaAlCientifico/docs/Preguntas.txt";

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
                System.out.println(line);
                line = bufferedReader.readLine();
                System.out.println(line);
                respuestas.add(new Respuesta(line, true));
                line = bufferedReader.readLine();
                System.out.println(line);
                respuestas.add(new Respuesta(line, false));
                line = bufferedReader.readLine();
                System.out.println(line);
                respuestas.add(new Respuesta(line, false));
                line = bufferedReader.readLine();
                System.out.println(line);
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
}


/*
 "src/salvaAlCientifico/docs/Preguntas.txt"
 */
