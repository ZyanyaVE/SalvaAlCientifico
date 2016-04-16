package salvaAlCientifico;

import javax.swing.ImageIcon;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;

/**
	El applet AppletAnimacion muestra una animación en pantalla.
*/
public class AppletAnimacion extends Applet implements Runnable{

	//Objeto de la clase Animacion para el manejo de la animación
	private Animacion anim;
	
	//Variables de control de tiempo de la animación
	private long tiempoActual;
	private long tiempoInicial;
	int posX, posY;
	
	/**
		El método init() crea la animación que se mostrará en pantalla.
	*/
	public void init(){
		setSize(500,500);
		//Se cargan las imágenes(cuadros) para la animación
		Image mano1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano1.jpg"));
		Image mano2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano2.jpg"));
		Image mano3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano3.jpg"));
		Image mano4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano4.jpg"));
		Image mano5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano5.jpg"));
		Image mano6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano6.jpg"));
		Image mano7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano7.jpg"));
		Image mano8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano8.jpg"));
		Image mano9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano9.jpg"));
		Image mano10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano10.jpg"));
		Image mano11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano11.jpg"));
		Image mano12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano12.jpg"));
                Image mano13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano13.jpg"));
                Image mano14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano14.jpg"));
                Image mano15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano15.jpg"));
                Image mano16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano16.jpg"));
                Image mano17 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano17.jpg"));
                Image mano18 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano18.jpg"));
                Image mano19 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano19.jpg"));
                Image mano20 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano20.jpg"));
                Image mano21 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano21.jpg"));
                Image mano22 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano22.jpg"));
                Image mano23 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano23.jpg"));
                Image mano24 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano24.jpg"));
                Image mano25 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano25.jpg"));
                Image mano26 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano26.jpg"));
                Image mano27 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano27.jpg"));
                Image mano28 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano28.jpg"));
                Image mano29 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano29.jpg"));
                Image mano30 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano30.jpg"));
                Image mano31 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano31.jpg"));
                Image mano32 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mano32.jpg"));
		
		//Se crea la animación
		anim = new Animacion();
		anim.sumaCuadro(mano1, 100);
		anim.sumaCuadro(mano2, 100);
		anim.sumaCuadro(mano3, 100);
		anim.sumaCuadro(mano4, 100);
		anim.sumaCuadro(mano5, 100);
		anim.sumaCuadro(mano6, 100);
		anim.sumaCuadro(mano7, 100);
		anim.sumaCuadro(mano8, 100);
		anim.sumaCuadro(mano9, 100);
		anim.sumaCuadro(mano10, 100);
		anim.sumaCuadro(mano11, 100);
		anim.sumaCuadro(mano12, 100);
                anim.sumaCuadro(mano13, 100);
                anim.sumaCuadro(mano14, 100);
                anim.sumaCuadro(mano15, 100);
                anim.sumaCuadro(mano16, 100);
                anim.sumaCuadro(mano17, 100);
                anim.sumaCuadro(mano18, 100);
                anim.sumaCuadro(mano19, 100);
                anim.sumaCuadro(mano20, 100);
                anim.sumaCuadro(mano21, 100);
                anim.sumaCuadro(mano22, 100);
                anim.sumaCuadro(mano23, 100);
                anim.sumaCuadro(mano24, 100);
                anim.sumaCuadro(mano25, 100);
                anim.sumaCuadro(mano26, 100);
                anim.sumaCuadro(mano27, 100);
                anim.sumaCuadro(mano28, 100);
                anim.sumaCuadro(mano29, 100);
                anim.sumaCuadro(mano30, 100);
                anim.sumaCuadro(mano31, 100);
		anim.sumaCuadro(mano32, 100);
                
		//Pinta el fondo del Applet de color amarillo		
		setBackground(Color.yellow);
	}
	
	//El método start() inicializa el thread que utiliza el Applet
	public void start(){
		
		//Crea el thread
		Thread hilo = new Thread(this);
	    //Inicializa el thread
	    hilo.start();
	}
	
	/**
	 * Metodo stop sobrescrito de la clase Applet.
	 * En este metodo se pueden tomar acciones para cuando se termina
	 * de usar el Applet. Usualmente cuando el usuario sale de la pagina
	 * en donde esta este Applet.
	 */
	public void stop() {

	}

	/**
	 * Metodo destroy sobrescrito de la clase Applet.
	 * En este metodo se toman las acciones necesarias para cuando
	 * el Applet ya no va a ser usado. Usualmente cuando el usuario
	 * cierra el navegador.
	 */
	public void destroy() {

	}

	
	/**
		El método run() manda a llamar los métodos atualiza() y repaint(),
		nesecarios para actualizar y mostrar la animación en pantalla.
	*/
	 public void run() {
	 	
	 	//Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

		//Ciclo principal del Applet. Actualiza y despliega en pantalla la animación hasta que el Applet sea cerrado
        while (true) {
        	
        	//Actualiza la animación
	         actualiza();
	         //Manda a llamar al método paint() para mostrar en pantalla la animación
	         repaint();
            
            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException ex) { }
        }

   	 }
    
    /*
   	 El método actualiza() actualiza la animación
    */
    public void actualiza() {
   	
   	//Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
         long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            
         //Guarda el tiempo actual
       	 tiempoActual += tiempoTranscurrido;

         //Actualiza la animación en base al tiempo transcurrido
         anim.actualiza(tiempoTranscurrido);
    }
   
    /*
   	 El método paint() muestra en pantalla la animación
    */
    public void paint(Graphics g) {
         posX =  this.getWidth()/2 - anim.getImagen().getWidth(null)/2;
         posY =	this.getHeight()/2 - anim.getImagen().getHeight(null)/2;
         // Muestra en pantalla el cuadro actual de la animación
         if (anim != null) {
        	 g.drawImage(anim.getImagen(), posX, posY, this);
         }

    }
}