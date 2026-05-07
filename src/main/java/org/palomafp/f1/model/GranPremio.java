package org.palomafp.f1.model;

import java.util.ArrayList;
import java.util.Date;

public class GranPremio {
	private int idGp;
	private String nombre;
	private String ubicacion;
	private double longitud;
	private int vueltas;
	private String vueltaRápida;
	private Date anioCreacion;
	private ArrayList<Participacion> participaciones;
	private String urlFoto;

	public GranPremio(int idGp, String nombre, String ubicacion, double longitud, int vueltas, String vueltaRápida,
			Date anioCreacion, ArrayList<Participacion> participaciones, String urlFoto) {
		this.idGp = idGp;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.longitud = longitud;
		this.vueltas = vueltas;
		this.vueltaRápida = vueltaRápida;
		this.anioCreacion = anioCreacion;
		this.participaciones = participaciones;
		this.urlFoto = urlFoto;
	}

	public void setIdGp(int idGp) {
		this.idGp = idGp;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setVueltas(int vueltas) {
		this.vueltas = vueltas;
	}

	public void setVueltaRápida(String vueltaRápida) {
		this.vueltaRápida = vueltaRápida;
	}

	public void setAnioCreacion(Date anioCreacion) {
		this.anioCreacion = anioCreacion;
	}

	public void setParticipaciones(ArrayList<Participacion> participaciones) {
		this.participaciones = participaciones;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public int getIdGp() {
		return idGp;
	}

	public String getNombre() {
		return nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public double getLongitud() {
		return longitud;
	}

	public int getVueltas() {
		return vueltas;
	}

	public String getVueltaRápida() {
		return vueltaRápida;
	}

	public Date getAnioCreacion() {
		return anioCreacion;
	}

	public ArrayList<Participacion> getParticipaciones() {
		return participaciones;
	}

	public String getUrlFoto() {
		return urlFoto;
	}
}
