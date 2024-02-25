package edu.escuelaing.ieti.app.model;

import java.io.Serializable;

/**
 * Representa una varilla de tipo 1 que tiene 1 doblez (90°)
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Tipo1 extends Varilla implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7523827611108995110L;

	/**
	 * Constructor para objetos de la clase Tipo1
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Tipo1 (int calibre, int longitud) {
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
		return 1;
	}
}
