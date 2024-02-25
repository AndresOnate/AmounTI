package edu.escuelaing.ieti.app.model;

import java.io.Serializable;
import java.util.*;
/**
 * Varilla es una clase abstracta ya que hay 8 tipos de varillas que van a heredar de esta
 * cada una tiene un comportamiento distinto para calcular sus longitudes, es por esto que no
 * se puede instanciar una varilla sin saber su tipo
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */

public abstract class Varilla implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6691501375648631320L;
	protected int longitud;
	protected int largo;
	protected int alto;
	protected int gancho;
	protected int calibre;
	protected double peso;
	//Calibres se encuentran la longitud en cm
	public static final HashMap<Integer, Integer> calibres = new HashMap<Integer, Integer>();
	
	/**
	 * Constructor para objetos de la clase varilla
	 * @param calibre indica el calibre o número de la varilla a crear
	 * @param longitud corresponde a la longitud total de la varilla en cm
	 */
	public Varilla (int calibre, int longitud) {
		this.calibre = calibre;
		this.longitud = longitud;
		agregarCalibres();
		gancho = calibres.get(calibre);
		calcularPeso();
		calcularMedidas();
	}
	
	/**
	 * Añade los calibres al HashMap
	 */
	private void agregarCalibres () {
		for (int i = 1; i < 11; i++) {
			calibres.put(i+1, 5*i);
		}
	}
	
	/**
	 * Calcula las medidas a, b y c dependiendo el calibre de la varilla
	 */
	public abstract void calcularMedidas ();
	
	public abstract int getTipo ();
	
	/**
	 * Calcula el peso de la varilla
	 */
	public void calcularPeso () {
		peso = (((double) calibre*(double) calibre)/16) * ((double) longitud/100);
	}
	
	public int getLongitud () {
		return longitud;
	}
	
	public int getLargo () {
		return largo;
	}
	
	public int getAlto () {
		return alto;
	}
	
	public int getGancho () {
		return gancho;
	}
	
	public int getCalibre () {
		return calibre;
	}
	
	public double getPeso () {
		return peso;
	}
	
	public HashMap<Integer, Integer> getCalibres () {
		return calibres;
	}
	
	/**
	 * Compara 2 varillas
	 * @param varilla corresponde a la varilla con la cual se va a comparar
	 * @return true o false dependiendo del caso
	 */
	public boolean comparaVarillas (Varilla varilla) {
		if (varilla.getClass().equals(this.getClass())) {
			if (varilla.getAlto() == this.getAlto() && varilla.getCalibre() == this.getCalibre() && varilla.getLargo() == this.getLargo()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Varilla{" +
				"longitud=" + longitud +
				", largo=" + largo +
				", alto=" + alto +
				", gancho=" + gancho +
				", calibre=" + calibre +
				", peso=" + peso +
				'}';
	}
}
