package edu.escuelaing.ieti.app.model;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import edu.escuelaing.ieti.app.exception.*;

import javax.swing.JOptionPane;

import com.spire.doc.*;
import com.spire.doc.documents.BackgroundType;
import com.spire.doc.documents.BorderStyle;
import com.spire.doc.documents.BreakType;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.MarginsF;
import com.spire.doc.documents.PageOrientation;
import com.spire.doc.documents.PageSize;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.RowAlignment;
import com.spire.doc.documents.ShapeType;
import com.spire.doc.documents.TableRowHeightType;
import com.spire.doc.documents.VerticalAlignment;
import com.spire.doc.fields.ShapeGroup;
import com.spire.doc.fields.ShapeObject;
import com.spire.doc.fields.TextBox;
import com.spire.doc.fields.TextRange;

/**
 * Cantidades es las clase principal que contiene elementos e items
 * @author Santiago Arévalo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class Cantidades implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -226326614452809701L;
	private ArrayList<Varilla> varillas;
	private ArrayList<Elemento> elementos;
	private ArrayList<Item> items;
	private HashMap<Item, Integer> itemsCantidades;
	private HashMap<Item, Double> pesoUnitarioItem;
	private HashMap<Item, Double> pesoTotalItem;
	private Elemento elementoActual;
	private Item itemActual;
	private String nombre;
	private File file;
	private Cantidades instancia = this;
	
	
	/**
	 * Constructor de objetos de la clase Cantidades
	 */
	public Cantidades (String nombre) {
		this.nombre = nombre;
		varillas = new ArrayList<Varilla>();
		elementos = new ArrayList<Elemento>();
		items = new ArrayList<Item>();
		itemsCantidades = new HashMap<Item, Integer>();
		pesoUnitarioItem = new HashMap<Item, Double>();
		pesoTotalItem = new HashMap<Item, Double>();
	}
	
	//CREAR
	
	/**
	 * Settea el item que se est� editando en el momento
	 * @param item actual
	 */
	public void ponerItemActual (Item item) {
		itemActual = item;
	}
	
	/**
	 * Crea un nuevo item
	 * @param nombre corresponde al nombre del nuevo item
	 */
	public void crearItem (String nombre, int cantidad) {
		Item nuevoItem = new Item(nombre);
		ponerItemActual(nuevoItem);
	}
	
	/**
	 * Agrega un item a las estructuras donde se guardan
	 * @param item el objeto que se va a agregar
	 * @param cantidad es la cantidad del item a agregar
	 */
	public void agregarItemAEstructuras (Item item, int cantidad) {
		items.add(item);
		itemsCantidades.put(item, cantidad);
	}
		
	/**
	 * Agrega un elemento al item actual
	 * @param nombre es el nombre del elemento que va a agregar en el item
	 * @param cantidad cuantos de dicho elemento tiene el item
	 * @throws CantidadErronea 
	 */
	public void agregarElementoAItem (String nombre, int cantidad) throws CantidadErronea{
		if (cantidad <= 0) {
			throw new CantidadErronea("Ingres� una cantidad err�nea");
		}
		for (Elemento elementoAAgregar : elementos) {
			if (elementoAAgregar.getNombre().equals(nombre)) {
				itemActual.agregarELemento(elementoAAgregar, cantidad);
				break;
			}
		}
	}
	
	/**
	 * Settea el elemento que se está editando en el momento
	 * @param elemento actual
	 */
	public void ponerElementoActual (Elemento elemento) {
		elementoActual = elemento;
	}
	
	/**
	 * Crea un nuevo elemento
	 * @param nombre corresponde al nombre del nuevo elemento
	 */
	public void crearElemento (String nombre) {
		Elemento nuevoElemento = new Elemento(nombre);
		ponerElementoActual(nuevoElemento);
	}
	
	/**
	 * Agregar al arreglo de elementos un elemento enviado
	 * @param elemento a agregar
	 */
	public void agregarElementoAArreglo (Elemento elemento) {
		elementos.add(elemento);
	}
	
	/**
	 * Este es el agregar varilla a elemento publico que puede acceder el gui
	 * @param calibre
	 * @param longitud
	 * @param tipo
	 * @param largo
	 * @param cantidad
	 * @throws TipoIncorrecto
	 * @throws CalibreNoExiste 
	 * @throws LongitudErronea 
	 * @throws CantidadErronea 
	 * @throws LargoErroneo 
	 */
	public void agregarVarillaAElementoPublico (int calibre, int longitud, int tipo, int largo, int cantidad) throws TipoIncorrecto, CalibreNoExiste, LongitudErronea, CantidadErronea, LargoErroneo {
		if (calibre < 2 || calibre > 10) {
			throw new CalibreNoExiste("Ingres� un calibre inexistente");
		}
		if (longitud <= 0 || longitud > 1200) {
			throw new LongitudErronea("Ingres� una longitud err�nea");
		}
		if (cantidad <= 0) {
			throw new CantidadErronea("Ingres� una cantidad err�nea");
		}
		if (tipo == 5 && (largo <= 0 || largo > longitud-1)) {
			throw new LargoErroneo("Ingres� un largo err�neo");
		}
		Varilla varillaAAgregar;
		try {
			varillaAAgregar = crearVarilla(calibre, longitud, tipo, largo);
			agregarVarillaAElemento(varillaAAgregar, cantidad);
		} catch (TipoIncorrecto e) {
			throw e;
		}
		
	}
	
	/**
	 * Agrega una varilla al elemento acutal
	 * (Para usarla debe usar primero crearVarilla para obtener la varilla que va a agregar si es desde 0's)
	 * @param varillaAAgregar objeto de tipo Varilla que se va a añadir al elemento
	 * @param cantidad
	 */
	private void agregarVarillaAElemento (Varilla varillaAAgregar, int cantidad) {
		elementoActual.agregarVarilla(varillaAAgregar, cantidad);
	}
	
	/**
	 * Agrega una varilla al arreglo de varillas
	 * @param calibre
	 * @param longitud
	 * @param tipo
	 * @param largo
	 * @throws TipoIncorrecto si el tipo ingresado no se encuentra entre 0 y 8
	 * @throws CalibreNoExiste si el tipo de calibre no existe
	 */
	private Varilla crearVarilla (int calibre, int longitud, int tipo, int largo) throws TipoIncorrecto {
		Varilla varillaAgregada = null;
		try {
			Varilla nuevaVarilla = convierteTipoAVarilla(calibre, longitud, tipo, largo);
			varillaAgregada = nuevaVarilla;
		} catch (TipoIncorrecto e) {
			throw e;
		}
		return varillaAgregada;
	}
	
	/**
	 * Agrega las varillas de un elemento al arreglo de varillas
	 * @param elemento del cual se obtienen las varillas
	 */
	public void agregarVarillasAArreglo (Elemento elemento) {
		for (Varilla varilla : elemento.getVarillasEnOrden()) {
			varillas.add(varilla);
		}
	}
	
	/**
	 * Crea una varilla seg�n el tipo de ingreso
	 * @param calibre
	 * @param longitud
	 * @param tipo
	 * @param largo
	 * @return un objeto varilla según el tipo
	 * @throws TipoIncorrecto si el tipo no es numero entre 0 y 8
	 */
	private Varilla convierteTipoAVarilla (int calibre, int longitud, int tipo, int largo) throws TipoIncorrecto {
		Varilla nuevaVarilla = new Tipo0(calibre, longitud);
		if (tipo == 0) {
			nuevaVarilla = new Tipo0(calibre, longitud);
		} else if (tipo == 1) {
			nuevaVarilla = new Tipo1(calibre, longitud);
		} else if (tipo == 2) {
			nuevaVarilla = new Tipo2(calibre, longitud);
		} else if (tipo == 3) {
			nuevaVarilla = new Tipo3(calibre, longitud);
		} else if (tipo == 4) {
			nuevaVarilla = new Tipo4(calibre, longitud);
		} else if (tipo == 5) {
			nuevaVarilla = new Tipo5(calibre, longitud, largo);
		} else if (tipo == 6) {
			nuevaVarilla = new Tipo6(calibre, longitud);
		} else if (tipo == 7) {
			nuevaVarilla = new Tipo7(calibre, longitud);
		} else if (tipo == 8) {
			nuevaVarilla = new Tipo8(calibre, longitud);
		} else throw new TipoIncorrecto("Ingres� un tipo de varilla inexistente"); 
		return nuevaVarilla;
	}
	
	
	//EDITAR
	
	/**
	 * Setea el elemento actual por el cual se va a editar y as� mismo lo pone en modo edici�n
	 * @param editar corresponde al elementos que se va a modificar
	 */
	public void editarElemento (Elemento editar) {
		ponerElementoActual(editar);
		editar.setEdicion(true);
	}
	
	/**
	 * Busca el item que se va a editar para proceder a setteralo como el nuevo item actual
	 * @param nombreItem
	 */
	public void editarItem (Item editar) {
		ponerItemActual(editar);
		editar.setEdicion(true);
	}
	
	//ELIMINAR ELEMENTO
	
	/**
	 * Elimina las ocurrencias de un elemento en los items que lo tenga
	 * @param eliminar elemento a eliminar
	 */
	public void eliminarElemento (Elemento eliminar) {
		for (Item item : items) {
			item.eliminarElemento(eliminar);
		}
		elementos.remove(eliminar);
	}
	
	/**
	 * Elmimin un iteme
	 * @param item se refiere al item a eliminar
	 */
	public void eliminarItem (Item item) {
		items.remove(item);
	}
	
	//PESOS ITEMS
	
	/**
	 * Calcula el peso unitario de cada uno de los items del arreglo de items
	 * @return pesoUnitarioItem un HashMap con llave el item y valor su peso unitario
	 */
	public HashMap<Item, Double> calcularPesoUnitarioItem () {
		pesoUnitarioItem.clear();
		for (Item item : items) {
			pesoUnitarioItem.put(item, item.calcularPesoTotal());
		}
		return pesoUnitarioItem;
	}
	
	/**
	 * Calcula el peso total de cada uno de los items del arreglo de items
	 * @return pesoUnitarioItem un HashMap con llave el item y valor su peso total
	 */
	public HashMap<Item, Double> calcularPesoTotalItem () {
		pesoTotalItem.clear();
		for (Item item : itemsCantidades.keySet()) {
			pesoTotalItem.put(item, item.calcularPesoTotal()*itemsCantidades.get(item));
		}
		return pesoTotalItem;
	}
	
	/**
	 * Verifica si ya existe un elemento identificado por el nombre
	 * @param nombre nombre de identificacion
	 * @return
	 * @throws ElementoRepetido 
	 */
	public Elemento tieneElemento (String nombre) throws ElementoRepetido {		
		for (Elemento elemento : elementos) {
			if (elemento.getNombre().equals(nombre)) {
				if (!elemento.getEdicion()) {
					throw new ElementoRepetido("Est� ingresando un elemento con el mismo nombre de otro ya existente");
				}
				return elemento;
			}
		}
		try {
			if (elementoActual.getEdicion()) {
				elementoActual.setNombre(nombre);
				return elementoActual;
			}
		} catch (NullPointerException e) {
		}
		return null;
	}
	
	/**
	 * Verifica si ya existe un item identificado por el nombre
	 * @param nombre nombre de identificacion
	 * @return
	 * @throws ItemRepetido 
	 */
	public Item tieneItem (String nombre) throws ItemRepetido {
		for (Item item : items) {
			if (item.getNombre().equals(nombre)) {
				if (!item.getEdicion()) {
					throw new ItemRepetido("Est� ingresando un item con el mismo nombre de otro ya existente");
				}
				return item;
			}
		}
		try {
			if (itemActual.getEdicion()) {
				itemActual.setNombre(nombre);
				return itemActual;
			}
		} catch (NullPointerException e) {
		}
		return null;
	}
	
	/**
	 * Verifica si alg�n item est� usando el elemento ingresado por par�metro
	 * @param elementoEnUso indica el elemento por el cual se va a preguntar
	 * @return false o true dependiendo del caso
	 */
	public boolean elementoEnUso (Elemento elementoEnUso) {
		for (Item item : items) {
			if (item.tieneElemento(elementoEnUso)) {
				return true;
			}
		}
		return false;
	}
	
	/*
     * Saves a file in java language
     * @param File to be saved 
     * @throws  CellularAutomataException 
     * In_Construccion if the function is not built.
     */
    public void imprima (File file, ArrayList<String> opcionesImpresion) throws IOException, ExtensionNoValidaDoc {
    	Cantidades proyecto = this;
    	String[] list = file.getName().split("\\.");    	
    	//if (!list[1].equals("doc")) throw new ExtensionNoValidaDoc("La extens�n permitida es .doc");
    	String ruta = file.getAbsolutePath();
    	
    	Document documento = new Document();
    	Section seccion = documento.addSection();
    	seccion.getPageSetup().setPageSize(PageSize.Letter);
    	seccion.getPageSetup().setOrientation(PageOrientation.Portrait);
    	seccion.getPageSetup().getMargins().setTop(17.9f);
    	seccion.getPageSetup().getMargins().setBottom(17.9f);
    	seccion.getPageSetup().getMargins().setLeft(15f);
    	seccion.getPageSetup().getMargins().setRight(15f);
    	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String fechaFinal = date.format(formatter);
    	ArrayList<String> fila1 = new ArrayList<String>();
    	datos.add(fila1);
    	LocalTime time = LocalTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
		String horaFinal = time.format(formatter2);
    	ArrayList<String> fila2 = new ArrayList<String>(Arrays.asList("Proyecto: " + nombre, horaFinal));
    	datos.add(fila2);
    	if (opcionesImpresion.contains("Resumen de acero por items")) {
    		fila1.clear();
    		fila1.add("MPA Ingenier�a y Construcci�n SAS");
    		fila1.add("Resumen de acero por items");
    		fila1.add(fechaFinal);
    		agregarTablaTitulos (seccion, fila1, fila2, "si");
    		imprimirResumenAceros(documento, seccion);
    	}
    	if (opcionesImpresion.contains("Cuadro de aceros")) {
    		fila1.clear();
    		fila1.add("MPA Ingenier�a y Construcci�n SAS");
    		fila1.add("Cuadro de aceros");
    		fila1.add(fechaFinal);
    		agregarTablaTitulos (seccion, fila1, fila2, "si");
    		imprimirCuadroDeAceros(documento, seccion);
    	}
    	if (opcionesImpresion.contains("Listado de items")) {
    		fila1.clear();
    		fila1.add("MPA Ingenier�a y Construcci�n SAS");
    		fila1.add("Listado de items");
    		fila1.add(fechaFinal);
    		agregarTablaTitulos (seccion, fila1, fila2, "si");
    		imprimirListadoDeItems(documento, seccion);	
    	}
    	if (opcionesImpresion.contains("Listado de elementos")) {
    		fila1.clear();
    		fila1.add("MPA Ingenier�a y Construcci�n SAS");
    		fila1.add("Listado de elementos");
    		fila1.add(fechaFinal);
    		agregarTablaTitulos (seccion, fila1, fila2, "si");
    		imprimirListadoDeElementos(documento, seccion);
    	}
    	if (opcionesImpresion.contains("<html><body>Resumen de acero<p> por items y diametros</body></html>")) {
    		fila1.clear();
    		fila1.add("MPA Ingenier�a y Construcci�n SAS");
    		fila1.add("Resumen de acero por items y diametros");
    		fila1.add(fechaFinal);
    		agregarTablaTitulos (seccion, fila1, fila2, "si");
    		imprimirResumenAcerosPorCalibre(documento, seccion);
    	}
    	documento.saveToFile(ruta + ".docx", FileFormat.Docx);
    }
    
    /**
     * Dibuja en word el cuadro de aceros
     * @param documento en el cual se dibujar�
     * @param seccion del documento
     */
    private void imprimirCuadroDeAceros (Document documento, Section seccion) {
    	for (Item item : items) {
        	HashMap<Item, Double> pesoUnitariPorItem = calcularPesoUnitarioItem();
        	Paragraph parrafo = seccion.addParagraph();
           	String itemIndice = String.valueOf(items.indexOf(item)+1);
        	String nombreItem = item.getNombre();
        	String cantidad = String.valueOf(itemsCantidades.get(item));
        	String pesoUnitario = String.format("%.2f", pesoUnitariPorItem.get(item));
           	TextRange range = parrafo.appendText("ITEM  N� - " + itemIndice + " NOMBRE - " + nombreItem + " CANTIDAD - " + cantidad + " PESO - " + pesoUnitario + "Kg/Item");
           	range.getCharacterFormat().setFontName("Arial");
           	range.getCharacterFormat().setFontSize(10f);
           	range.getCharacterFormat().setBold(true);
           	range.getCharacterFormat().setTextColor(Color.black);
           	TextRange range2 = parrafo.appendText("");
           	range2.getCharacterFormat().setFontSize(12f);
           	agregarEspacioEnBlanco(seccion);
           	agregarEspacioEnBlanco(seccion);
           	int indice = 0;
        	HashMap<Integer, HashMap<Varilla, Integer>> cantidadesVarillasTotales = item.calcularTotalesCantidadesVarillas();
        	for (int i = 10; i > 1; i--) {
        		indice = imprimirVarilla(cantidadesVarillasTotales.get(i), documento, seccion, itemsCantidades.get(item), indice);
        	}
        }
    }
    
    /**
     * Recibe las varillas de cada tipo de calibre
     * @param varillasImprimir Hashmap con la varilla y la cantidad de esta
     * @param documento en el cual se va a dibujar
     * @param seccion del documento
     * @param cantidadItem indica cuantas veces se tiene un item
     */
    private int imprimirVarilla (HashMap<Varilla, Integer> varillasImprimir, Document documento, Section seccion, int cantidadItem, int indice) {
    	ArrayList<Varilla> varillasImprimirOrden = new ArrayList<Varilla>();
    	for (Varilla varilla : varillasImprimir.keySet()) {
    		varillasImprimirOrden.add(varilla);
    	}
    	Collections.sort(varillasImprimirOrden, Comparator.comparing(Varilla::getLargo).
                thenComparing(Varilla::getTipo));
    	
    	for (int j = varillasImprimirOrden.size()-1; j > -1; j--) {
    		Varilla varilla = varillasImprimirOrden.get(j);
    		indice += 1;
    		imprimirVarillaTipo(varilla, varillasImprimir.get(varilla) * cantidadItem, documento, seccion, indice);
    	}
    	return indice;
    }
    
    /**
     * Dibuja una varilla en el word mirando que tipo de varilla es
     * @param varilla corresponde a la varilla a dibujar
     * @param cantidad es la cantidad que se tiene de la varilla
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param indice numero consecutivo de las varillas
     */
    private void imprimirVarillaTipo (Varilla varilla, int cantidad, Document documento, Section seccion, int indice) {
    	float largo = 17f*1000f/1200f;
    	String largoReal = String.valueOf(varilla.getLargo());
    	String ganchoReal = String.valueOf(varilla.getGancho());
    	String indiceS = String.valueOf(indice);
    	TextBox textField =  new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(indice + ")" + " " + String.valueOf(cantidad) + "#" + String.valueOf(varilla.getCalibre()) + "." + String.valueOf(varilla.getLongitud()));
    	text.getCharacterFormat().setFontName("Arial");
    	text.getCharacterFormat().setFontSize(8f);
    	text.getCharacterFormat().setTextColor(Color.black);
    	setTextBoxColor(textField);
    	if (varilla.getTipo() == 0) {
    		varillaTipo0(documento, seccion, largo, largoReal, textField);
        	agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 1) {
    		varillaTipo1(documento, seccion, largo, 0.5f, largoReal, ganchoReal, textField);
        	agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 2) {
    		varillaTipo2(documento, seccion, largo, 0.5f, largoReal, ganchoReal, textField);
        	agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 3) {
    		varillaTipo3(documento, seccion, largo, 0.5f, 0.5f, largoReal, ganchoReal, textField);
    		agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 4) {
    		varillaTipo4(documento, seccion, largo, 0.5f, 0.5f, largoReal, ganchoReal, textField);
        	agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 5) {
    		float altoFleje = (8.5f*209)/594f;
    		String altoReal = String.valueOf(varilla.getAlto());
    		largo = 17f*141f/1200f;
    		varillaTipo5(documento, seccion, altoFleje, largo, altoReal, largoReal, ganchoReal, textField);
    		for (int i = 0; i < largo*2; i++) {
    			agregarEspacioEnBlanco(seccion);
    		}
    	} else if (varilla.getTipo() == 6) {
    		largo = 17f*141f/1200f;
    		varillaTipo6(documento, seccion, largo, largo, largoReal, ganchoReal, textField);
    		for (int i = 0; i < largo*2; i++) {
    			agregarEspacioEnBlanco(seccion);
    		}
    	} else if (varilla.getTipo() == 7) {
    		varillaTipo7(documento, seccion, largo, largoReal, textField);
    		agregarEspacioEnBlanco(seccion);
    	} else if (varilla.getTipo() == 8) {
    		varillaTipo8(documento, seccion, largo, 0.5f, 0.5f, largoReal, ganchoReal, textField);
    		agregarEspacioEnBlanco(seccion);
    	}
    }
    
    /**
     * Dibuja en el word una varilla de tipo0
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo0 (Document documento, Section seccion, float largo, String largoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f + 2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    }
    
    /**
     * Dibuja una varilla tipo 1
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo1 (Document documento, Section seccion, float largo, float alto, String largoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, 0.23f + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f + 2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    }
    
    /**
     * Dibuja una varilla tipo 2
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo2 (Document documento, Section seccion, float largo, float alto, String largoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) +2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, 0.23f + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	TextBox textField3 = new TextBox(documento);
    	Paragraph texto3 = textField3.getBody().addParagraph();
    	TextRange text3 = texto3.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, largo+3.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField3, text3);
    	group.getChildObjects().add((ShapeObject) textField3);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setHeight(alto*conversionAltoDentro);
    	line3.setHorizontalPosition((1.15f + largo +2f)*conversionAnchoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    }
    
    /**
     * Dibuja una varilla tipo 3
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param gancho con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo3 (Document documento, Section seccion, float largo, float alto, float gancho, String largoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, 1f + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setWidth(gancho*conversionAnchoDentro);
    	line3.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line3.setVerticalPosition(alto*conversionAltoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    }
    
    /**
     * Dibuja una varilla tipo 4
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param gancho con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo4 (Document documento, Section seccion, float largo, float alto, float gancho, String largoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, 1f + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	TextBox textField3 = new TextBox(documento);
    	Paragraph texto3 = textField3.getBody().addParagraph();
    	TextRange text3 = texto3.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, (largo-gancho-0.5f +3.15f), 0.02f, conversionAnchoDentro, conversionAltoDentro, textField3, text3);
    	group.getChildObjects().add((ShapeObject) textField3);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setHeight(alto*conversionAltoDentro);
    	line3.setHorizontalPosition((largo+3.15f)*conversionAnchoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    	ShapeObject line4 = new ShapeObject(documento, ShapeType.Line);
    	line4.setWidth(gancho*conversionAnchoDentro);
    	line4.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line4.setVerticalPosition(alto*conversionAltoDentro);
    	line4.setStrokeWeight(1.5);
    	group.getChildObjects().add(line4);
    	ShapeObject line5 = new ShapeObject(documento, ShapeType.Line);
    	line5.setWidth(gancho*conversionAnchoDentro);
    	line5.setVerticalPosition(alto*conversionAltoDentro);
    	line5.setHorizontalPosition((largo - gancho +3.15f)*conversionAnchoDentro);
    	line5.setStrokeWeight(1.5);
    	group.getChildObjects().add(line5);
    }

    /**
     * Dibuja una varilla tipo 5
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo5 (Document documento, Section seccion, float largo, float alto, String anchoReal, String altoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 200f/1.7f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, 8.5f*conversionTamanosWord);
    	
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(anchoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) +2.65f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(altoReal);
    	setCompleteTxtBox(0.69f, 1.2f, 0.23f + 2.15f, alto/2f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	TextBox textField3 = new TextBox(documento);
    	Paragraph texto3 = textField3.getBody().addParagraph();
    	TextRange text3 = texto3.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, largo+3.15f, 0.0f, conversionAnchoDentro, conversionAltoDentro, textField3, text3);
    	group.getChildObjects().add((ShapeObject) textField3);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setWidth(largo*conversionAnchoDentro);
    	line3.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line3.setVerticalPosition(alto*conversionAltoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    	ShapeObject line4 = new ShapeObject(documento, ShapeType.Line);
    	line4.setHeight(alto*conversionAltoDentro);
    	line4.setHorizontalPosition((1.15f + largo +2f)*conversionAnchoDentro);
    	line4.setStrokeWeight(1.5);
    	group.getChildObjects().add(line4);
    	ShapeObject line5 = new ShapeObject(documento, ShapeType.Line);
    	line5.setHeight(0.2f*conversionAltoDentro);
    	line5.setHorizontalPosition((1.15f + largo - 0.3f +2f)*conversionAnchoDentro);
    	line5.setStrokeWeight(1.5);
    	group.getChildObjects().add(line5);
    	ShapeObject line6 = new ShapeObject(documento, ShapeType.Line);
    	line6.setWidth(0.2f*conversionAnchoDentro);
    	line6.setHorizontalPosition((1.15f + largo - 0.25f +2f)*conversionAnchoDentro);
    	line6.setVerticalPosition(0.2f*conversionAltoDentro);
    	line6.setStrokeWeight(1.5);
    	group.getChildObjects().add(line6);
    }
    
    /**
     * Dibuja una varilla tipo 6
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param calibreReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo6 (Document documento, Section seccion, float largo, float alto, String largoReal, String calibreReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 200f/1.7f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, 8.5f*conversionTamanosWord);
    	
    	
    	TextBox textField1 = new TextBox(documento);
    	Paragraph texto1 = textField1.getBody().addParagraph();
    	TextRange text1 = texto1.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, 2.15f, (alto/2f), conversionAnchoDentro, conversionAltoDentro, textField1, text1);
    	group.getChildObjects().add((ShapeObject) textField1);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(calibreReal);
    	setCompleteTxtBox(0.69f, 1.12f, (1.15f + largo +2.15f), 0, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Ellipse);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHeight(largo*conversionAltoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(0.3f*conversionAnchoDentro);
    	line2.setHorizontalPosition((1.15f + largo/2f - 0.2f +2f)*conversionAnchoDentro);
    	group.getChildObjects().add(line2);
    	line2.setStrokeWeight(1.5);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setHeight(0.3f*conversionAnchoDentro);
    	line3.setHorizontalPosition((1.15f + largo/2f + 0.2f +2f)*conversionAnchoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    }
    
    /**
     * Dibuja en el word una varilla de tipo7
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo7 (Document documento, Section seccion, float largo, String largoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal + " Figurar en obra");
    	setCompleteTxtBox(0.69f, 6.6f, (largo/2f) +2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro,  textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    }
    
    /**
     * Dibuja una varilla tipo 8
     * @param documento indica el objeto tipo Document en el cual se esta dibujando
     * @param seccion es la seccion del documento
     * @param largo con el cual se va a dibujar la varilla en cm
     * @param alto con el cual se va a dibujar la varilla en cm
     * @param gancho con el cual se va a dibujar la varilla en cm
     * @param largoReal texto que va a aparecer en el dibujo que corresponde al largo real
     * @param ganchoReal texto que va a aprecer en el dibujo que corresponde al gancho real
     * @param titulo informacion de varilla que contiene cantidad, calibre y largo
     */
    private void varillaTipo8 (Document documento, Section seccion, float largo, float alto, float gancho, String largoReal, String ganchoReal, TextBox titulo) {
    	Paragraph parrafo = seccion.addParagraph();
    	float pageWidth = seccion.getPageSetup().getClientWidth();
    	float conversionTamanosWord = pageWidth/20.53f;
    	float conversionAnchoDentro = 17f/0.29f;
    	float conversionAltoDentro = 1000f;
    	ShapeGroup group = parrafo.appendShapeGroup(17f*conversionTamanosWord, conversionTamanosWord);
    	
    	TextBox textField = new TextBox(documento);
    	Paragraph texto = textField.getBody().addParagraph();
    	TextRange text = texto.appendText(largoReal);
    	setCompleteTxtBox(0.69f, 1.2f, (largo/2f) + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField, text);
    	group.getChildObjects().add((ShapeObject) textField);
    	TextBox textField2 = new TextBox(documento);
    	Paragraph texto2 = textField2.getBody().addParagraph();
    	TextRange text2 = texto2.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, 1f + 2.15f, 0.02f, conversionAnchoDentro, conversionAltoDentro, textField2, text2);
    	group.getChildObjects().add((ShapeObject) textField2);
    	TextBox textField3 = new TextBox(documento);
    	Paragraph texto3 = textField3.getBody().addParagraph();
    	TextRange text3 = texto3.appendText(ganchoReal);
    	setCompleteTxtBox(0.69f, 1.12f, (largo + 3.15f), 0.02f, conversionAnchoDentro, conversionAltoDentro, textField3, text3);
    	group.getChildObjects().add((ShapeObject) textField3);
    	setTitulosVarillas(titulo, conversionAltoDentro, conversionAnchoDentro);
    	group.getChildObjects().add((ShapeObject) titulo);
    	
    	ShapeObject line1 = new ShapeObject(documento, ShapeType.Line);
    	line1.setWidth(largo*conversionAnchoDentro);
    	line1.setHorizontalPosition((1.15f + 2f)*conversionAnchoDentro);
    	line1.setStrokeWeight(1.5);
    	group.getChildObjects().add(line1);
    	ShapeObject line2 = new ShapeObject(documento, ShapeType.Line);
    	line2.setHeight(alto*conversionAltoDentro);
    	line2.setHorizontalPosition((1.15f + 2f)*conversionAnchoDentro);
    	line2.setStrokeWeight(1.5);
    	group.getChildObjects().add(line2);
    	ShapeObject line3 = new ShapeObject(documento, ShapeType.Line);
    	line3.setHeight(alto*conversionAltoDentro);
    	line3.setHorizontalPosition((1.15f+largo+2f)*conversionAnchoDentro);
    	line3.setStrokeWeight(1.5);
    	group.getChildObjects().add(line3);
    	ShapeObject line4 = new ShapeObject(documento, ShapeType.Line);
    	line4.setWidth(gancho*conversionAnchoDentro);
    	line4.setVerticalPosition(alto*conversionAltoDentro);
    	line4.setHorizontalPosition((1.15f+2f)*conversionAnchoDentro);
    	line4.setStrokeWeight(1.5);
    	group.getChildObjects().add(line4);
    }
    
    private void setCompleteTxtBox (float altura, float anchura, float posX, float posY, float conversionAnchoDentro, float conversionAltoDentro, TextBox textField, TextRange text) {
		text.getCharacterFormat().setFontName("Arial");
    	text.getCharacterFormat().setFontSize(8f);
    	text.getCharacterFormat().setTextColor(Color.black);
    	setTextBoxColor(textField);
    	textField.setHeight(altura*conversionAltoDentro);
    	textField.setWidth(anchura*conversionAnchoDentro);
    	textField.setHorizontalPosition(posX*conversionAnchoDentro);
    	textField.setVerticalPosition(posY*conversionAltoDentro);
	}
    
    private void setTextBoxColor (TextBox text) {
		text.getFormat().getFillEffects().setType(BackgroundType.No_Background);
    	text.getFormat().setNoLine(true);
    }
    
    private void setTitulosVarillas (TextBox titulo, float conversionAltoDentro, float conversionAnchoDentro) {
    	setTextBoxColor(titulo);
    	titulo.setHeight(0.69f*conversionAltoDentro);
    	titulo.setWidth(3.5f*conversionAnchoDentro);
    }
    
    
    /**
     * Imprimela tabla de resumen de items por calibre
     * @param documento
     * @param seccion
     */
    private void imprimirResumenAcerosPorCalibre (Document documento, Section seccion) {
    	ArrayList<String> cabecero = new ArrayList<String>(Arrays.asList("Item", "CT", "#2", "#3", "#4", "#5", "#6", "#7", "#8", "#9", "#10", "Total"));
    	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
    	HashMap<Integer, Double> pesosCalibresTotales = new HashMap<Integer, Double>();
    	Double pesoTotalTotales = 0.0;
    	for (int i = 2; i < 11; i++) {
    		pesosCalibresTotales.put(i, 0.0);
    	}
    	for (Item item : items) {
    		ArrayList<String> datosItem = new ArrayList<String>();
    		double pesoTotalItem = 0;
    		HashMap<Integer, Double> pesosPorCalibre = item.calcularPesoPorCalibre();
    		String nombreItem = item.getNombre();
    		String cantidad = String.valueOf(itemsCantidades.get(item));
    		datosItem.add(nombreItem);
    		datosItem.add(cantidad);
    		for (int i = 2; i < 11; i++) {
    			Double pesoDeCalibreItem = pesosPorCalibre.get(i) * itemsCantidades.get(item);
    			String peso = String.format("%.2f", pesoDeCalibreItem);
    			datosItem.add(peso);
    			pesoTotalItem += pesoDeCalibreItem;
    			Double pesoTotalCalibre = pesosCalibresTotales.get(i);
    			pesoTotalCalibre += pesoDeCalibreItem;
    			pesosCalibresTotales.replace(i, pesoTotalCalibre);
    		}
    		pesoTotalTotales += pesoTotalItem;
    		String pesoStrItemTotal = String.format("%.2f", pesoTotalItem);
    		datosItem.add(pesoStrItemTotal);
    		datos.add(datosItem);
    	}
    	ArrayList<String> ultimaFila = new ArrayList<String>();
    	ultimaFila.add("TOTAL");
    	ultimaFila.add("");
    	for (int i = 2; i < 11; i++) {
    		String peso = String.format("%.2f", pesosCalibresTotales.get(i));
			ultimaFila.add(peso);
    	}
    	ultimaFila.add(String.format("%.2f", pesoTotalTotales));
    	datos.add(ultimaFila);
    	agregarTabla(seccion, cabecero, datos, "si");
    }
    
    /**
     * Imprime el listado total de varilla de cada elemento
     * @param documento
     * @param seccion
     */
    private void imprimirListadoDeElementos (Document documento, Section seccion) {
    	for (Elemento elemento : elementos) {
    		imprimirVarillasDeUnElemento (documento, seccion, elemento);
    	}
    		
    }
    
    /**
     * Imprime las varillas de un solo elemento
     * @param documento en el cual se van a imprimir
     * @param elemento del cual se obtiene la informaci�n
     */
    private void imprimirVarillasDeUnElemento (Document documento, Section seccion, Elemento elemento) {
		Paragraph parrafo = seccion.addParagraph();
    	String elementoIndice = String.valueOf(elementos.indexOf(elemento)+1);
		String nombreElemento = elemento.getNombre();
		String pesoUnitario = String.format("%.2f", elemento.calcularPesoTotal());
		TextRange range = parrafo.appendText("N� - " + elementoIndice + " NOMBRE - " + nombreElemento +  " PESO - " + pesoUnitario + "Kg");
    	range.getCharacterFormat().setFontName("Arial");
    	range.getCharacterFormat().setFontSize(10f);
    	range.getCharacterFormat().setBold(true);
    	range.getCharacterFormat().setTextColor(Color.black);
    	
    	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
    	ArrayList<String> textoDeVarillas = new ArrayList<String>();
    	datos.add(textoDeVarillas);
		ArrayList<Varilla> varillasDeElemento = elemento.getVarillasEnOrden();
		HashMap<Varilla, Integer> varillasCantidades = elemento.getVarillas();
		Varilla varillaActual;
		for (int i = 0; i < varillasDeElemento.size(); i++) {
			if (((i % 5) == 0) && (i != 0)) {
				textoDeVarillas = new ArrayList<String>();
				datos.add(textoDeVarillas);
			}
			varillaActual = varillasDeElemento.get(i);
			String cantidadVarilla = String.valueOf(varillasCantidades.get(varillaActual));
    		String calibreVarilla = String.valueOf(varillaActual.getCalibre());
    		String longitudVarilla = String.valueOf(varillaActual.getLongitud());
    		String tipoVarilla = hallarTipoVarilla(varillaActual);
    		String indice = String.valueOf(i + 1);
    		String text = indice + ")  " + cantidadVarilla + "#" + calibreVarilla + "." + longitudVarilla + "-" + tipoVarilla;
    		textoDeVarillas.add(text);
		}
		ArrayList<String> cabecero = new ArrayList<String>();
		for (int i = 0 ; i < datos.get(0).size(); i++) {
			cabecero.add("");
		}
		agregarTabla(seccion, cabecero, datos, "no");
    }
	
    /**
     * Imprime el listado total de elementos de cada item
     * @param documento indica el documento sobre el cual se va a escribir
     * @param seccion indica la seccion a la que se le agrega la tabla
     */
    private void imprimirListadoDeItems (Document documento, Section seccion) {
    	for (Item item : items) {
    		imprimirUnItemDeListadoDeItems(documento, seccion, item);
    	}
    }
    
    /**
     * Escribe una tabla de un item de listado de items es decir los elementos de un item
     * @param documento indica el documento sobre el cual se va a escribir
     * @param seccion indica la seccion a la que se le agrega la tabla
     * @param item corresponde al item al cual se le va a obtener las info
     */
    private void imprimirUnItemDeListadoDeItems (Document documento, Section seccion, Item item) {
    	HashMap<Item, Double> pesoUnitariPorItem = calcularPesoUnitarioItem();
    	Paragraph parrafo = seccion.addParagraph();
    	String itemIndice = String.valueOf(items.indexOf(item)+1);
		String nombreItem = item.getNombre();
		String cantidad = String.valueOf(itemsCantidades.get(item));
		String pesoUnitario = String.format("%.2f", pesoUnitariPorItem.get(item));
    	TextRange range = parrafo.appendText("ITEM  N� - " + itemIndice + " NOMBRE - " + nombreItem + " CANTIDAD - " + cantidad + " PESO - " + pesoUnitario + "Kg/Item");
    	range.getCharacterFormat().setFontName("Arial");
    	range.getCharacterFormat().setFontSize(10f);
    	range.getCharacterFormat().setBold(true);
    	range.getCharacterFormat().setTextColor(Color.black);
    	TextRange range2 = parrafo.appendText("");
    	range2.getCharacterFormat().setFontSize(12f);
    	agregarEspacioEnBlanco(seccion);
    	agregarEspacioEnBlanco(seccion);
    	
    	ArrayList<String> cabecero = new ArrayList<String>(Arrays.asList("N�", "Nombre", "Cantidad", "Peso Unitario", "Peso Total"));
    	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
    	HashMap<Elemento, Double> pesoUnitarioPorElemento = item.calcularPesoUnitarioELemento();
    	HashMap<Elemento, Double> pesoTotalPorElemento = item.calcularPesoTotalElemento();
    	ArrayList<Elemento> elementosDeItem = item.getElementosEnOrden();
    	HashMap<Elemento, Integer> elementosCantidades = item.getElementos();
    	for (Elemento elemento : elementosDeItem) {
    		ArrayList<String> datosElemento = new ArrayList<String>();
    		String elementoIndice = String.valueOf(elementosDeItem.indexOf(elemento)+1);
    		String nombreElemento = elemento.getNombre();
    		String cantidadElemento = String.valueOf(elementosCantidades.get(elemento));
    		String pesoUnitarioElemento = String.format("%.2f", pesoUnitarioPorElemento.get(elemento));
    		String pesoTotalElemento = String.format("%.2f", pesoTotalPorElemento.get(elemento));
    		datosElemento.add(elementoIndice);
    		datosElemento.add(nombreElemento);
    		datosElemento.add(cantidadElemento);
    		datosElemento.add(pesoUnitarioElemento);
    		datosElemento.add(pesoTotalElemento);
    		datos.add(datosElemento);
    	}
    	
    	agregarTabla(seccion, cabecero, datos, "no");
    }

    
    /**
     * Escribe la tabla de resumen de aceros por item
     * @param documento indica el documento sobre el cual se va a escribir
     * @param seccion indica la seccion a la que se le agrega la tabla
     */
    private void imprimirResumenAceros (Document documento, Section seccion) {
    	ArrayList<String> cabecero = new ArrayList<String>(Arrays.asList("Item", "Nombre", "Cantidad", "Peso Unitario", "Peso Total"));
    	ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
    	HashMap<Item, Double> pesoUnitarioPorItem = calcularPesoUnitarioItem();
    	HashMap<Item, Double> pesoTotalPorItem = calcularPesoTotalItem();
    	for (Item item : items) {
    		ArrayList<String> datosItem = new ArrayList<String>();
    		String itemIndice = String.valueOf(items.indexOf(item)+1);
    		String nombreItem = item.getNombre();
    		String cantidad = String.valueOf(itemsCantidades.get(item));
    		String pesoUnitario = String.format("%.2f", pesoUnitarioPorItem.get(item));
    		String pesoTotal = String.format("%.2f", pesoTotalPorItem.get(item));
    		datosItem.add(itemIndice);
    		datosItem.add(nombreItem);
    		datosItem.add(cantidad);
    		datosItem.add(pesoUnitario);
    		datosItem.add(pesoTotal);
    		datos.add(datosItem);
    	}
    	agregarTabla(seccion, cabecero, datos, "si");
    }
    
    /**
     * Agrega una tabla a la seccion mandada
     * @param seccion corresponde a la seccion a la cual se le agrega la tabla
     * @param cabecero titulos de tabla
     * @param datos matriz de datos
     */
    private void agregarTabla (Section seccion, ArrayList<String> cabecero, ArrayList<ArrayList<String>> datos, String conLineas) {
		//Agregar tabla
    	Table tabla = seccion.addTable(true);
    	tabla.resetCells(datos.size() + 1, cabecero.size());
    	// Establece la primera fila de la tabla como encabezado, escribe el contenido de la matriz de encabezado y formatea los datos del encabezado
    	TableRow fila = tabla.getRows().get(0);
    	fila.isHeader(true);
    	fila.setHeight(18);
    	fila.setHeightType(TableRowHeightType.Exactly);
    	fila.getRowFormat().setBackColor(Color.white);
    	//Agrega los titulos de la tabla
    	for (int i = 0; i < cabecero.size(); i++) {
    	    fila.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
    	    Paragraph p = fila.getCells().get(i).addParagraph();
    	    p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
    	    TextRange range1 = p.appendText(cabecero.get(i));
    	    range1.getCharacterFormat().setFontName("Arial");
    	    range1.getCharacterFormat().setFontSize(10f);
    	    range1.getCharacterFormat().setBold(true);
    	    range1.getCharacterFormat().setTextColor(Color.black);
    	}
    	// Escriba el contenido restante del grupo en la tabla y formatee los datos
    	for (int r = 0; r < datos.size(); r++) {
    	    TableRow dataRow = tabla.getRows().get(r + 1);
    	    dataRow.setHeight(18);
    	    dataRow.setHeightType(TableRowHeightType.Exactly);
    	    dataRow.getRowFormat().setBackColor(Color.white);
    	    for (int c = 0; c < datos.get(r).size(); c++) {
    	        dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
    	        TextRange range2 = dataRow.getCells().get(c).addParagraph().appendText(datos.get(r).get(c));
        	    range2.getOwnerParagraph().getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
        	    range2.getCharacterFormat().setFontName("Arial");
        	    range2.getCharacterFormat().setFontSize(10f);
    	    }
    	}
    	
    	if (conLineas.equals("no")) {
    		tabla.getTableFormat().getBorders().getTop().setColor(Color.WHITE);
    		tabla.getTableFormat().getBorders().getLeft().setColor(Color.WHITE);
    		tabla.getTableFormat().getBorders().getBottom().setColor(Color.WHITE);
    		tabla.getTableFormat().getBorders().getRight().setColor(Color.WHITE);
    		tabla.getTableFormat().getBorders().getVertical().setColor(Color.WHITE);
    		tabla.getTableFormat().getBorders().getHorizontal().setColor(Color.WHITE);
    	}
    	tabla.autoFit(AutoFitBehaviorType.Auto_Fit_To_Contents);
    	tabla.getTableFormat().setHorizontalAlignment(RowAlignment.Center);
    	
    	agregarEspacioEnBlanco(seccion);
		agregarEspacioEnBlanco(seccion);
    	
	}
    
    /**
     * Agrega una tabla a la seccion mandada
     * @param seccion corresponde a la seccion a la cual se le agrega la tabla
     * @param cabecero titulos de tabla
     * @param datos matriz de datos
     * @param conLineas si desea que la tabla no tenga lineas
     */
    private void agregarTablaTitulos (Section seccion, ArrayList<String> cabecero, ArrayList<String> datos, String conLineas) {
		//Agregar tabla
    	Table tabla = seccion.addTable(true);
    	tabla.resetCells(2, 3);
    	tabla.applyVerticalMerge(1, 0, 1);
    	// Establece la primera fila de la tabla como encabezado, escribe el contenido de la matriz de encabezado y formatea los datos del encabezado
    	TableRow fila = tabla.getRows().get(0);
    	fila.isHeader(true);
    	fila.setHeight(18);
    	fila.setHeightType(TableRowHeightType.Exactly);
    	fila.getRowFormat().setBackColor(Color.white);
    	//Agrega los titulos de la tabla
    	for (int i = 0; i < cabecero.size(); i++) {
    	    fila.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
    	    Paragraph p = fila.getCells().get(i).addParagraph();
    	    p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
    	    TextRange range1 = p.appendText(cabecero.get(i));
    	    range1.getCharacterFormat().setFontName("Arial");
    	    range1.getCharacterFormat().setFontSize(10f);
    	    range1.getCharacterFormat().setBold(true);
    	    range1.getCharacterFormat().setTextColor(Color.black);
    	}
    	TableRow dataRow = tabla.getRows().get(1);
    	dataRow.setHeight(18);
    	dataRow.setHeightType(TableRowHeightType.Exactly);
    	dataRow.getRowFormat().setBackColor(Color.white);
    	dataRow.getCells().get(0).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
    	dataRow.getCells().get(2).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
    	Paragraph p = dataRow.getCells().get(0).addParagraph();
	    p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
	    TextRange range1 = p.appendText(datos.get(0));
	    range1.getCharacterFormat().setFontName("Arial");
	    range1.getCharacterFormat().setFontSize(10f);
	    range1.getCharacterFormat().setBold(true);
	    range1.getCharacterFormat().setTextColor(Color.black);
	    Paragraph p2 = dataRow.getCells().get(2).addParagraph();
	    p2.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
	    TextRange range2 = p2.appendText(datos.get(1));
	    range2.getCharacterFormat().setFontName("Arial");
	    range2.getCharacterFormat().setFontSize(10f);
	    range2.getCharacterFormat().setBold(true);
	    range2.getCharacterFormat().setTextColor(Color.black);
    	
    	tabla.getTableFormat().setHorizontalAlignment(RowAlignment.Center);
    	
    	tabla.getTableFormat().getBorders().getTop().setColor(Color.BLACK);
		tabla.getTableFormat().getBorders().getLeft().setColor(Color.WHITE);
		tabla.getTableFormat().getBorders().getBottom().setColor(Color.BLACK);
		tabla.getTableFormat().getBorders().getRight().setColor(Color.WHITE);
		tabla.getTableFormat().getBorders().getVertical().setColor(Color.WHITE);
		tabla.getTableFormat().getBorders().getHorizontal().setColor(Color.WHITE);
		tabla.getTableFormat().getBorders().getTop().setBorderType(BorderStyle.Dash_Large_Gap);
		tabla.getTableFormat().getBorders().getBottom().setBorderType(BorderStyle.Dash_Large_Gap);

    	
    	agregarEspacioEnBlanco(seccion);
		agregarEspacioEnBlanco(seccion);
    	
	}
    
    /**
     * Guarda en lenguaje de java
     * @param file para ser guardado
     */
    public void guarde(File file) {
    	this.file = file;
    }
    
    /**
     * Guarda en lenguaje de java
     * @throws  IOException
     * In_Construccion if the function is not built.
     */
    public void guardeAntesDeSalir () throws IOException {
    	Cantidades proyecto = this;
    	String ruta = file.getAbsolutePath();
    	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + ".dat"))) {
    		oos.writeObject(proyecto);
    	} catch (IOException e) {
    		throw e;
    	}
    }
    
    /*
     * Abre un cantidades que est� en lenguaje de java
     * @param File to open
     */
    public static Cantidades abra(File file) throws Exception{
    	try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file.getPath()))) {
			return (Cantidades)(reader.readObject());
		} catch (Exception ex) {
			throw ex;
		}    	
    }
    
    /**
     * Agrega un enter
     */
    private void agregarEspacioEnBlanco (Section seccion) {
    	Paragraph parrafo = seccion.addParagraph();
		TextRange range = parrafo.appendText("  ");
    	range.getCharacterFormat().setFontSize(10f);
    	TextRange range2 = parrafo.appendText("  ");
    	range2.getCharacterFormat().setFontSize(10f);
    }
    
    /**
	 * Retorna el valor en stirng del tipo de varilla
	 * @param varilla varilla a la cual se le quiere saber el tipo
	 * @return
	 */
	private String hallarTipoVarilla (Varilla varilla) {
		if (varilla instanceof Tipo0) {
			return "0";
		} if (varilla instanceof Tipo1) {
			return "1";
		} if (varilla instanceof Tipo2) {
			return "2";
		} if (varilla instanceof Tipo3) {
			return "3";
		} if (varilla instanceof Tipo4) {
			return "4";
		} if (varilla instanceof Tipo5) {
			return "5";
		} if (varilla instanceof Tipo6) {
			return "6";
		} if (varilla instanceof Tipo7) {
			return "7";
		} else {
			return "8";
		}
	}
    
	public String getNombre () {
		return nombre;
	}
	
	public Item getItemActual () {
		return itemActual;
	}
	
	public Elemento getElementoActual () {
		return elementoActual;
	}
	
	public ArrayList<Elemento> getElementos () {
		return elementos;
	}
	
	public ArrayList<Item> getItems () {
		return items;
	}
	
	public HashMap<Item, Integer> getItemCantidades () {
		return itemsCantidades;
	}

	public Cantidades getInstancia() {
		return instancia;
	}

	public void setInstancia(Cantidades instancia) {
		this.instancia = instancia;
	}
	
	public void setItemCantidad (Item item, int cant) {
		itemsCantidades.put(item, cant);
	}
}
