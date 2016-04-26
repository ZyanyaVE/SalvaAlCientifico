/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salvaAlCientifico;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.LinkedList;

/**
 *
 * @author ZyanyaVE
 */
public class Galeria {

    private LinkedList<Image> imagenes;
    private int cont = 7, index = 0;
    URL URLtemp;
    Image imagen;

    public Galeria() {
        imagenes = new LinkedList<Image>();
        for (int i = 1; i <= cont; i++) {
            URLtemp = this.getClass().getResource("images/galeria/imagen" + i + ".png");
            imagen = Toolkit.getDefaultToolkit().getImage(URLtemp);
            imagenes.add(imagen);
        }
    }

    public Image nextImage() {
        if (index == cont)
            index = 0;
        else
            index++;
        
        return imagenes.get(index);
    }
    
        public Image previousImage() {
        if (index == 0)
            index = cont;
        else
            index--;
        
        return imagenes.get(index);
    }
}
