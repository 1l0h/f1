package org.palomafp.f1.model;

import java.util.Date;

public class Piloto {
    private int numero;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private Date fechaNacimiento;
    private int podios;
    private int victorias;
    private int campeonatos;
    private int poles;
    private String urlFoto;
    
	public Piloto(int numero, String nombre, String apellido, String nacionalidad, Date fechaNacimiento, int podios,
			int victorias, int campeonatos, int poles, String urlFoto) {
		this.numero = numero;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.podios = podios;
		this.victorias = victorias;
		this.campeonatos = campeonatos;
		this.poles = poles;
		this.urlFoto = urlFoto;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setPodios(int podios) {
		this.podios = podios;
	}

	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}

	public void setCampeonatos(int campeonatos) {
		this.campeonatos = campeonatos;
	}

	public void setPoles(int poles) {
		this.poles = poles;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public int getNumero() {
		return numero;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public String getNacionalidad() {
		return nacionalidad;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public int getPodios() {
		return podios;
	}
	
	public int getVictorias() {
		return victorias;
	}
	
	public int getCampeonatos() {
		return campeonatos;
	}
	
	public int getPoles() {
		return poles;
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}
}
