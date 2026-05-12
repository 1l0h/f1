package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.palomafp.f1.model.ModeloCoche;

public class ModeloCochesDAO {
	private Connection conexion;

	public ModeloCochesDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los modelos de coches
	 */
	public ArrayList<ModeloCoche> obtenerTodosModelos() {
		ArrayList<ModeloCoche> coches = new ArrayList<>();
		try {
			String sql = "SELECT * FROM monoplaza ORDER BY id_modelo";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				ModeloCoche coche = new ModeloCoche(resultado.getInt("id_modelo"), resultado.getString("nombre"),
						resultado.getString("motor"), resultado.getInt("caballos"),
						resultado.getInt("velocidad_max"), resultado.getDouble("peso"), resultado.getString("foto"));
				coches.add(coche);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todos los modelos: " + sqle.getMessage());
		}
		return coches;
	}

	/**
	 * Obtiene un modelo de coche por su ID
	 */
	public ModeloCoche obtenerModeloPorId(int idModelo) {
		try {
			String sql = "SELECT * FROM monoplaza WHERE id_modelo = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idModelo);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				ModeloCoche coche = new ModeloCoche(resultado.getInt("id_modelo"), resultado.getString("nombre"),
						resultado.getString("motor"), resultado.getInt("caballos"),
						resultado.getInt("velocidad_max"), resultado.getDouble("peso"), resultado.getString("foto"));
				resultado.close();
				ps.close();
				return coche;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener modelo por ID: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Obtiene modelos de coche por nombre
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorNombre(String nombre) {
		ArrayList<ModeloCoche> coches = new ArrayList<>();
		try {
			String sql = "SELECT * FROM monoplaza WHERE nombre LIKE ? ORDER BY nombre";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, "%" + nombre + "%");
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				ModeloCoche coche = new ModeloCoche(resultado.getInt("id_modelo"), resultado.getString("nombre"),
						resultado.getString("motor"), resultado.getInt("caballos"),
						resultado.getInt("velocidad_max"), resultado.getDouble("peso"), resultado.getString("foto"));
				coches.add(coche);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener modelos por nombre: " + sqle.getMessage());
		}
		return coches;
	}

	/**
	 * Obtiene modelos de coche por motor
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorMotor(String motor) {
		ArrayList<ModeloCoche> coches = new ArrayList<>();
		try {
			String sql = "SELECT * FROM monoplaza WHERE motor = ? ORDER BY nombre";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, motor);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				ModeloCoche coche = new ModeloCoche(resultado.getInt("id_modelo"), resultado.getString("nombre"),
						resultado.getString("motor"), resultado.getInt("caballos"),
						resultado.getInt("velocidad_max"), resultado.getDouble("peso"), resultado.getString("foto"));
				coches.add(coche);
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener modelos por motor: " + sqle.getMessage());
		}
		return coches;
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
