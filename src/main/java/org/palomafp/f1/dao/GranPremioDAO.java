package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.GranPremio;
import org.palomafp.f1.model.Participacion;
import org.palomafp.f1.model.Piloto;

public class GranPremioDAO {

	private String url;
	private String usuario;
	private String contrasena;
	private PilotosDAO pilotosDAO;

	public GranPremioDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los grandes premios (desde BD o MOCK)
	 */
	public ArrayList<GranPremio> obtenerTodosGrandesPremios() {
		ArrayList<GranPremio> granPremios = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fechaPrimeraCarrera FROM granpremios";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idGp = rs.getInt("idGp");
					String nombre = rs.getString("nombre");
					String ubicacion = rs.getString("ubicacion");
					double longitud = rs.getDouble("longitud");
					int vueltas = rs.getInt("vueltas");
					String tiempoPromedio = rs.getString("tiempoPromedio");
					Date fecha = new Date(rs.getDate("fechaPrimeraCarrera").getTime());
					
					// Obtener participaciones del GP
					ArrayList<Participacion> participaciones = obtenerParticipacionesGranPremio(idGp);
					
					GranPremio gp = new GranPremio(idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fecha, participaciones, null);
					granPremios.add(gp);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!granPremios.isEmpty()) {
					return granPremios;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar GPs desde BD: " + e.getMessage());
		}
		
		// Fallback a mock
		ArrayList<Participacion> part1 = new ArrayList<>();
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));
		GranPremio bahrain = new GranPremio(1, "Bahrain Grand Prix", "Bahréin", 5.412, 57, "01:31:12", 
			parseDate("2004-01-01"), part1, null);
		granPremios.add(bahrain);

		ArrayList<Participacion> part2 = new ArrayList<>();
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));
		GranPremio saudiArabia = new GranPremio(2, "Saudi Arabian Grand Prix", "Arabia Saudita", 6.174, 50, "01:55:23", 
			parseDate("2021-01-01"), part2, null);
		granPremios.add(saudiArabia);

		ArrayList<Participacion> part3 = new ArrayList<>();
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));
		GranPremio australia = new GranPremio(3, "Australian Grand Prix", "Australia", 5.278, 58, "02:29:45", 
			parseDate("1985-01-01"), part3, null);
		granPremios.add(australia);

		ArrayList<Participacion> part4 = new ArrayList<>();
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "02:00:12", 1));
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "02:00:45", 2));
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:01:20", 3));
		GranPremio japon = new GranPremio(4, "Japanese Grand Prix", "Japón", 5.807, 53, "02:02:15", 
			parseDate("1976-01-01"), part4, null);
		granPremios.add(japon);

		ArrayList<Participacion> part5 = new ArrayList<>();
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:58:32", 1));
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:59:10", 2));
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "01:59:45", 3));
		GranPremio monaco = new GranPremio(5, "Monaco Grand Prix", "Mónaco", 3.337, 78, "01:55:23", 
			parseDate("1950-01-01"), part5, null);
		granPremios.add(monaco);

		return granPremios;
	}

	/**
	 * Obtiene un gran premio por su ID (desde BD o MOCK)
	 */
	public GranPremio obtenerGranPremioPorId(int idGp) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fechaPrimeraCarrera FROM granpremios WHERE idGp = " + idGp;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					String nombre = rs.getString("nombre");
					String ubicacion = rs.getString("ubicacion");
					double longitud = rs.getDouble("longitud");
					int vueltas = rs.getInt("vueltas");
					String tiempoPromedio = rs.getString("tiempoPromedio");
					Date fecha = new Date(rs.getDate("fechaPrimeraCarrera").getTime());
					ArrayList<Participacion> participaciones = obtenerParticipacionesGranPremio(idGp);
					
					GranPremio gp = new GranPremio(idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fecha, participaciones, null);
					rs.close();
					stmt.close();
					con.close();
					return gp;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al obtener GP por ID: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getIdGp() == idGp) {
				return gp;
			}
		}
		return null;
	}

	/**
	 * Obtiene un gran premio por su nombre (desde BD o MOCK)
	 */
	public GranPremio obtenerGranPremioPorNombre(String nombre) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fechaPrimeraCarrera FROM granpremios WHERE nombre = '" + nombre + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					int idGp = rs.getInt("idGp");
					String ubicacion = rs.getString("ubicacion");
					double longitud = rs.getDouble("longitud");
					int vueltas = rs.getInt("vueltas");
					String tiempoPromedio = rs.getString("tiempoPromedio");
					Date fecha = new Date(rs.getDate("fechaPrimeraCarrera").getTime());
					ArrayList<Participacion> participaciones = obtenerParticipacionesGranPremio(idGp);
					
					GranPremio gp = new GranPremio(idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fecha, participaciones, null);
					rs.close();
					stmt.close();
					con.close();
					return gp;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al obtener GP por nombre: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getNombre().equalsIgnoreCase(nombre)) {
				return gp;
			}
		}
		return null;
	}

	/**
	 * Obtiene GPs por ubicación (desde BD o MOCK)
	 */
	public ArrayList<GranPremio> obtenerGrandesPremiosPorUbicacion(String ubicacion) {
		ArrayList<GranPremio> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fechaPrimeraCarrera FROM granpremios WHERE ubicacion = '" + ubicacion + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idGp = rs.getInt("idGp");
					String nombre = rs.getString("nombre");
					double longitud = rs.getDouble("longitud");
					int vueltas = rs.getInt("vueltas");
					String tiempoPromedio = rs.getString("tiempoPromedio");
					Date fecha = new Date(rs.getDate("fechaPrimeraCarrera").getTime());
					ArrayList<Participacion> participaciones = obtenerParticipacionesGranPremio(idGp);
					
					GranPremio gp = new GranPremio(idGp, nombre, ubicacion, longitud, vueltas, tiempoPromedio, fecha, participaciones, null);
					resultado.add(gp);
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener GPs por ubicación: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getUbicacion().equalsIgnoreCase(ubicacion)) {
				resultado.add(gp);
			}
		}
		return resultado;
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
	 * Obtiene participaciones de un GP desde BD
	 */
	private ArrayList<Participacion> obtenerParticipacionesGranPremio(int idGp) {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numeroPiloto, tiempoCarrera, posicion FROM participaciones WHERE idGp = " + idGp + " ORDER BY posicion";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numeroPiloto = rs.getInt("numeroPiloto");
					String tiempoCarrera = rs.getString("tiempoCarrera");
					int posicion = rs.getInt("posicion");
					
					Piloto piloto = pilotosDAO.obtenerPilotoPorNumero(numeroPiloto);
					if (piloto != null) {
						participaciones.add(new Participacion(piloto, tiempoCarrera, posicion));
					}
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!participaciones.isEmpty()) {
					return participaciones;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener participaciones del GP: " + e.getMessage());
		}
		
		return participaciones;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar aquí (se cierra en cada query)
	}
}
