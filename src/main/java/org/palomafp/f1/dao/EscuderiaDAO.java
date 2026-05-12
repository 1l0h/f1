package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Escuderia;
import org.palomafp.f1.model.ModeloCoche;
import org.palomafp.f1.model.Piloto;

public class EscuderiaDAO {
	private Connection conexion;

	public EscuderiaDAO(String url, String usuario, String contrasena) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conexion = DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las escuderías
	 */
	public ArrayList<Escuderia> obtenerTodasEscuderias() {
		ArrayList<Escuderia> escuderias = new ArrayList<>();
		try {
			String sql = "SELECT * FROM escuderia ORDER BY nombre";
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery(sql);

			while (resultado.next()) {
				Escuderia escuderia = construirEscuderia(resultado);
				escuderias.add(escuderia);
			}

			resultado.close();
			declaracion.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener todas las escuderías: " + sqle.getMessage());
		}
		return escuderias;
	}

	/**
	 * Obtiene una escudería por su ID
	 */
	public Escuderia obtenerEscuderiaPorId(int idEscuderia) {
		try {
			String sql = "SELECT * FROM escuderia WHERE id_escuderia = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idEscuderia);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				Escuderia escuderia = construirEscuderia(resultado);
				resultado.close();
				ps.close();
				return escuderia;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener escudería por ID: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Obtiene una escudería por su nombre
	 */
	public Escuderia obtenerEscuderiaPorNombre(String nombre) {
		try {
			String sql = "SELECT * FROM escuderia WHERE nombre = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet resultado = ps.executeQuery();

			if (resultado.next()) {
				Escuderia escuderia = construirEscuderia(resultado);
				resultado.close();
				ps.close();
				return escuderia;
			}

			resultado.close();
			ps.close();
		} catch (SQLException sqle) {
			System.err.println("Error al obtener escudería por nombre: " + sqle.getMessage());
		}
		return null;
	}

	/**
	 * Construye una escudería desde un ResultSet
	 */
	private Escuderia construirEscuderia(ResultSet resultado) throws SQLException {
		int idEscuderia = resultado.getInt("id_escuderia");
		String nombre = resultado.getString("nombre");
		String pais = resultado.getString("pais");
		String jefeEquipo = resultado.getString("jefe_equipo");
		Date anioEntrada = resultado.getDate("anio_entrada") != null
				? new Date(resultado.getDate("anio_entrada").getTime())
				: null;
		int campeonatos = resultado.getInt("campeonatos");
		int campeonatosPilotos = resultado.getInt("campeonatos_pilotos");
		int victorias = resultado.getInt("victorias");
		String color = resultado.getString("color_hex");
		String urlFoto = resultado.getString("foto");

		// Obtener pilotos
		ArrayList<Piloto> pilotos = obtenerPilotosPorEscuderia(idEscuderia);

		// Obtener modelo de coche actual
		ModeloCoche coche = obtenerModeloCochePorEscuderia(idEscuderia);

		return new Escuderia(idEscuderia, nombre, pais, jefeEquipo, anioEntrada, campeonatos, campeonatosPilotos,
				victorias, color, coche, pilotos, urlFoto);
	}

	/**
	 * Obtiene los pilotos de una escudería
	 */
	private ArrayList<Piloto> obtenerPilotosPorEscuderia(int idEscuderia) {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		try {
			String sql = "SELECT DISTINCT p.* FROM piloto p "
					+ "JOIN escuderia_ficha_piloto efp ON p.numero = efp.numero "
					+ "WHERE efp.id_escuderia = ? ORDER BY p.numero";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idEscuderia);
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
			System.err.println("Error al obtener pilotos: " + sqle.getMessage());
		}
		return pilotos;
	}

	/**
	 * Obtiene el modelo de coche de una escudería
	 */
	private ModeloCoche obtenerModeloCochePorEscuderia(int idEscuderia) {
		try {
			String sql = "SELECT m.* FROM monoplaza m "
					+ "JOIN escuderia_tiene_monoplaza_temporada etm ON m.id_modelo = etm.id_modelo "
					+ "WHERE etm.id_escuderia = ? ORDER BY etm.anio DESC LIMIT 1";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idEscuderia);
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
			System.err.println("Error al obtener modelo de coche: " + sqle.getMessage());
		}
		return null;
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
