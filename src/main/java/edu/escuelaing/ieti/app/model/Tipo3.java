package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 3 que tiene un doblez (180°)
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo3 extends Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -453613315347709144L;

	/**
	 * Constructor para objetos de la clase Tipo3
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo3 (int calibre, int longitud) {
		super (calibre, longitud);
	}
	
	/**
	 * Calcula las dimensiones, usa la fórmula largo = longitud - gancho
	 */
	public void calcularMedidas () {
		largo = longitud - gancho;
		alto = 0;
	}
	
	public int getTipo () {
		return 3;
	}
}
