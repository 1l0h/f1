package org.palomafp.f1.dao;

import java.util.ArrayList;
import java.util.List;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.model.Escuderia;
import org.palomafp.f1.model.GranPremio;
import org.palomafp.f1.model.Piloto;
import org.palomafp.f1.model.Temporada;
import org.springframework.beans.factory.annotation.Autowired;

public class TemporadaDAO {

	@Autowired
	private DatabaseConfig dbConfig;

	private List<Temporada> temporadas;

	private PilotosDAO pilotosDAO;
	private GranPremioDAO granPremioDAO;
	private EscuderiaDAO escuderiaDAO;

	public TemporadaDAO() {

		// String url = dbConfig.getUrl();
		// String usuario = dbConfig.getUsuario();
		// String contrasena = dbConfig.getContrasena();
		// Constructor adaptado para modo mock - no se conecta a BD
		// this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		// this.granPremioDAO = new GranPremioDAO(url, usuario, contrasena);
		// this.escuderiaDAO = new EscuderiaDAO(url, usuario, contrasena);

		this.pilotosDAO = new PilotosDAO(null, null, null);
		this.granPremioDAO = new GranPremioDAO(null, null, null);
		this.escuderiaDAO = new EscuderiaDAO(null, null, null);

		rellenarTemporadas();
	}

	public void rellenarTemporadas() {

		temporadas = new ArrayList<Temporada>();

		// Temporada 2026
		Piloto pilotoGanador2026 = pilotosDAO.obtenerPilotoPorNumero(1); // Lando Norris
		ArrayList<GranPremio> carreras2026 = granPremioDAO.obtenerTodosGrandesPremios();
		ArrayList<Escuderia> escuderias2026 = escuderiaDAO.obtenerTodasEscuderias();
		Temporada temp2026 = new Temporada(2026, carreras2026, escuderias2026, pilotoGanador2026);
		temporadas.add(temp2026);

		// Temporada 2025
		Piloto pilotoGanador2025 = pilotosDAO.obtenerPilotoPorNumero(33); // Max Verstappen
		ArrayList<GranPremio> carreras2025 = new ArrayList<>(granPremioDAO.obtenerTodosGrandesPremios());
		ArrayList<Escuderia> escuderias2025 = new ArrayList<>(escuderiaDAO.obtenerTodasEscuderias());
		Temporada temp2025 = new Temporada(2025, carreras2025, escuderias2025, pilotoGanador2025);
		temporadas.add(temp2025);

		// Temporada 2024
		Piloto pilotoGanador2024 = pilotosDAO.obtenerPilotoPorNumero(44); // Lewis Hamilton
		ArrayList<GranPremio> carreras2024 = new ArrayList<>(granPremioDAO.obtenerTodosGrandesPremios());
		ArrayList<Escuderia> escuderias2024 = new ArrayList<>(escuderiaDAO.obtenerTodasEscuderias());
		Temporada temp2024 = new Temporada(2024, carreras2024, escuderias2024, pilotoGanador2024);
		temporadas.add(temp2024);
	}

	/**
	 * Obtiene todas las temporadas (DATOS MOCK)
	 */
	public List<Temporada> obtenerTodasTemporadas() {
		return temporadas;
	}

	/**
	 * Obtiene una temporada por año (DATOS MOCK)
	 */
	public Temporada obtenerTemporadaPorAnio(int anio) {
		List<Temporada> todas = obtenerTodasTemporadas();
		for (Temporada t : todas) {
			if (t.getAnio() == anio) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
		if (pilotosDAO != null) {
			pilotosDAO.cerrarConexion();
		}
		if (granPremioDAO != null) {
			granPremioDAO.cerrarConexion();
		}
		if (escuderiaDAO != null) {
			escuderiaDAO.cerrarConexion();
		}
	}
}
