package salvaAlCientifico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  Leonardo Daniel Gutiérrez
 *  Zyanya Valdés Esquivel
 *  Manuel Alejandro Tellez
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.Color.black;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;


public class SalvaAlCientifico extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {
    
    ////////////////  Variables de Control sobre el Juego /////////////////////
    static final int ANCHO = 1100;                  // Ancho del applet
    static final int ALTO = 720;                    // Alto del applet
    private int score;                              // Score del jugador
    private int ventana = 1;
    private boolean pausa = false;
    private boolean manuActivo = true;
    
    //////////////////////////// Objetos ////////////////////////////////
    private Image dbImage;                          // Imagen a proyectar
    private Image background;                       // Fondo del applet
    private Image tituloPrincipal;
    private Graphics dbg;                           // Objeto grafico
    private long tiempoActual;                      // Tiempo Actual

    
    
     /** 
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public SalvaAlCientifico() {  
        reinicia();
        
        //Adds
        addKeyListener(this);   //Implementa Key Listener    
        addMouseListener(this); //Implementa Mouse Listener
        addMouseMotionListener(this);   //implementa Mouse Motion Listener
        
        //**Start
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
                
    }
    
     /** 
     * Metodo <I>reinicia</I>
     * En este metodo se ejecutan todos los metodos usables durante el juego
     * Se utiliza para dar inicio cada vez que se requiera a todo el juego
     */
    public void reinicia() {                        // Metodo que inicia el juego
        ////////////////////   Caracteristicas del Applet  ////////////////////
        //Imagen de Background
        URL bURL = this.getClass().getResource("images/lab.png");
        background = Toolkit.getDefaultToolkit().getImage(bURL);
        bURL = this.getClass().getResource("images/titulo2.png");
        tituloPrincipal = Toolkit.getDefaultToolkit().getImage(bURL);
        
        
        
        setBackground (black);                              // Color de fondo del applet
        setSize(ANCHO, ALTO);                               // Dimensiones del applet  
    }
 
    
     /** 
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y dependiendo de la direccion, finalmente 
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     */
    public void run (){
        //Guarda el tiempo actual del sistema
        //tiempoActual = System.currentTimeMillis();
        
        while (true) {

            if (!pausa) {
                actualiza();
            }
                repaint();              // Se actualiza el <code>Applet</code> repintando el contenido.
                try {
                                        // El thread se duerme.
                        Thread.sleep (20);
                }
                catch (InterruptedException ex)	{
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
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
		if (dbImage == null){
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		// Actualiza la imagen de fondo.
		dbg.setColor(getBackground ());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// Actualiza el Foreground.
		dbg.setColor(getForeground());
		paint1(dbg);

		// Dibuja la imagen actualizada
		g.drawImage (dbImage, 0, 0, this);
        
    }
    
    
    	/**
	 * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
    public void paint1(Graphics g) {
        // Ventana de Menu
        if(ventana == 1){
            if (background != null){
                // Paint de la imagen de fondo
                g.drawImage(background, 0, 0, ANCHO, ALTO, this);
                g.drawImage(tituloPrincipal, 1, 1,500 , 280, this);
            }  
            else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
            
            
            
        }else {
            if (ventana == 2){
         
            }
            
        }
    }

   
    	/**
	 * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
	 * @param e es el <code>evento</code> generado al presionar las teclas.
	 */
    public void keyPressed(KeyEvent e) {

    }

    
        /**
	 * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al soltar la tecla presionada.
	 * @param e es el <code>evento</code> que se genera en al soltar las teclas.
	 */
    public void keyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
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
}
    
    
