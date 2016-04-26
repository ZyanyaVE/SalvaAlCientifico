package salvaAlCientifico;


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ZyanyaVE
 */
public class FunFacts {

    private LinkedList<Image> imagenes;
    private int cont = 7, index;
    URL URLtemp;
    Image imagen;
    

    public FunFacts() {
        imagenes = new LinkedList<Image>();
        for (int i = 1; i <= cont; i++) {
            URLtemp = this.getClass().getResource("images/funfacts/ff" + i + ".png");
            imagen = Toolkit.getDefaultToolkit().getImage(URLtemp);
            imagenes.add(imagen);
        }
    }
    
    public Image getFunFact(){
        index = (int )(Math.random() * cont + 0);;
        System.out.println(index);
        return imagenes.get(index);
    }
}
