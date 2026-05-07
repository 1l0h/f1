package org.palomafp.f1.model;

public class ModeloCoche {
	private int idModelo;
	private String nombre;
	private String motor;
	private int caballos;
	private int velMax;
	private double peso;
	private String urlFoto;
	
	public ModeloCoche(int idModelo, String nombre, String motor, int caballos, int velMax, double peso,
			String urlFoto) {
		this.idModelo = idModelo;
		this.nombre = nombre;
		this.motor = motor;
		this.caballos = caballos;
		this.velMax = velMax;
		this.peso = peso;
		this.urlFoto = urlFoto;
	}

	public void setIdModelo(int idModelo) {
		this.idModelo = idModelo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public void setCaballos(int caballos) {
		this.caballos = caballos;
	}

	public void setVelMax(int velMax) {
		this.velMax = velMax;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public int getIdModelo() {
		return idModelo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getMotor() {
		return motor;
	}
	
	public int getCaballos() {
		return caballos;
	}
	
	public int getVelMax() {
		return velMax;
	}
	
	public double getPeso() {
		return peso;
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}
}
