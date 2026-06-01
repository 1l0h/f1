package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Piloto;

public class PilotosDAO {
	
	private String url;
	private String usuario;
	private String contrasena;
	private Connection conexion;

	public PilotosDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.conexion = null;
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			// Modo mock si no hay credenciales
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los pilotos (desde BD o MOCK)
	 */
	public ArrayList<Piloto> obtenerTodosPilotos() {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numero, nombre, apellido, nacionalidad, fechaNacimiento, podios, victorias, campeonatos, poles FROM pilotos";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numero = rs.getInt("numero");
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellido");
					String nacionalidad = rs.getString("nacionalidad");
					Date fecha = new Date(rs.getDate("fechaNacimiento").getTime());
					int podios = rs.getInt("podios");
					int victorias = rs.getInt("victorias");
					int campeonatos = rs.getInt("campeonatos");
					int poles = rs.getInt("poles");
					
					Piloto piloto = new Piloto(numero, nombre, apellido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
					pilotos.add(piloto);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!pilotos.isEmpty()) {
					return pilotos;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar con BD, usando datos mock: " + e.getMessage());
		}
		
		// Fallback a datos mock
		pilotos.add(crearPiloto(1, "Lando", "Norris", "Británica", "1999-11-13", 42, 11, 1, 15));
		pilotos.add(crearPiloto(81, "Oscar", "Piastri", "Australiana", "2001-04-06", 28, 8, 0, 6));
		pilotos.add(crearPiloto(63, "George", "Russell", "Británica", "1998-02-15", 18, 4, 0, 5));
		pilotos.add(crearPiloto(12, "Kimi", "Antonelli", "Italiana", "2006-08-25", 2, 0, 0, 1));
		pilotos.add(crearPiloto(16, "Charles", "Leclerc", "Monegasca", "1997-10-16", 39, 8, 0, 26));
		pilotos.add(crearPiloto(44, "Lewis", "Hamilton", "Británica", "1985-01-07", 201, 105, 7, 104));
		pilotos.add(crearPiloto(33, "Max", "Verstappen", "Neerlandesa", "1997-09-30", 115, 67, 4, 44));
		pilotos.add(crearPiloto(6, "Isack", "Hadjar", "Francesa", "2004-09-28", 0, 0, 0, 0));
		pilotos.add(crearPiloto(14, "Fernando", "Alonso", "Española", "1981-07-29", 106, 32, 2, 22));
		pilotos.add(crearPiloto(18, "Lance", "Stroll", "Canadiense", "1998-10-29", 3, 0, 0, 1));
		
		return pilotos;
	}

	/**
	 * Obtiene un piloto por su número (desde BD o MOCK)
	 */
	public Piloto obtenerPilotoPorNumero(int numero) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numero, nombre, apellido, nacionalidad, fechaNacimiento, podios, victorias, campeonatos, poles FROM pilotos WHERE numero = " + numero;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellido");
					String nacionalidad = rs.getString("nacionalidad");
					Date fecha = new Date(rs.getDate("fechaNacimiento").getTime());
					int podios = rs.getInt("podios");
					int victorias = rs.getInt("victorias");
					int campeonatos = rs.getInt("campeonatos");
					int poles = rs.getInt("poles");
					
					Piloto piloto = new Piloto(numero, nombre, apellido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
					rs.close();
					stmt.close();
					con.close();
					return piloto;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al query piloto por número: " + e.getMessage());
		}
		
		// Fallback a mock
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getNumero() == numero) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Obtiene pilotos por nacionalidad (desde BD o MOCK)
	 */
	public ArrayList<Piloto> obtenerPilotosPorNacionalidad(String nacionalidad) {
		ArrayList<Piloto> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numero, nombre, apellido, nacionalidad, fechaNacimiento, podios, victorias, campeonatos, poles FROM pilotos WHERE nacionalidad = '" + nacionalidad + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numero = rs.getInt("numero");
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellido");
					Date fecha = new Date(rs.getDate("fechaNacimiento").getTime());
					int podios = rs.getInt("podios");
					int victorias = rs.getInt("victorias");
					int campeonatos = rs.getInt("campeonatos");
					int poles = rs.getInt("poles");
					
					Piloto piloto = new Piloto(numero, nombre, apellido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
					resultado.add(piloto);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al query pilotos por nacionalidad: " + e.getMessage());
		}
		
		// Fallback a mock
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getNacionalidad().equalsIgnoreCase(nacionalidad)) {
				resultado.add(p);
			}
		}
		return resultado;
	}

	/**
	 * Obtiene pilotos por apellido (desde BD o MOCK)
	 */
	public ArrayList<Piloto> obtenerPilotosPorApellido(String apellido) {
		ArrayList<Piloto> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numero, nombre, apellido, nacionalidad, fechaNacimiento, podios, victorias, campeonatos, poles FROM pilotos WHERE apellido LIKE '%" + apellido + "%'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numero = rs.getInt("numero");
					String nombre = rs.getString("nombre");
					String apYObtenido = rs.getString("apellido");
					String nacionalidad = rs.getString("nacionalidad");
					Date fecha = new Date(rs.getDate("fechaNacimiento").getTime());
					int podios = rs.getInt("podios");
					int victorias = rs.getInt("victorias");
					int campeonatos = rs.getInt("campeonatos");
					int poles = rs.getInt("poles");
					
					Piloto piloto = new Piloto(numero, nombre, apYObtenido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
					resultado.add(piloto);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al query pilotos por apellido: " + e.getMessage());
		}
		
		// Fallback a mock
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
				resultado.add(p);
			}
		}
		return resultado;
	}

	/**
	 * Método auxiliar para crear pilotos
	 */
	private Piloto crearPiloto(int numero, String nombre, String apellido, String nacionalidad,
			String fechaNacimiento, int podios, int victorias, int campeonatos, int poles) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = sdf.parse(fechaNacimiento);
			return new Piloto(numero, nombre, apellido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
		} catch (Exception e) {
			System.err.println("Error al crear piloto: " + e.getMessage());
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
