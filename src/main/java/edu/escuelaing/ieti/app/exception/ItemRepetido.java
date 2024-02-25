package edu.escuelaing.ieti.app.exception;

/**
 * ItemRepetido es una excepcion que trata si el usuario ingresa un item con el mismo nombre de otro
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (12 Octubre 2022)
 */
public class ItemRepetido extends CantidadesExcepciones{
	public ItemRepetido(String message) {
		super(message);
	}
}
