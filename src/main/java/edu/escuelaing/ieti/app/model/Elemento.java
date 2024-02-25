package edu.escuelaing.ieti.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;
/**
 * Elemento es una clase que representa los elementos que contienen todo el acero correspondiente,
 * tanto transversal como longitudinal
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */

public class Elemento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6018202396780803922L;
	private String nombre;
	private HashMap<Varilla, Integer> varillas;
	private ArrayList<Varilla> varillasEnOrden;
	//Los pesos tienen unidad en kg
	private	HashMap<Varilla, Double> pesoUnitarioVarilla;
	private HashMap<Varilla, Double> pesoPorTipoVarilla;
	private HashMap<Integer, Double> pesosPorCalibre;
	private HashMap<Integer, ArrayList<Varilla>> varillasClasificadas;
	private HashMap<Integer, HashMap<Varilla, Integer>> cantidadesTotalesVarillas;
	private double pesoTotal;
	private boolean edicion;
	
	/**
	 * Constructor para objetos de la clase Elemento
	 * @param nombre es el nombre con el cual se identifica el elemento
	 */
	public Elemento (String nombre) {
		this.nombre = nombre;
		varillas = new HashMap<Varilla, Integer>();
		pesoUnitarioVarilla = new HashMap<Varilla, Double>();
		pesoPorTipoVarilla = new HashMap<Varilla, Double>();
		varillasEnOrden = new ArrayList<Varilla>();
		pesosPorCalibre = new HashMap<Integer, Double>();
		edicion = false;
		varillasClasificadas = new HashMap<Integer, ArrayList<Varilla>>();
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.put(i, new ArrayList<Varilla>());
		}
		cantidadesTotalesVarillas = new HashMap<Integer, HashMap<Varilla, Integer>>();
		for (int i = 2; i < 11; i++) {
			cantidadesTotalesVarillas.put(i, new HashMap<Varilla, Integer>());
		}
	}
	
	/**
	 * Pide el peso de cada varilla que tenga el elemento pero unitario
	 * @return pesoUnitarioVarilla es un hashmap con llave la varilla y valor el peso unitario
	 */
	public HashMap<Varilla, Double> calcularPesoUnitarioVarilla () {
		pesoUnitarioVarilla.clear();
		for (Varilla varilla : varillas.keySet()) {
			pesoUnitarioVarilla.put(varilla, varilla.getPeso());
		}
		return pesoUnitarioVarilla;
	}
	
	/**
	 * Pide el peso de cada varilla que tenga el elemento y la multiplica por la cantidad
	 * @return pesoPorTipoVarilla es un hashmap con llave la varilla y valor el peso
	 */
	public HashMap<Varilla, Double> calcularPesoPorTipoVarilla () {
		pesoPorTipoVarilla.clear();
		for (Varilla varilla : varillas.keySet()) {
			pesoPorTipoVarilla.put(varilla, varilla.getPeso()*varillas.get(varilla));
		}
		return pesoPorTipoVarilla;
	}
	
	/**
	 * Suma todos los pesos por tipo de varilla
	 * @return pesoTotal es la sumatoria
	 */
	public double calcularPesoTotal () {
		pesoTotal = 0;
		calcularPesoPorTipoVarilla();
		for (Double peso : pesoPorTipoVarilla.values()) {
			pesoTotal += peso;
		}
		return pesoTotal;
	}
	
	/**
	 * Calcula cuanto tiene de cada tipo de calibre
	 * @return pesosPorCalibre es un hashMap con llave el calibre y valor el peso
	 */
	public HashMap<Integer, Double> calcularPesosPorCalibre () {
		calcularPesoPorTipoVarilla();
		pesosPorCalibre.clear();
		for (int i = 2; i < 11; i++) {
			double pesoPorCalibre = 0;
			for (Varilla varilla : pesoPorTipoVarilla.keySet()) {
				if (varilla.getCalibre() == i) {
					pesoPorCalibre += pesoPorTipoVarilla.get(varilla);
				}
			}
			pesosPorCalibre.put(i, pesoPorCalibre);
		}
		return pesosPorCalibre;
	}
	
	/**
	 * Calcula las cantidades totales de varillas
	 * @return cantidadesTotalesVarillas es un hashmap con llave el tipo de varillas y valor las varillas que tiene con la cantidad total de cada una
	 */
	public HashMap<Integer, HashMap<Varilla, Integer>> calcularTotalesCantidadesVarillas () {
		for (int i = 2; i < 11; i++) {
			cantidadesTotalesVarillas.get(i).clear();
		}
		for (int i = 2; i < 11; i++) {
			cantidadesTotalesVarillas.put(i, new HashMap<Varilla, Integer>());
		}
		clasificarVarillas();
		for (int calibreVarillas : varillasClasificadas.keySet()) {
			ArrayList<Varilla> varillasCalibreIndice = varillasClasificadas.get(calibreVarillas);
			HashMap<Varilla, Integer> varillasAgregadasTotales = cantidadesTotalesVarillas.get(calibreVarillas);
			ArrayList<Varilla> varillasNoAgregar = new ArrayList<Varilla>();
			for (Varilla varilla : varillasCalibreIndice) {
				if (!varillasNoAgregar.contains(varilla)) {
					int cantidad = varillas.get(varilla);
					varillasAgregadasTotales.put(varilla, cantidad);
					for (int i = varillasCalibreIndice.indexOf(varilla) + 1; i < varillasCalibreIndice.size(); i++) {
						if (varilla.comparaVarillas(varillasCalibreIndice.get(i))) {
							int cantAnterior = varillasAgregadasTotales.get(varilla);
							int cantNueva = varillas.get(varillasCalibreIndice.get(i)) + cantAnterior;
							varillasAgregadasTotales.replace(varilla, cantNueva);
							varillasNoAgregar.add(varillasCalibreIndice.get(i));
						}
					}
				}
			}
			cantidadesTotalesVarillas.replace(calibreVarillas, varillasAgregadasTotales);
		}
		return cantidadesTotalesVarillas;
	}
	
	/**
	 * Agrega una varilla nueva al HashMap de varillas
	 * @param nuevaVarilla objeto de tipo varilla
	 * @param cantidad cantidad de la nueva varilla
	 */
	public void agregarVarilla (Varilla nuevaVarilla, int cantidad) {
		varillas.put(nuevaVarilla, cantidad);
		varillasEnOrden.add(nuevaVarilla);
	}
	
	/**
	 * Clasificar varillas
	 */
	public void clasificarVarillas () {
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.get(i).clear();
		}
		for (Varilla varilla : varillasEnOrden) {
			varillasClasificadas.get(varilla.getCalibre()).add(varilla);
		}
	}
	
	/**
	 * Borra toda la informacion del elemento
	 */
	public void borrarInformacion () {
		varillas.clear();
		varillasEnOrden.clear();
		pesoUnitarioVarilla.clear();
		pesoPorTipoVarilla.clear();
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.get(i).clear();
			cantidadesTotalesVarillas.get(i).clear();
		}
	}
	
	public String getNombre () {
		return nombre;
	}
	
	public HashMap<Varilla, Integer> getVarillas () {
		return varillas;
	}
	
	public boolean getEdicion () {
		return edicion;
	}
	
	public void setEdicion (boolean valor) {
		edicion = valor;
	}
	
	public ArrayList<Varilla> getVarillasEnOrden () {
		return varillasEnOrden;
	}
	
	public void setNombre (String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
	public HashMap<Integer, ArrayList<Varilla>> getVarillasClasificadas () {
		return varillasClasificadas;
	}

	@Override
	public String toString() {
		return "Elemento{" +
				"nombre='" + nombre + '\'' +
				", varillas=" + varillas +
				", varillasEnOrden=" + varillasEnOrden +
				", pesoUnitarioVarilla=" + pesoUnitarioVarilla +
				", pesoPorTipoVarilla=" + pesoPorTipoVarilla +
				", pesosPorCalibre=" + pesosPorCalibre +
				", varillasClasificadas=" + varillasClasificadas +
				", cantidadesTotalesVarillas=" + cantidadesTotalesVarillas +
				", pesoTotal=" + pesoTotal +
				", edicion=" + edicion +
				'}';
	}
}
