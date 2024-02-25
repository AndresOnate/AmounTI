package edu.escuelaing.ieti.app.exception;

/**
 * CalibreNoExiste es una excepcion que trata si el usuario ingresa un calibre inexistente
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (27 Octubre 2022)
 */
public class CalibreNoExiste extends CantidadesExcepciones{
	public CalibreNoExiste(String message) {
		super(message);
	}
}
