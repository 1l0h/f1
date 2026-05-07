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
}
