package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 8 que tiene un doblez (180°) y un doblez (90°)
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo8 extends Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1117730722223287253L;

	/**
	 * Constructor para objetos de la clase Tipo7
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo8 (int calibre, int longitud) {
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
		return 8;
	}
}
