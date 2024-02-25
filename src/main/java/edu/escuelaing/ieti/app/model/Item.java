package edu.escuelaing.ieti.app.model;

import java.io.Serializable;
import java.util.*;
/**
 * Item es una clase que representa los grupos o items que contienen elementos junto con la cantidad
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6851924882067187504L;
	private String nombre;
	private HashMap<Elemento, Integer> elementos;
	private ArrayList<Elemento> elementosEnOrden;
	//Los pesos tienen unidad en kg
	private HashMap<Elemento, Double> pesoUnitarioElemento;
	private HashMap<Elemento, Double> pesoTotalElemento;
	private HashMap<Integer, Double> pesoPorCalibreItem;
	private HashMap<Integer, HashMap<Varilla, Integer>> varillasClasificadas;
	private HashMap<Integer, HashMap<Varilla, Integer>> cantidadesTotalesVarillas;
	private HashMap<Integer, ArrayList<Varilla>> varillasClasificadasSinCant;
	private double pesoTotal;
	private boolean edicion;
	
	/**
	 * Constructor para objetos de la clase Item
	 * @param nombre es el nombre con el cual se identifica el item
	 */
	public Item (String nombre) {
		this.nombre = nombre;
		elementosEnOrden = new ArrayList<Elemento>();
		elementos = new HashMap<Elemento, Integer>();
		pesoUnitarioElemento = new HashMap<Elemento, Double>();
		pesoTotalElemento = new HashMap<Elemento, Double>();
		pesoPorCalibreItem = new HashMap<Integer, Double>();
		edicion = false;
		varillasClasificadas = new HashMap<Integer, HashMap<Varilla, Integer>>();
		cantidadesTotalesVarillas = new HashMap<Integer, HashMap<Varilla, Integer>>();
		varillasClasificadasSinCant = new HashMap<Integer, ArrayList<Varilla>>();
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.put(i, new HashMap<Varilla, Integer>());
			cantidadesTotalesVarillas.put(i, new HashMap<Varilla, Integer>());
			varillasClasificadasSinCant.put(i, new ArrayList<Varilla>());
		}
	}
	
	/**
	 * Pide el peso de cada elemento que tenga el item pero unitario
	 * @return pesoUnitarioElemento es un hashmap con llave el elemento y valor el peso unitario
	 */
	public HashMap<Elemento, Double> calcularPesoUnitarioELemento () {
		pesoUnitarioElemento.clear();
		for (Elemento elemento : elementos.keySet()) {
			pesoUnitarioElemento.put(elemento, elemento.calcularPesoTotal());
		}
		return pesoUnitarioElemento;
	}
	
	/**
	 * Pide el peso de cada elemento que tenga el item y la multiplica por la cantidad
	 * @return pesoTotalElemento es un hashmap con llave el elemento y valor el peso
	 */
	public HashMap<Elemento, Double> calcularPesoTotalElemento () {
		pesoTotalElemento.clear();
		for (Elemento elemento : elementos.keySet()) {
			pesoTotalElemento.put(elemento, elemento.calcularPesoTotal()*elementos.get(elemento));
		}
		return pesoTotalElemento;
	}
	
	/**
	 * Suma todos los pesos totales de elementos
	 * @return pesoTotal es la sumatoria
	 */
	public double calcularPesoTotal () {
		pesoTotal = 0;
		calcularPesoTotalElemento();
		for (Double peso : pesoTotalElemento.values()) {
			pesoTotal += peso;
		}
		return pesoTotal;
	}
	
	/**
	 * Calcula los pesos por cada tipo de calibre 
	 * @return pesoPorCalibre es un hashmap con llave el calibre y valor el peso
	 */
	public HashMap<Integer, Double> calcularPesoPorCalibre () {
		pesoPorCalibreItem.clear();
		for (int i = 2; i < 11; i++) {
			double pesoPorCalibreT = 0;
			for (Elemento elemento : elementosEnOrden) {
				HashMap <Integer, Double> pesosCalEle = elemento.calcularPesosPorCalibre();
				pesoPorCalibreT += (pesosCalEle.get(i) * elementos.get(elemento));
			}
			pesoPorCalibreItem.put(i, pesoPorCalibreT);
		}
		return pesoPorCalibreItem;
	}
	
	/**
	 * Verifica si tiene el elemento mandado por par�metro
	 * @param elemento indica por cual elemento se va a preguntar
	 * @return
	 */
	public boolean tieneElemento (Elemento elemento) {
		return elementosEnOrden.contains(elemento);
	}
	
	/**
	 * Elimina un elemento del arraylist y del hashmap
	 * @param elemento indica que elemento se va a eliminar
	 */
	public void eliminarElemento (Elemento elemento) {
		elementos.remove(elemento);
		elementosEnOrden.remove(elemento);
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
			HashMap<Varilla, Integer> varillasCalibreIndice = varillasClasificadas.get(calibreVarillas);
			HashMap<Varilla, Integer> varillasAgregadasTotales = cantidadesTotalesVarillas.get(calibreVarillas);
			ArrayList<Varilla> varillasNoAgregar = new ArrayList<Varilla>();
			ArrayList<Varilla> varillasCalIndSinCant = varillasClasificadasSinCant.get(calibreVarillas);
			for (Varilla varilla : varillasCalIndSinCant) {
				if (!varillasNoAgregar.contains(varilla)) {
					int cantidad = varillasCalibreIndice.get(varilla);
					varillasAgregadasTotales.put(varilla, cantidad);
					for (int i = varillasCalIndSinCant.indexOf(varilla) + 1; i < varillasCalIndSinCant.size(); i++) {
						if (varilla.comparaVarillas(varillasCalIndSinCant.get(i))) {
							int cantAnterior = varillasAgregadasTotales.get(varilla);
							int cantNueva = varillasCalibreIndice.get(varillasCalIndSinCant.get(i)) + cantAnterior;
							varillasAgregadasTotales.replace(varilla, cantNueva);
							varillasNoAgregar.add(varillasCalIndSinCant.get(i));
						}
					}
				}
			}
			cantidadesTotalesVarillas.replace(calibreVarillas, varillasAgregadasTotales);
		}
		return cantidadesTotalesVarillas;
	}
	
	/**
	 * Agrega un elemento nuevo al HashMap de elementos
	 * @param nuevoElemento objeto de tipo elemento
	 * @param cantidad cantidad del nuevo elemento
	 */
	public void agregarELemento (Elemento nuevoElemento, int cantidad) {
		elementos.put(nuevoElemento, cantidad);
		elementosEnOrden.add(nuevoElemento);
	}
	
	/**
	 * Clasifica las varillas por calibre
	 */
	public void clasificarVarillas () {
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.get(i).clear();
			varillasClasificadasSinCant.get(i).clear();
		}
		for (Elemento elemento : elementosEnOrden) {
			HashMap<Integer, HashMap<Varilla, Integer>> totalesElemento = elemento.calcularTotalesCantidadesVarillas();
			for (Integer calibre : totalesElemento.keySet()) {
				HashMap<Varilla, Integer> varillasAAgregar = totalesElemento.get(calibre);
				for (Varilla varillaAgregar : varillasAAgregar.keySet()) {
					varillasClasificadas.get(calibre).put(varillaAgregar, varillasAAgregar.get(varillaAgregar)*elementos.get(elemento));
					varillasClasificadasSinCant.get(calibre).add(varillaAgregar);
				}
			}
		}
	}
	
	/**
	 * Borra toda la informacion del Item
	 */
	public void borrarInformacion () {
		elementos.clear();
		elementosEnOrden.clear();
		pesoUnitarioElemento.clear();
		pesoTotalElemento.clear();
		for (int i = 2; i < 11; i++) {
			varillasClasificadas.get(i).clear();
			cantidadesTotalesVarillas.get(i).clear();
			varillasClasificadasSinCant.get(i).clear();
		}
	}
	
	public String getNombre () {
		return nombre;
	}
	
	public void setEdicion (boolean valor) {
		edicion = valor;
	}
	
	public HashMap<Elemento, Integer> getElementos () {
		return elementos;
	}
	
	public boolean getEdicion () {
		return edicion;
	}
	
	public ArrayList<Elemento> getElementosEnOrden () {
		return elementosEnOrden;
	}
	
	public void setNombre (String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
}
