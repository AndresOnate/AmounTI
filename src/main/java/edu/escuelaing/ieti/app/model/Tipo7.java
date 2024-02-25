package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 7 las cuales son sin ningún doblez que llegan a la obra a ser dobladas
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo7 extends Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6330317440470650525L;

	/**
	 * Constructor para objetos de la clase Tipo7
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo7 (int calibre, int longitud) {
		super (calibre, longitud);
	}
	
	/**
	 * Calcula las dimensiones, el largo tiene el valor de la longitud
	 */
	public void calcularMedidas () {
		largo = longitud;
		alto = 0;
	}
	
	public int getTipo () {
		return 7;
	}
}
