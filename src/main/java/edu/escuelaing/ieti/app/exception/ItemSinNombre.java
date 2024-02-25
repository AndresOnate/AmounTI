package edu.escuelaing.ieti.app.exception;

/**
 * ItemSinNombre es una excepcion que trata si el usuario no ingresa nombre al item
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (27 Octubre 2022)
 */
public class ItemSinNombre extends CantidadesExcepciones{
	public ItemSinNombre(String message) {
		super(message);
	}
}
