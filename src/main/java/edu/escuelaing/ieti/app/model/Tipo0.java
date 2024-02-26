package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 0 o sea sin ningún doblez o lisa
 * @version 1.0. (26 de Febrero 2024)
 */
public class Tipo0 extends Varilla implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor para objetos de la clase Tipo0
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo0 (int calibre, int longitud) {
		super (calibre, longitud);
	}
	
	/**
	 * Calcula las dimensiones, como es lisa el largo tiene el valor de la longitud total
	 */
	public void calcularMedidas () {
		largo = longitud;
		alto = 0;
	}	
	
	public int getTipo () {
		return 0;
	}
}
