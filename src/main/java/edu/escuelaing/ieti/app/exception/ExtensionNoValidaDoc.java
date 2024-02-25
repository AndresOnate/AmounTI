package edu.escuelaing.ieti.app.exception;

/**
 * ExtensionNoValida es una excepcion que trata si el usuario ingresa la extensi�n distinta a .doc para imprimir
 * @author Santiago Ar�valo Rojas
 * @version 1.0. (29 Octubre 2022)
 */
public class ExtensionNoValidaDoc extends CantidadesExcepciones {
	public ExtensionNoValidaDoc(String message) {
		super(message);
	}
}
