package org.palomafp.f1.model;

public class Participacion {
	private Piloto piloto;
	private String tiempo;
	private int posicion;
	
	public Participacion(Piloto piloto, String tiempo, int posicion) {
		this.piloto = piloto;
		this.tiempo = tiempo;
		this.posicion = posicion;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public Piloto getPiloto() {
		return piloto;
	}
	
	public String getTiempo() {
		return tiempo;
	}
	
	public int getPosicion() {
		return posicion;
	}
}
