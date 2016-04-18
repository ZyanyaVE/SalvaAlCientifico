/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salvaAlCientifico;

/**
 *
 * @author ZyanyaVE
 */
public class Jugador {
    private int puntaje = 0;
    private int preguntasAcertadas = 0;
    private int timeouts = 0;

    public Jugador() {
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getPreguntasAcertadas() {
        return preguntasAcertadas;
    }

    public void setPreguntasAcertadas(int preguntasAcertadas) {
        this.preguntasAcertadas = preguntasAcertadas;
    }

    public int getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(int timeouts) {
        this.timeouts = timeouts;
    }
    
    
}
