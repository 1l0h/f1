package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Piloto;

public class PilotosDAO {
	private Connection conexion;

	public PilotosDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los pilotos
	 */
	public ArrayList<Piloto> obtenerTodosPilotos() {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		try {
			String sql = "SELECT * FROM piloto ORDER BY numero";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto"));
				pilotos.add(piloto);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todos los pilotos: " + sqle.getMessage());
		}
		return pilotos;
	}

	/**
	 * Obtiene un piloto por su número
	 */
	public Piloto obtenerPilotoPorNumero(int numero) {
		try {
			String sql = "SELECT * FROM piloto WHERE numero = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, numero);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto"));
				resultado.close();
				ps.close();
				return piloto;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener piloto por número: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Obtiene pilotos por nacionalidad
	 */
	public ArrayList<Piloto> obtenerPilotosPorNacionalidad(String nacionalidad) {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		try {
			String sql = "SELECT * FROM piloto WHERE nacionalidad = ? ORDER BY apellido";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nacionalidad);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto"));
				pilotos.add(piloto);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener pilotos por nacionalidad: " + sqle.getMessage());
		}
		return pilotos;
	}

	/**
	 * Obtiene pilotos por apellido
	 */
	public ArrayList<Piloto> obtenerPilotosPorApellido(String apellido) {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		try {
			String sql = "SELECT * FROM piloto WHERE apellido LIKE ? ORDER BY apellido";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, "%" + apellido + "%");
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Piloto piloto = new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						new Date(resultado.getDate("fecha_nacimiento").getTime()), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto"));
				pilotos.add(piloto);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener pilotos por apellido: " + sqle.getMessage());
		}
		return pilotos;
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
