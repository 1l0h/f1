package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

	private String url;
	private String usuario;
	private String contrasena;
	private PilotosDAO pilotosDAO;
	private GranPremioDAO granPremioDAO;
	private EscuderiaDAO escuderiaDAO;

	public TemporadaDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		this.granPremioDAO = new GranPremioDAO(url, usuario, contrasena);
		this.escuderiaDAO = new EscuderiaDAO(url, usuario, contrasena);

		rellenarTemporadas();
	}
	
	public TemporadaDAO() {
		this.pilotosDAO = new PilotosDAO(null, null, null);
		this.granPremioDAO = new GranPremioDAO(null, null, null);
		this.escuderiaDAO = new EscuderiaDAO(null, null, null);

		rellenarTemporadas();
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	public void rellenarTemporadas() {
		temporadas = new ArrayList<Temporada>();

		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT anio, numeroPilotoGanador FROM temporadas ORDER BY anio DESC";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int anio = rs.getInt("anio");
					int numeroPiloto = rs.getInt("numeroPilotoGanador");
					
					Piloto pilotoGanador = pilotosDAO.obtenerPilotoPorNumero(numeroPiloto);
					ArrayList<GranPremio> carreras = granPremioDAO.obtenerTodosGrandesPremios();
					ArrayList<Escuderia> escuderias = escuderiaDAO.obtenerTodasEscuderias();
					
					Temporada temp = new Temporada(anio, carreras, escuderias, pilotoGanador);
					temporadas.add(temp);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!temporadas.isEmpty()) {
					return;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar con BD: " + e.getMessage());
		}
		
		// Fallback a mock
		Piloto pilotoGanador2026 = pilotosDAO.obtenerPilotoPorNumero(1);
		ArrayList<GranPremio> carreras2026 = granPremioDAO.obtenerTodosGrandesPremios();
		ArrayList<Escuderia> escuderias2026 = escuderiaDAO.obtenerTodasEscuderias();
		Temporada temp2026 = new Temporada(2026, carreras2026, escuderias2026, pilotoGanador2026);
		temporadas.add(temp2026);

		Piloto pilotoGanador2025 = pilotosDAO.obtenerPilotoPorNumero(33);
		ArrayList<GranPremio> carreras2025 = new ArrayList<>(granPremioDAO.obtenerTodosGrandesPremios());
		ArrayList<Escuderia> escuderias2025 = new ArrayList<>(escuderiaDAO.obtenerTodasEscuderias());
		Temporada temp2025 = new Temporada(2025, carreras2025, escuderias2025, pilotoGanador2025);
		temporadas.add(temp2025);

		Piloto pilotoGanador2024 = pilotosDAO.obtenerPilotoPorNumero(44);
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
