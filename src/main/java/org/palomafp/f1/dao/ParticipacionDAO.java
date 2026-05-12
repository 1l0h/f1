package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Participacion;
import org.palomafp.f1.model.Piloto;

public class ParticipacionDAO {
	private Connection conexion;

	public ParticipacionDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las participaciones
	 */
	public ArrayList<Participacion> obtenerTodasParticipaciones() {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		try {
			String sql = "SELECT p.*, ppgp.posicion, ppgp.tiempo FROM piloto p "
					+ "JOIN piloto_participa_gp ppgp ON p.numero = ppgp.num_piloto "
					+ "ORDER BY ppgp.id_gp, ppgp.posicion";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				Participacion participacion = construirParticipacion(resultado);
				participaciones.add(participacion);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todas las participaciones: " + sqle.getMessage());
		}
		return participaciones;
	}

	/**
	 * Obtiene participaciones de un piloto
	 */
	public ArrayList<Participacion> obtenerParticipacionesPiloto(int numeroPiloto) {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		try {
			String sql = "SELECT p.*, ppgp.posicion, ppgp.tiempo FROM piloto p "
					+ "JOIN piloto_participa_gp ppgp ON p.numero = ppgp.num_piloto "
					+ "WHERE p.numero = ? ORDER BY ppgp.posicion";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, numeroPiloto);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Participacion participacion = construirParticipacion(resultado);
				participaciones.add(participacion);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener participaciones del piloto: " + sqle.getMessage());
		}
		return participaciones;
	}

	/**
	 * Obtiene participaciones de un gran premio
	 */
	public ArrayList<Participacion> obtenerParticipacionesGranPremio(int idGp) {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		try {
			String sql = "SELECT p.*, ppgp.posicion, ppgp.tiempo FROM piloto p "
					+ "JOIN piloto_participa_gp ppgp ON p.numero = ppgp.num_piloto "
					+ "WHERE ppgp.id_gp = ? ORDER BY ppgp.posicion";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idGp);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Participacion participacion = construirParticipacion(resultado);
				participaciones.add(participacion);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener participaciones del GP: " + sqle.getMessage());
		}
		return participaciones;
	}

	/**
	 * Construye una participación desde un ResultSet
	 */
	private Participacion construirParticipacion(ResultSet resultado) throws SQLException {
		Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
				resultado.getString("apellido"), resultado.getString("nacionalidad"),
				new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
				resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
				resultado.getString("foto"));

		String tiempo = resultado.getTime("tiempo") != null ? resultado.getTime("tiempo").toString() : null;
		int posicion = resultado.getInt("posicion");

		return new Participacion(piloto, tiempo, posicion);
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
