package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 5 es decir flejes
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo5 extends Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2399061420658363394L;

	/**
	 * Constructor para objetos de la clase Tipo5
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 * @param largo es el largo del fleje
	 */
	public Tipo5 (int calibre, int longitud, int largo) {
		super (calibre, longitud);
		this.largo = largo;
		calcularMedidas();
	}
	
	/**
	 * Calcula las dimensiones, usa la fórmula alto = (longitud - (2*largo + 2*gancho))/2
	 */
	public void calcularMedidas () {
		alto = (longitud -2 * largo - 2 * gancho)/2;
	}
	
	public int getTipo () {
		return 5;
	}
}
