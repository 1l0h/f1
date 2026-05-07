package org.palomafp.f1.model;

import java.util.ArrayList;
import java.util.Date;

public class Escuderia {
	private int idEscuderia;
	private String nombre;
	private String pais;
	private String jefeEquipo;
	private Date anioEntrada;
	private int campeonatos;
	private int campeonatosPilotos;
	private int victorias;
	private String color;
	private ModeloCoche coche;
	private ArrayList<Piloto> pilotos;
	private String urlFoto;
	
	public Escuderia(int idEscuderia, String nombre, String pais, String jefeEquipo, Date anioEntrada, int campeonatos,
			int campeonatosPilotos, int victorias, String color, ModeloCoche coche, ArrayList<Piloto> pilotos,
			String urlFoto) {
		this.idEscuderia = idEscuderia;
		this.nombre = nombre;
		this.pais = pais;
		this.jefeEquipo = jefeEquipo;
		this.anioEntrada = anioEntrada;
		this.campeonatos = campeonatos;
		this.campeonatosPilotos = campeonatosPilotos;
		this.victorias = victorias;
		this.color = color;
		this.coche = coche;
		this.pilotos = pilotos;
		this.urlFoto = urlFoto;
	}

	public void setIdEscuderia(int idEscuderia) {
		this.idEscuderia = idEscuderia;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setJefeEquipo(String jefeEquipo) {
		this.jefeEquipo = jefeEquipo;
	}

	public void setAnioEntrada(Date anioEntrada) {
		this.anioEntrada = anioEntrada;
	}

	public void setCampeonatos(int campeonatos) {
		this.campeonatos = campeonatos;
	}

	public void setCampeonatosPilotos(int campeonatosPilotos) {
		this.campeonatosPilotos = campeonatosPilotos;
	}

	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setCoche(ModeloCoche coche) {
		this.coche = coche;
	}

	public void setPilotos(ArrayList<Piloto> pilotos) {
		this.pilotos = pilotos;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public int getIdEscuderia() {
		return idEscuderia;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getPais() {
		return pais;
	}
	
	public String getJefeEquipo() {
		return jefeEquipo;
	}
	
	public Date getAnioEntrada() {
		return anioEntrada;
	}
	
	public int getCampeonatos() {
		return campeonatos;
	}
	
	public int getCampeonatosPilotos() {
		return campeonatosPilotos;
	}
	
	public int getVictorias() {
		return victorias;
	}
	
	public String getColor() {
		return color;
	}
	
	public ModeloCoche getCoche() {
		return coche;
	}
	
	public ArrayList<Piloto> getPilotos() {
		return pilotos;
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}
}
