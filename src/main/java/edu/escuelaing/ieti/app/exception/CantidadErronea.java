package edu.escuelaing.ieti.app.exception;

/**
 * CantidadErronea es una excepcion que trata si el usuario ingresa una cantidad <= 0
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (29 Octubre 2022)
 */
public class CantidadErronea extends CantidadesExcepciones{
	public CantidadErronea(String message) {
		super(message);
	}
}
