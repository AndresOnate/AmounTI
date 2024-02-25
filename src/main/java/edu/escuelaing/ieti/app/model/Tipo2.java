package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 2 que tiene 2 dobleces (90°)
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo2 extends Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 939948544301227817L;

	/**
	 * Constructor para objetos de la clase Tipo2
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo2 (int calibre, int longitud) {
		super (calibre, longitud);
	}
	
	/**
	 * Calcula las dimensiones, usa la fórmula largo = longitud - 2*gancho
	 */
	public void calcularMedidas () {
		largo = longitud - (2 * gancho);
		alto = 0;
	}
	
	public int getTipo () {
		return 2;
	}
}
