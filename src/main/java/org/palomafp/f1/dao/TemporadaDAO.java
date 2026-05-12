package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.palomafp.f1.model.Escuderia;
import org.palomafp.f1.model.GranPremio;
import org.palomafp.f1.model.Piloto;
import org.palomafp.f1.model.Temporada;

public class TemporadaDAO {
	private Connection conexion;
	private PilotosDAO pilotosDAO;

	public TemporadaDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las temporadas
	 */
	public ArrayList<Temporada> obtenerTodasTemporadas() {
		ArrayList<Temporada> temporadas = new ArrayList<>();
		try {
			String sql = "SELECT * FROM temporada ORDER BY anio DESC";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				Temporada temporada = construirTemporada(resultado);
				temporadas.add(temporada);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todas las temporadas: " + sqle.getMessage());
		}
		return temporadas;
	}

	/**
	 * Obtiene una temporada por año
	 */
	public Temporada obtenerTemporadaPorAnio(int anio) {
		try {
			String sql = "SELECT * FROM temporada WHERE anio = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, anio);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				Temporada temporada = construirTemporada(resultado);
				resultado.close();
				ps.close();
				return temporada;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener temporada por año: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Construye una temporada desde un ResultSet
	 */
	private Temporada construirTemporada(ResultSet resultado) throws SQLException {
		int anio = resultado.getInt("anio");
		int numeroPilotoGanador = resultado.getInt("num_piloto_ganador");

		Piloto pilotoGanador = null;
		if (numeroPilotoGanador > 0) {
			pilotoGanador = pilotosDAO.obtenerPilotoPorNumero(numeroPilotoGanador);
		}

		ArrayList<GranPremio> carreras = obtenerCarrerasPorTemporada(anio);
		ArrayList<Escuderia> escuderias = obtenerEscuderiasPorTemporada(anio);

		return new Temporada(anio, carreras, escuderias, pilotoGanador);
	}

	/**
	 * Obtiene carreras de una temporada
	 */
	private ArrayList<GranPremio> obtenerCarrerasPorTemporada(int anio) {
		ArrayList<GranPremio> carreras = new ArrayList<>();
		try {
			String sql = "SELECT DISTINCT g.* FROM gran_premio g WHERE YEAR(g.fecha) = ? ORDER BY g.fecha";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, anio);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				// Aquí podrías crear objetos GranPremio si lo necesitas
				// Por ahora solo registramos que existen
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener carreras: " + sqle.getMessage());
		}
		return carreras;
	}

	/**
	 * Obtiene escuderías de una temporada
	 */
	private ArrayList<Escuderia> obtenerEscuderiasPorTemporada(int anio) {
		ArrayList<Escuderia> escuderias = new ArrayList<>();
		try {
			String sql = "SELECT DISTINCT e.id_escuderia FROM escuderia e "
					+ "JOIN escuderia_ficha_piloto efp ON e.id_escuderia = efp.id_escuderia "
					+ "WHERE efp.anio = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, anio);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				// Aquí podrías crear objetos Escuderia si lo necesitas
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener escuderías: " + sqle.getMessage());
		}
		return escuderias;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
			if (pilotosDAO != null) {
				pilotosDAO.cerrarConexion();
			}
		} catch (SQLException sqle) {
			System.err.println("Error al cerrar conexión: " + sqle.getMessage());
		}
	}
}
