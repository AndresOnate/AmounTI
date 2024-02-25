package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 6 es decir flejes circulares
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo6 extends Varilla implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4217361232113810326L;

	/**
	 * Constructor para objetos de la clase Tipo6
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 * @param largo es el largo del fleje
	 */
	public Tipo6 (int calibre, int longitud) {
		super (calibre, longitud);
	}
	
	/**
	 * Calcula las dimensiones, usa la fórmula largo = longitud - 2*gancho
	 */
	public void calcularMedidas () {
		largo = longitud - 2 * gancho;
		alto = 0;
	}
	
	public int getTipo () {
		return 6;
	}
}
