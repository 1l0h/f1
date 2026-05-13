package org.palomafp.f1.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Escuderia;
import org.palomafp.f1.model.ModeloCoche;
import org.palomafp.f1.model.Piloto;

public class EscuderiaDAO {

	private PilotosDAO pilotosDAO;
	private ModeloCochesDAO modeloCochesDAO;

	public EscuderiaDAO(String url, String usuario, String contrasena) {
		// Constructor adaptado para modo mock - no se conecta a BD
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		this.modeloCochesDAO = new ModeloCochesDAO(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las escuderías (DATOS MOCK)
	 */
	public ArrayList<Escuderia> obtenerTodasEscuderias() {
		ArrayList<Escuderia> escuderias = new ArrayList<>();

		// McLaren
		ArrayList<Piloto> pilotos1 = new ArrayList<>();
		pilotos1.add(pilotosDAO.obtenerPilotoPorNumero(1));    // Lando Norris
		pilotos1.add(pilotosDAO.obtenerPilotoPorNumero(81));   // Oscar Piastri
		Escuderia mclaren = new Escuderia(1, "McLaren", "Reino Unido", "Andrea Stella", 
			parseDate("1966-01-01"), 9, 13, 195, "FF8000", 
			modeloCochesDAO.obtenerModeloPorId(1), pilotos1, null);
		escuderias.add(mclaren);

		// Mercedes
		ArrayList<Piloto> pilotos2 = new ArrayList<>();
		pilotos2.add(pilotosDAO.obtenerPilotoPorNumero(63));    // George Russell
		pilotos2.add(pilotosDAO.obtenerPilotoPorNumero(44));    // Lewis Hamilton
		Escuderia mercedes = new Escuderia(2, "Mercedes", "Alemania", "Toto Wolff",
			parseDate("2010-01-01"), 8, 9, 129, "00D2BE",
			modeloCochesDAO.obtenerModeloPorId(2), pilotos2, null);
		escuderias.add(mercedes);

		// Ferrari
		ArrayList<Piloto> pilotos3 = new ArrayList<>();
		pilotos3.add(pilotosDAO.obtenerPilotoPorNumero(16));    // Charles Leclerc
		pilotos3.add(pilotosDAO.obtenerPilotoPorNumero(55));    // Carlos Sainz
		Escuderia ferrari = new Escuderia(3, "Ferrari", "Italia", "Frederic Vasseur",
			parseDate("1950-01-01"), 16, 15, 248, "DC0000",
			modeloCochesDAO.obtenerModeloPorId(3), pilotos3, null);
		escuderias.add(ferrari);

		// Red Bull Racing
		ArrayList<Piloto> pilotos4 = new ArrayList<>();
		pilotos4.add(pilotosDAO.obtenerPilotoPorNumero(33));    // Max Verstappen
		pilotos4.add(pilotosDAO.obtenerPilotoPorNumero(11));    // Sergio Perez
		Escuderia redBull = new Escuderia(4, "Red Bull Racing", "Austria", "Christian Horner",
			parseDate("2005-01-01"), 6, 8, 122, "1E41FF",
			modeloCochesDAO.obtenerModeloPorId(4), pilotos4, null);
		escuderias.add(redBull);

		// Aston Martin
		ArrayList<Piloto> pilotos5 = new ArrayList<>();
		pilotos5.add(pilotosDAO.obtenerPilotoPorNumero(14));    // Fernando Alonso
		pilotos5.add(pilotosDAO.obtenerPilotoPorNumero(18));    // Lance Stroll
		Escuderia astonMartin = new Escuderia(5, "Aston Martin", "Reino Unido", "Mike Krack",
			parseDate("2021-01-01"), 0, 0, 1, "006F62",
			modeloCochesDAO.obtenerModeloPorId(5), pilotos5, null);
		escuderias.add(astonMartin);

		return escuderias;
	}

	/**
	 * Obtiene una escudería por su ID (DATOS MOCK)
	 */
	public Escuderia obtenerEscuderiaPorId(int idEscuderia) {
		ArrayList<Escuderia> todas = obtenerTodasEscuderias();
		for (Escuderia e : todas) {
			if (e.getIdEscuderia() == idEscuderia) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Obtiene una escudería por su nombre (DATOS MOCK)
	 */
	public Escuderia obtenerEscuderiaPorNombre(String nombre) {
		ArrayList<Escuderia> todas = obtenerTodasEscuderias();
		for (Escuderia e : todas) {
			if (e.getNombre().equalsIgnoreCase(nombre)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Método auxiliar para parsear fechas
	 */
	private Date parseDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
	}
}
