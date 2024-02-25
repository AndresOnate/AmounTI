package edu.escuelaing.ieti.app.exception;

/**
 * LargoErroneo es una excepcion que trata si el usuario ingresa un largo <= 0 o >longitud-1
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (29 Octubre 2022)
 */
public class LargoErroneo extends CantidadesExcepciones{
	public LargoErroneo(String message) {
		super(message);
	}
}
