package edu.escuelaing.ieti.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import edu.escuelaing.ieti.app.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CantidadesTest {
	//Pruebas creación de tipos de varillas
	private Tipo0 tipo0;
	private Tipo1 tipo1;
	private Tipo2 tipo2;
	private Tipo3 tipo3;
	private Tipo4 tipo4;
	private Tipo5 tipo5;
	private Tipo6 tipo6;
	private Tipo7 tipo7;
	private Tipo8 tipo8;
	
	//Pruebas creación de elementos y cálculo de pesos
	private Elemento vg01;
	private Tipo5 v0;
	private Tipo2 v1;
	private Tipo2 v2;
	private Tipo0 v3;
	
	//Pruebas creación de Items y cálculo de pesos
	private Item piso1;
	
	//Pruebas calcular totales de varillas
	private Tipo2 v4;
	private Tipo2 v5;
	
	@BeforeEach
	public void configuracion () {
		//
		tipo0 = new Tipo0(5, 800);
		tipo1 = new Tipo1(5, 800);
		tipo2 = new Tipo2(5, 800);
		tipo3 = new Tipo3(5, 800);
		tipo4 = new Tipo4(5, 800);
		tipo5 = new Tipo5(3, 178, 37);
		tipo6 = new Tipo6(3, 170);
		tipo7 = new Tipo7(5, 800);
		tipo8 = new Tipo8(5, 800);
		//
		vg01 = new Elemento("vg01");
		v0 = new Tipo5(3, 178, 37);
		v1 = new Tipo2(5, 900);
		v2 = new Tipo2(5, 900);
		v3 = new Tipo0(6, 600);
		//
		piso1 = new Item("Piso 1");
		//
		v4 = new Tipo2(8, 950);
		v5 = new Tipo2(8, 950);
	}
	
	@Test
	public void debeIngresarCalibres () {
		assertEquals("{2=5, 3=10, 4=15, 5=20, 6=25, 7=30, 8=35, 9=40, 10=45, 11=50}",tipo0.getCalibres().toString());
	}
	
	@Test
	public void debeCalcularMedidasT0 () {
		assertEquals(800, tipo0.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT1 () {
		assertEquals(20, tipo1.getGancho());
		assertEquals(780, tipo1.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT2 () {
		assertEquals(20, tipo2.getGancho());
		assertEquals(760, tipo2.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT3 () {
		assertEquals(20, tipo3.getGancho());
		assertEquals(780, tipo3.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT4 () {
		assertEquals(20, tipo4.getGancho());
		assertEquals(760, tipo4.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT5 () {
		assertEquals(10, tipo5.getGancho());
		assertEquals(37, tipo5.getLargo());
		assertEquals(42, tipo5.getAlto());
		
	}
	
	@Test
	public void debeCalcularMedidasT6 () {
		assertEquals(10, tipo6.getGancho());
		assertEquals(150, tipo6.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT7 () {
		assertEquals(800, tipo7.getLargo());
	}
	
	@Test
	public void debeCalcularMedidasT8 () {
		assertEquals(20, tipo8.getGancho());
		assertEquals(760, tipo8.getLargo());
	}
	
	@Test
	public void debeAgregarVarillas () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		HashMap<Varilla, Integer> varillasPrueba = vg01.getVarillas();
		boolean prueba = true;
		assertEquals(prueba, varillasPrueba.containsKey(v0));
		assertEquals(prueba, varillasPrueba.containsKey(v1));
		assertEquals(prueba, varillasPrueba.containsKey(v2));
		assertEquals(prueba, varillasPrueba.containsKey(v3));
	}
	
	@Test
	public void debeCalcularPesoDeUnaVarilla () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		HashMap<Varilla, Double> resultado = vg01.calcularPesoUnitarioVarilla();
		assertEquals(1.00125, resultado.get(v0));
		assertEquals(14.0625, resultado.get(v1));
		assertEquals(14.0625, resultado.get(v2));
		assertEquals(13.5, resultado.get(v3));
	}
	
	@Test
	public void debeCalcularPesoPorTipo () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		HashMap<Varilla, Double> resultado = vg01.calcularPesoPorTipoVarilla();
		assertEquals(50.0625, resultado.get(v0));
		assertEquals(28.125, resultado.get(v1));
		assertEquals(28.125, resultado.get(v2));
		assertEquals(13.5, resultado.get(v3));
	}
	
	@Test
	public void debeCalcularPesoTotalElemento () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		assertEquals(119.8125, vg01.calcularPesoTotal());
	}
	
	@Test
	public void debeAgregarElementos() {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		piso1.agregarELemento(vg01, 2);
		HashMap<Elemento, Integer> elementosPrueba = piso1.getElementos();
		boolean prueba = true;
		assertEquals(prueba, elementosPrueba.containsKey(vg01));
	}
	
	@Test
	public void debeCalcularPesoElementoUnitario () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		piso1.agregarELemento(vg01, 2);
		HashMap<Elemento, Double> resultado = piso1.calcularPesoUnitarioELemento();
		assertEquals(119.8125, resultado.get(vg01));
	}
	
	@Test
	public void debeCalcularPesoTotalElementoEnItem () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		piso1.agregarELemento(vg01, 2);
		HashMap<Elemento, Double> resultado = piso1.calcularPesoTotalElemento();
		assertEquals(239.625, resultado.get(vg01));
	}
	
	@Test
	public void debeCalcularPesoTotalItme () {
		vg01.agregarVarilla(v0, 50);
		vg01.agregarVarilla(v1, 2);
		vg01.agregarVarilla(v2, 2);
		vg01.agregarVarilla(v3, 1);
		piso1.agregarELemento(vg01, 2);
		assertEquals(239.625, piso1.calcularPesoTotal());
	}
	
	@Test
	public void debeCompararVarillas () {
		assertEquals(true, v4.comparaVarillas(v5));
	}
	
	@Test
	public void debeCalcularVarillasTotales () {
		Elemento vg1 = new Elemento("vg01");
		Varilla v00 = new Tipo0(2, 154);
		Varilla v01 = new Tipo1(3, 456);
		Varilla v02 = new Tipo2(4, 789);
		Varilla v03 = new Tipo3(5, 987);
		Varilla v04 = new Tipo4(6, 654);
		Varilla v05 = new Tipo6(7, 741);
		Varilla v06 = new Tipo7(8, 852);
		Varilla v07 = new Tipo8(9, 932);
		Varilla v08 = new Tipo0(10, 874);
		Varilla v09 = new Tipo0(2, 789);
		Varilla v10 = new Tipo1(3, 753);
		Varilla v11 = new Tipo2(8, 964);
		Varilla v12 = new Tipo2(8, 964);
		vg1.agregarVarilla(v00, 1);
		vg1.agregarVarilla(v01, 1);
		vg1.agregarVarilla(v02, 1);
		vg1.agregarVarilla(v03, 1);
		vg1.agregarVarilla(v04, 1);
		vg1.agregarVarilla(v05, 1);
		vg1.agregarVarilla(v06, 1);
		vg1.agregarVarilla(v07, 1);
		vg1.agregarVarilla(v08, 1);
		vg1.agregarVarilla(v09, 1);
		vg1.agregarVarilla(v10, 1);
		vg1.agregarVarilla(v11, 1);
		vg1.agregarVarilla(v12, 1);
		Item piso = new Item("Piso1");
		piso.agregarELemento(vg1, 1);
		System.out.println(piso.calcularTotalesCantidadesVarillas());
		System.out.println("Varillas de elemento sumadas");
		System.out.println(vg1.calcularTotalesCantidadesVarillas());
	}
}
