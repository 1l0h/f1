package org.palomafp.f1.model;

import java.util.ArrayList;

public class Temporada {
	private int anio;
	private ArrayList<GranPremio> carreras;
	private ArrayList<Escuderia> escuderias;
	private Piloto pilotoGanador;
	
	public Temporada(int anio, ArrayList<GranPremio> carreras, ArrayList<Escuderia> escuderias, Piloto pilotoGanador) {
		this.anio = anio;
		this.carreras = carreras;
		this.escuderias = escuderias;
		this.pilotoGanador = pilotoGanador;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public void setCarreras(ArrayList<GranPremio> carreras) {
		this.carreras = carreras;
	}

	public void setEscuderias(ArrayList<Escuderia> escuderias) {
		this.escuderias = escuderias;
	}

	public void setPilotoGanador(Piloto pilotoGanador) {
		this.pilotoGanador = pilotoGanador;
	}

	public int getAnio() {
		return anio;
	}
	
	public ArrayList<GranPremio> getCarreras() {
		return carreras;
	}
	
	public ArrayList<Escuderia> getEscuderias() {
		return escuderias;
	}
	
	public Piloto getPilotoGanador() {
		return pilotoGanador;
	}
}
