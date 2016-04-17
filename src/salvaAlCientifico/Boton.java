/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salvaAlCientifico;

import java.awt.Image;

/**
 *
 * @author ZyanyaVE
 */
public class Boton {

    private Image imagen;
    private int coordX;
    private int coordY;

    public Boton(Image imagen, int coordX, int coordY) {
        this.imagen = imagen;
        this.coordX = coordX;
        this.coordY = coordY;
    }
    
    
    
    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
    
    
}
