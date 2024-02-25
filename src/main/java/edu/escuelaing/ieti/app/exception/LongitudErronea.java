package edu.escuelaing.ieti.app.exception;
/**
 * LongitudErronea es una excepcion que trata si el usuario ingresa una longitud <0 o >1200
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (29 Octubre 2022)
 */
public class LongitudErronea extends CantidadesExcepciones{
	public LongitudErronea(String message) {
		super(message);
	}
}
