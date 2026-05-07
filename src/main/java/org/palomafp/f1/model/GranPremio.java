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
}
