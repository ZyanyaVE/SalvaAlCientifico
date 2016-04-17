package salvaralcientifico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ZyanyaVE
 */
public class Pregunta {
    private String pregunta;
    private Respuesta[] respuesta;
    private int indiceDeRespuesta;

    
    public Pregunta(String pregunta) {
        this.pregunta = pregunta;
        this.indiceDeRespuesta = 0;
        respuesta = new Respuesta[4];
    }
    
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Respuesta[] getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta[indiceDeRespuesta] = respuesta;
        indiceDeRespuesta++;
    }
    
    
    
}
