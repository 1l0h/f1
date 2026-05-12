package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.GranPremio;
import org.palomafp.f1.model.Participacion;
import org.palomafp.f1.model.Piloto;

public class GranPremioDAO {
	private Connection conexion;

	public GranPremioDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los grandes premios
	 */
	public ArrayList<GranPremio> obtenerTodosGrandesPremios() {
		ArrayList<GranPremio> granPremios = new ArrayList<>();
		try {
			String sql = "SELECT * FROM gran_premio ORDER BY nombre";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				GranPremio gp = construirGranPremio(resultado);
				granPremios.add(gp);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todos los GPs: " + sqle.getMessage());
		}
		return granPremios;
	}

	/**
	 * Obtiene un gran premio por su ID
	 */
	public GranPremio obtenerGranPremioPorId(int idGp) {
		try {
			String sql = "SELECT * FROM gran_premio WHERE id_gp = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idGp);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				GranPremio gp = construirGranPremio(resultado);
				resultado.close();
				ps.close();
				return gp;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener GP por ID: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Obtiene un gran premio por su nombre
	 */
	public GranPremio obtenerGranPremioPorNombre(String nombre) {
		try {
			String sql = "SELECT * FROM gran_premio WHERE nombre = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				GranPremio gp = construirGranPremio(resultado);
				resultado.close();
				ps.close();
				return gp;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener GP por nombre: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Obtiene GPs por ubicación
	 */
	public ArrayList<GranPremio> obtenerGrandesPremiosPorUbicacion(String ubicacion) {
		ArrayList<GranPremio> granPremios = new ArrayList<>();
		try {
			String sql = "SELECT * FROM gran_premio WHERE ubicacion = ? ORDER BY nombre";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, ubicacion);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				GranPremio gp = construirGranPremio(resultado);
				granPremios.add(gp);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener GPs por ubicación: " + sqle.getMessage());
		}
		return granPremios;
	}

	/**
	 * Construye un gran premio desde un ResultSet
	 */
	private GranPremio construirGranPremio(ResultSet resultado) throws SQLException {
		int idGp = resultado.getInt("id_gp");
		String nombre = resultado.getString("nombre");
		String ubicacion = resultado.getString("ubicacion");
		double longitud = resultado.getDouble("longitud");
		int vueltas = resultado.getInt("vueltas");
		String vueltaRapida = resultado.getTime("vuelta_rapida") != null
				? resultado.getTime("vuelta_rapida").toString()
				: null;
		Date anioCreacion = resultado.getDate("anio_creacion") != null
				? new Date(resultado.getDate("anio_creacion").getTime())
				: null;
		String urlFoto = resultado.getString("imagen");

		ArrayList<Participacion> participaciones = obtenerParticipacionesPorGP(idGp);

		return new GranPremio(idGp, nombre, ubicacion, longitud, vueltas, vueltaRapida, anioCreacion,
				participaciones, urlFoto);
	}

	/**
	 * Obtiene participaciones de un GP
	 */
	private ArrayList<Participacion> obtenerParticipacionesPorGP(int idGp) {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		try {
			String sql = "SELECT p.*, ppgp.posicion, ppgp.tiempo FROM piloto p "
					+ "JOIN piloto_participa_gp ppgp ON p.numero = ppgp.num_piloto "
					+ "WHERE ppgp.id_gp = ? ORDER BY ppgp.posicion";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idGp);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto"));

				String tiempo = resultado.getTime("tiempo") != null ? resultado.getTime("tiempo").toString() : null;
				int posicion = resultado.getInt("posicion");

				Participacion participacion = new Participacion(piloto, tiempo, posicion);
				participaciones.add(participacion);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener participaciones: " + sqle.getMessage());
		}
		return participaciones;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		} catch (SQLException sqle) {
			System.err.println("Error al cerrar conexión: " + sqle.getMessage());
		}
	}
}
